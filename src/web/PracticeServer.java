package web;

import com.google.gson.reflect.TypeToken;
import dao.inter.ChoiceQuestionDao;
import pojo.ChoiceQuestion;
import service.Impl.InstructorServiceImpl;
import service.inter.InstructorService;
import utils.RequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class PracticeServer extends BaseServlet{

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private int connectAmount;
    private boolean isIssued;
    private HashMap<String, Integer> nameAndScore;
    private String standardAnswer;
    private int currentNumber;
    private List<ChoiceQuestion> choiceQuestionList;
    private InstructorService instructorService;
    private static final int PORT = 6667;

    //构造器，初始化工作
    public PracticeServer() {
        try {
            //初始化教师服务
            instructorService = new InstructorServiceImpl();
            //初始化题目列表
            choiceQuestionList = new ArrayList<ChoiceQuestion>();
            //初始化哈希表
            nameAndScore = new HashMap<>();
            isIssued = false;
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将该 listenChannel 注册到 selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1.设置房间的人数——班级总人数currentNumber √
    //2.从题库随机选题组成一套试题列表 √
    //3.将题目列表分发给客户端 √
    protected void initPracticeServer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");

        //设置当前房间的人数
        currentNumber = instructorService.getSection(courseID, classID).getCurrentNumber();
        //设置题目答案
        standardAnswer = "ABC";
        int size = 3;
        //设置题目列表
        choiceQuestionList = instructorService.getRandomQuestionList(size);
    }

    //点击进入对抗练习，运行此入口
    protected void startServer() {
        //创建服务器对象
        PracticeServer practiceServer = new PracticeServer();
        practiceServer.listen();
    }

    //前端获取对抗练习成绩单，servlet
    protected void getNameAndScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(String.valueOf(nameAndScore));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        try {
            //循环处理

            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //有事件处理
                    // 遍历得到 selectionKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出 selection key
                        SelectionKey key = iterator.next();
                        //监听到 accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将该 sc 注册到 selector
                            sc.register(selector, SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                            connectAmount++;
                            System.out.println(connectAmount);
                        }
                        if (key.isReadable()) {//通道发送read事件，即通道是可读的状态
                            // 处理读(专门写方法..)
                            readData(key);
                        }
                        if (connectAmount == currentNumber && !isIssued) {
                            //发题
                            for (ChoiceQuestion choiceQuestion : choiceQuestionList) {
                                String question = choiceQuestion.toString();
                                IssueQuestions(question);
                            }
                            isIssued = true;//已经发过，无需再发
                        }

                        //当前的 key 删除，防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //发生异常处理....
        }
    }

    //读取客户端消息
    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //得到 channel
            channel = (SocketChannel) key.channel();
            //创建 buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据 count 的值做处理
            if (count > 0) {
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from客户端:" + msg);

                processingClientData(msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了..");
                connectAmount--;
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    //获取并处理客户端发送的信息，进行分数计算、排名等，servlet
    private void processingClientData(String msg) throws IOException {

        String[] message = msg.split(" says: ");
        String name = message[0];
        String clientAnswer = message[1];
        System.out.println(name);
        System.out.println(clientAnswer);
        int score = 0;

        //比较提交答案和标准答案
        if (nameAndScore.get(name) == null) {
            for(int i = 0; i < standardAnswer.length(); i++) {
                if(clientAnswer.charAt(i) == standardAnswer.charAt(i)) {
                    score++;
                }
            }
            nameAndScore.put(name, score);//第0项为客户端名称，第1项为客户端提交答案
        }

        System.out.println(nameAndScore);
    }

    //广播给其他客户端消息
    private void broadcastToEachClient() throws IOException {
        System.out.println("服务器转发消息中...");

        for (SelectionKey key : selector.keys()) {
            //通过 key 取出对应的 SocketChannel
            Channel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将 哈希表 存储到 buffer
                ByteBuffer buffer = ByteBuffer.wrap(nameAndScore.toString().getBytes());
                //将 buffer 的数据写入通道
                dest.write(buffer);
            }
        }
    }

    //发布题目
    private void IssueQuestions(String msg) throws IOException {
        System.out.println("现在发题...");
        //遍历所有注册到 selector 上的 SocketChannel
        for (SelectionKey key : selector.keys()) {
            //通过 key 取出对应的 SocketChannel
            Channel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将 msg 存储到 buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将 buffer 的数据写入通道
                dest.write(buffer);
            }
        }
    }

}