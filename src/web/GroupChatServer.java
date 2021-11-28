package netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//多人对抗练习服务器，一次5人。成绩分为金牌、银牌、铜牌、铁牌、纸牌。
// 服务器功能：
//1.等待5个人进入一个服务器
//2.人数满后，服务器出题，展示给所有选手
//3.选手答完方可提交，信息交给服务器
//4.所有人答完后，服务器将排名展示出来
public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private int connectAmount;
    private boolean isIssued;
    private HashMap<String, Integer> nameAndScore;
    private String standardAnswer;

    private static final int PORT = 6667;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            standardAnswer = "ABC";

            nameAndScore = new HashMap<>();
            //初始化哈希表
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
                        if (connectAmount == 3 && !isIssued) {
                            //发题
                            IssueQuestions("请听题：隔壁老王哪天进了你家？A.昨天 B.今天 C.明天");
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

    //获取并处理客户端发送的信息，进行分数计算、排名等。
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

    //点击进入对抗练习，运行此，编写为servlet函数
    public static void main(String[] args) {
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}