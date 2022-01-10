package web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import dao.impl.PracticeScoreDaoImpl;
import dao.inter.PracticeScoreDao;
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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

public class PracticeClient extends BaseServlet {

    //定义相关的属性
    private final String HOST = "127.0.0.1";//服务器的ip
    private final int PORT = 6667;//服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    private String info;
    private List<ChoiceQuestion> choiceQuestionList;
    private int groupNumber;//组号
    private PracticeScoreDao practiceScoreDao = new PracticeScoreDaoImpl();

    //构造器,完成初始化工作
    public PracticeClient() throws IOException {

        //初始化题目列表
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

    protected void initPracticeClient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PracticeClient practiceClient = new PracticeClient();
        practiceClient.getGroupNumberFromServer();//获取组号groupNumber

        String choiceQuestionListOfString = null;
        while(choiceQuestionListOfString == null) {
            choiceQuestionListOfString = practiceClient.readInfo();
        }

        String[] eachQuestion = choiceQuestionListOfString.split(" ");
        for (String s : eachQuestion) {
//            if (s == null || "".equals(s) || s.equals("")) {
//                break;
//            }
            String[] values = s.split(",");
            if (values.length == 1) {
                break;
            }
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
            System.out.println(choiceQuestionObj);
            choiceQuestionList.add(choiceQuestionObj);
        }

        String json = JSONObject.toJSONString(choiceQuestionList);
        System.out.println(json);
        resp.getWriter().write(json);
    }

//    //servlet，获取学生填写的答案
//    protected void setInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json");
//        String reqJson = RequestJsonUtils.getJson(req);
//        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
//        }.getType());
//
//        info = reqObject.get("answer");
//        System.out.println(info);
//        sendInfo();
//    }

//    //servlet，将题目列表传给前端
//    protected void getQuestionList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String choiceQuestionListOfString = null;
//        while(choiceQuestionListOfString == null) {
//            choiceQuestionListOfString = readInfo();
//        }
//
//        String[] eachQuestion = choiceQuestionListOfString.split(" ");
//        for (String s : eachQuestion) {
////            if (s == null || "".equals(s) || s.equals("")) {
////                break;
////            }
//            String[] values = s.split(",");
//            if (values.length == 1) {
//                break;
//            }
//            int choiceId = Integer.parseInt(values[0].split(":")[1]);
//            String choiceQuestion = values[1].split(":")[1];
//            String choiceOption = values[2].split(":")[1];
//            int choiceDifficulty = Integer.parseInt(values[3].split(":")[1]);
//            String choiceAnswer = values[4].split(":")[1];
//            String choiceAnalysis = values[5].split(":")[1];
//            double choiceScore = Double.parseDouble(values[6].split(":")[1]);
//            ChoiceQuestion choiceQuestionObj = new ChoiceQuestion();
//
//            choiceQuestionObj.setChoiceId(choiceId);
//            choiceQuestionObj.setChoiceQuestion(choiceQuestion);
//            choiceQuestionObj.setChoiceOption(choiceOption);
//            choiceQuestionObj.setChoiceDifficulty(choiceDifficulty);
//            choiceQuestionObj.setChoiceAnswer(choiceAnswer);
//            choiceQuestionObj.setChoiceAnalysis(choiceAnalysis);
//            choiceQuestionObj.setChoiceScore(choiceScore);
//            System.out.println(choiceQuestionObj);
//            choiceQuestionList.add(choiceQuestionObj);
//        }
//
//        String json = JSONObject.toJSONString(choiceQuestionList);
//        System.out.println(json);
//        resp.getWriter().write(json);
//    }

    protected void getPracticeInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        double score = Double.parseDouble(reqObject.get("score"));
        Timestamp finishTime = Timestamp.valueOf(reqObject.get("finishTime"));
        String studentNumber = reqObject.get("studentNumber");
        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String practiceName = reqObject.get("practiceName"); //待定

        practiceScoreDao.insertPracticeScore(courseID, classID, practiceName, studentNumber, score, finishTime, groupNumber);
    }

//    //交给服务器批改
//    public void sendInfo() {
//
//        info = username + " says: " + info;
//        try {
//            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //首先获取从服务器分配的组号
    public void getGroupNumberFromServer() {
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
                        groupNumber = Integer.parseInt(msg.trim());
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

    //读取从服务器端回复的消息
    public String readInfo() {
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
                        msg = msg;
                        String question = msg;
                        System.out.println(question);
                        return question;
//                        String[] msgArray = msg.split("group:");
//                        String groupNumberFromServer = msgArray[0];
//
//                        //如果服务器发放的组号与本客户端的组号匹配，则发放题目
//                        if (Objects.equals(groupNumberFromServer, String.valueOf(groupNumber))) {
//                            String question = msgArray[1];
//                            System.out.println(question);
//                            return question;
//                        }

                    }
                }
                iterator.remove(); //删除当前的 selectionKey,防止重复操作
            } else {
                //System.out.println("没有可以用的通道...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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