package web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import pojo.ChoiceQuestion;
import utils.RequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

public class PracticeClient extends BaseServlet{

    //定义相关的属性
    private final String HOST = "127.0.0.1";//服务器的ip
    private final int PORT = 6667;//服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    private String info;
    private List<String> choiceQuestionListOfString;
    private List<ChoiceQuestion> choiceQuestionList;

    //构造器,完成初始化工作
    public PracticeClient() throws IOException {

        //初始化题目列表
        choiceQuestionListOfString = new ArrayList<String>();
        choiceQuestionList = new ArrayList<ChoiceQuestion>();
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将 channel 注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到 username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    //servlet，获取学生填写的答案
    protected void setInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        info = reqObject.get("answer");
        System.out.println(info);
        sendInfo();
    }

    //servlet，将题目列表传给前端
    protected void getQuestionList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        readInfo();
//        resp.getWriter().write(String.valueOf(choiceQuestionList));
        for (String s : choiceQuestionListOfString) {
            String[] values = s.split(",");

            int choiceId = Integer.parseInt(values[0].split(":")[1]);
            String choiceQuestion = values[1].split(":")[1];
            String choiceOption = values[2].split(":")[1];
            int choiceDifficulty = Integer.parseInt(values[3].split(":")[1]);
            String choiceAnswer = values[4].split(":")[1];
            String choiceAnalysis = values[5].split(":")[1];
            double choiceScore = Double.parseDouble(values[6].split(":")[1]);
            ChoiceQuestion choiceQuestionObj = new ChoiceQuestion();

            choiceQuestionObj.setChoiceId(choiceId);
            choiceQuestionObj.setChoiceQuestion(choiceQuestion);
            choiceQuestionObj.setChoiceOption(choiceOption);
            choiceQuestionObj.setChoiceDifficulty(choiceDifficulty);
            choiceQuestionObj.setChoiceAnswer(choiceAnswer);
            choiceQuestionObj.setChoiceAnalysis(choiceAnalysis);
            choiceQuestionObj.setChoiceScore(choiceScore);

            choiceQuestionList.add(choiceQuestionObj);
        }
        
        String json = JSONObject.toJSONString(choiceQuestionList);
        resp.getWriter().write(json);
    }


    //交给服务器批改
    public void sendInfo() {

        info = username + " says: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取从服务器端回复的消息
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {//有可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个 Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                        choiceQuestionListOfString.add(msg);
                    }
                }
                iterator.remove(); //删除当前的 selectionKey,防止重复操作
            } else {
                //System.out.println("没有可以用的通道...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //客户端入口
//    public void startClient() throws Exception {
//
//        //启动我们客户端
////        final PracticeClient chatClient = new PracticeClient();
//        //启动一个线程,每隔 3 秒，读取从服务器发送数据
////        new Thread() {
////            public void run() {
////                while (true) {
////                    chatClient.readInfo();
////                    try {
////                        Thread.currentThread().sleep(3000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        }.start();
//
//        //发送数据给服务器端
//        sendInfo();
////        //发送数据给服务器端
////        Scanner scanner = new Scanner(System.in);
////        while (scanner.hasNextLine()) {
////            String s = scanner.nextLine();
////            chatClient.sendInfo();
////        }
//    }
}