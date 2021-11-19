package web;

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
import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

        int msg = 0;
        String userNumber = reqObject.get("userNumber");
        User user = userService.ifActivated(userNumber);
        int status;
        if (user == null) {
            msg = 0;//账号不存在
        } else {
            status = user.getStatus();
            if (status == 1) {
                msg = 1;//已激活
            } else {
                //未激活
                if (user instanceof Student) {
                    user = (Student) user;
                } else if (user instanceof Instructor) {
                    user = (Instructor) user;
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
                    // 发送成功
                    msg = 2;

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回响应
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
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
            if (status.equals("0")) {
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
        userInformation.put("identify", identify);
        //转Json-String格式
        String userInformationJson = gson.toJson(userInformation);
        //返回响应
        resp.getWriter().write(userInformationJson);
    }

    /**
     * getUserStatus确认已激活后才会调用此方法来执行登陆成功的页面跳转
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String userNumber = reqObject.get("userNumber");
        //获取需要登录的用户对象，用于判断身份
        User user = userService.ifActivated(userNumber);
        //将userNumber信息加入到session,方便后续直接获取用户信息
        HttpSession session = req.getSession();
        session.setAttribute("userNumber", userNumber);
        //转到登录成功后的界面
        resp.addHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向
        if (user instanceof Student) {
            //标明身份
            session.setAttribute("identity", "student");
            //学生页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/student/sIndex.html");//重定向地址
        } else if (user instanceof Instructor) {
            session.setAttribute("identity", "instructor");
            //教师页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/instructor/aIndex.html");//重定向地址
        } else {
            session.setAttribute("identity", "administrator");
            //管理员页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/administrator/aIndex.html");//重定向地址
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("access-control-expose-headers", "REDIRECT,CONTEXTPATH");
    }

    /**
     * 根据login中存储的uerNumber信息来获取详细信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        //先获取userNumber信息
        String userNumber = (String) req.getSession().getAttribute("userNumber");
        //从数据库获取用户信息
        User user = userService.ifActivated(userNumber);
        Map<String, String> map = new HashMap<>();
        map.put("userNumber", user.getUserNumber());
        map.put("name", user.getName());
        map.put("sex", (user.getSex() == 1) ? "男" : "女");
        map.put("email", user.getEmail());
        map.put("phoneNumber", user.getPhoneNumber());
        //转Json-String格式
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 用户修改自己的信息：仅电话和邮箱
     * 返回0/1表示成功或失败
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void changeUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String userNumber = reqObject.get("userNumber");
        String phoneNumber = reqObject.get("phoneNumber");
        String email = reqObject.get("email");

        //获取用户身份
        String identity = (String) req.getSession().getAttribute("identity");
        //修改结果
        int result = userService.alterUserInfo(identity, userNumber, phoneNumber, email);
        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);

        resp.getWriter().write(gson.toJson(map));
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.清除网页记录的所有用户信息
        //2.转到登录界面
    }

    /**
     * 获取前端发送的用户输入的验证码，用于用户验证
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void verify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        //获取用户输入的验证码
        String verificationCode = reqObject.get("verificationCode");
        //获取session中存储的正确验证码
        String correctCode = (String) req.getSession().getAttribute("verificationCode");
        //结果
        int result = 0;
        if (verificationCode.equals(correctCode)) {
            result = 1;
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 用户调用该方法修改密码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String userNumber = reqObject.get("userNumber");
        String oldPassword = reqObject.get("oldPassword");
        String newPassword = reqObject.get("newPassword");
        //获取用户身份
        String identity = (String)req.getSession().getAttribute("identity");
        //修改结果
        int result = 0;
        if(oldPassword.equals(userService.getPassword(identity,userNumber))){
            //旧密码输入正确，设置新密码
            result = userService.changePassword(identity,userNumber,newPassword);
        }

        Map<String,Integer> map = new HashMap<>();
        map.put("result",result);
        resp.getWriter().write(gson.toJson(map));
    }

}
