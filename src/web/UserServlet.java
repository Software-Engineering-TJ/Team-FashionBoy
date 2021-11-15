package web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pojo.Instructor;
import pojo.Student;
import pojo.User;
import service.Impl.UserServiceImpl;
import service.inter.UserService;
import utils.RequestJsonUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * UserServlet类的描述：
 * 处理用户请求：登录、注册、退出
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:47
 */

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    //发送邮件
    public void sendEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        int msg;
        String userNumber = reqObject.get("userNumber");
        User user = userService.ifActivated(userNumber);
        int status;
        if (user == null) {
            msg = 0;//账号不存在
        } else {
            status = user.getStatus();
            if(status == 1){
                msg = 1;//已激活
            }else {
                //未激活
                if (user instanceof Student) {
                    user = (Student)user;
                } else if (user instanceof Instructor) {
                    user = (Instructor)user;
                }
                String desEmail = user.getEmail();
                // 创建Properties 类用于记录邮箱的一些属性
                Properties props = new Properties();
                // 表示SMTP发送邮件，必须进行身份验证
                props.put("mail.smtp.auth", "true");
                //此处填写SMTP服务器
                props.put("mail.smtp.host", "smtp.qq.com");
                //端口号，QQ邮箱端口587
                props.put("mail.smtp.port", "587");
                // 此处填写，写信人的账号
                props.put("mail.user", "939543598@qq.com");
                // 此处填写16位STMP口令
                props.put("mail.password", "dhexqmyoafxibbia");

                // 构建授权信息，用于进行SMTP进行身份验证
                Authenticator authenticator = new Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        // 用户名、密码
                        String userName = props.getProperty("mail.user");
                        String password = props.getProperty("mail.password");
                        return new PasswordAuthentication(userName, password);
                    }
                };
                // 使用环境属性和授权信息，创建邮件会话
                Session mailSession = Session.getInstance(props, authenticator);
                // 创建邮件消息
                MimeMessage message = new MimeMessage(mailSession);
                // 设置发件人
                InternetAddress form = null;
                try {
                    form = new InternetAddress(props.getProperty("mail.user"));

                } catch (AddressException e) {
                    e.printStackTrace();
                }
                try {
                    message.setFrom(form);
                    // 设置收件人的邮箱
                    InternetAddress to = new InternetAddress(desEmail);
                    message.setRecipient(MimeMessage.RecipientType.TO, to);

                    // 设置邮件标题
                    message.setSubject("请验证您的身份");
                    //生成四位随机数
                    int verificationCode = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
                    // 设置邮件的内容体
                    message.setContent("亲爱的" + user.getName() + "，你好！您的验证码是：" + verificationCode, "text/html;charset=UTF-8");

                    // 发送邮件
                    Transport.send(message);

                    //将发送给目标邮箱的验证码，返回给前端
                    HttpSession session = req.getSession();
                    session.setAttribute("verificationCode", verificationCode);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取email对应的账号的密码和激活状态
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String userNumber = reqObject.get("userNumber");
        User user = userService.ifActivated(userNumber);
        String password;
        String status;
        int identify = 0;  //激活码

        if (user != null) {
            password = user.getPassword();
            status = Integer.toString(user.getStatus());
            //若未激活，返回激活码
            if(status.equals("0")){
                identify = (int) req.getSession().getAttribute("verificationCode");
            }
        } else {
            //如果没有该用户，则返回如下内容
            userNumber = null;
            password = null;  // 表示账号信息不存在（那么也肯定未激活）
            status = null;
        }
        //需要返回的信息
        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("userNumber", userNumber);
        userInformation.put("password", password);
        userInformation.put("status", status);
        userInformation.put("identify",identify);
        //转Json-String格式
        String userInformationJson = gson.toJson(userInformation);
        //返回响应
        resp.getWriter().write(userInformationJson);
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String userNumber = reqObject.get("userNumber");
        //获取需要登录的用户对象，用于判断身份
        User user = userService.ifActivated(userNumber);
//        将必要信息加入到session
//        HttpSession session = req.getSession();
//        session.setAttribute("userNumber", userNumber);
        //转到登录成功后的界面
        resp.addHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
        if(user instanceof Student) {
            //学生页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/student/aIndex.html");//重定向地址
            resp.sendRedirect("/SoftwareEngineering/pages/administrator/aIndex.html");
        }else if(user instanceof Instructor){
            //教师页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/instructor/aIndex.html");//重定向地址
            resp.sendRedirect("/SoftwareEngineering/pages/instructor/aIndex.html");
        }else{
            //管理员页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/administrator/aIndex.html");//重定向地址
            resp.sendRedirect("/SoftwareEngineering/pages/administrator/aIndex.html");
        }
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.清除网页记录的所有用户信息
        //2.转到登录界面
    }

    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (userService.ExistEmail(email) == null) {
            //执行注册失败操作
        } else {
            User user = userService.Register(email, password);
            //跳转到注册成功界面
        }
    }

}
