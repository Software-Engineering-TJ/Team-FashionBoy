package web;

import com.google.gson.reflect.TypeToken;
import pojo.*;
import service.Impl.AdministrationServiceImpl;
import service.Impl.StudentServiceImpl;
import service.Impl.UserServiceImpl;
import service.inter.AdministrationService;
import service.inter.StudentService;
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
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * UserServlet类的描述：
 * 处理用户请求：登录、注册、退出
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:47
 */

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    private AdministrationService administrationService = new AdministrationServiceImpl();

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
     * 获取userNumber对应的账号的密码和激活状态
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
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/teacher/tIndex.html");//重定向地址
        } else {
            session.setAttribute("identity", "administrator");
            //管理员页面
            resp.addHeader("CONTEXTPATH", "/SoftwareEngineering/pages/administrator/aIndex.html");//重定向地址
        }
        //激活账户
        if (reqObject.get("identify") != null && !reqObject.get("identify").equals("")) {
            //如果首次激活，则将数据库用户激活状态设置为“1”
            userService.activateAccount((String) session.getAttribute("identity"), user.getEmail());
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
     *
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
        String identity = (String) req.getSession().getAttribute("identity");
        //修改结果
        int result = 0;
        if (oldPassword.equals(userService.getPassword(identity, userNumber))) {
            //旧密码输入正确，设置新密码
            result = userService.changePassword(identity, userNumber, newPassword);
        }

        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师or学生获取某个课程班级下的所有实验报告信息 √
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getReportDesc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        //获取该课程班级的所有实验描述列表
        List<Map<String, String>> expReportInfoList = userService.getExpReports(courseID, classID);

        resp.getWriter().write(gson.toJson(expReportInfoList));
    }

    /**
     * 老师或助教获取学生实验报告文件列表
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void getExpReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expname");

        //使用Path类方便获取不同电脑下的当前文件所在的路径，随环境而变
        Path path = Paths.get(req.getServletContext().getRealPath(""));
        //定位到“整个项目”所在的路径
        path = path.getParent().getParent().getParent();
        //定位到学生文件存放的路径
        Path directory = Paths.get(path.toString(), "web/WEB-INF/files/" + courseID + "/" + classID + "/" + expname);

        List<Map<String, Object>> studentFileList = userService.getFilesOfExpname(directory, courseID, classID, expname);
        //开始完善返回信息
        List<Map<String, Object>> fileInfoList = new ArrayList<>();
        for (Map<String, Object> map : studentFileList) {
            //上传文件的学生
            Student student = (Student) map.get("student");
            //文件
            File file = (File) map.get("file");
            //文件名
            String filename = file.getName();
            //文件提交的的时间
            long fileDate = file.lastModified();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fileDateStr = format.format(new Date(fileDate));
            //文件大小
            DecimalFormat df = new DecimalFormat("0.00");
            String fileSize = "";
            if (file.length() < 1024) {
                fileSize = df.format((double) file.length()) + "B";
            } else if (file.length() < 1048576) {
                fileSize = df.format((double) file.length() / 1024) + "K";
            } else if (file.length() < 1073741824) {
                fileSize = df.format((double) file.length() / 1048576) + "M";
            } else {
                fileSize = df.format((double) file.length() / 1073741824) + "G";
            }
            //文件路径
            String url = (String) map.get("url");

            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("filename", filename);
            fileInfo.put("studentNumber", student.getStudentNumber());
            fileInfo.put("studentName", student.getName());
            fileInfo.put("fileSize", fileSize);
            fileInfo.put("uploadTime", fileDateStr);
            fileInfo.put("url", url);

            fileInfoList.add(fileInfo);
        }

        resp.getWriter().write(gson.toJson(fileInfoList));
    }

    /**
     * 教师或学生获取某个课程的某个班级下，老师发布的所有参考资料信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void getReferenceMaterial(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");

        //使用Path类方便获取不同电脑下的当前文件所在的路径，随环境而变
        Path path = Paths.get(req.getServletContext().getRealPath(""));
        //定位到“整个项目”所在的路径
        path = path.getParent().getParent().getParent();
        //定位到参考资料存放的路径：courserID-classID-“references”
        Path directory = Paths.get(path.toString(), "web/WEB-INF/files/" + courseID + "/" + classID + "/" + "references");

        List<Map<String, Object>> referenceList = userService.getReferencesOfSection(directory, courseID, classID);
        //开始完善返回信息
        List<Map<String, Object>> referencesInfoList = new ArrayList<>();
        for(Map<String,Object> reference : referenceList){
            //文件上传者
            Instructor instructor = (Instructor) reference.get("instructor");
            //参考文件
            File file = (File) reference.get("file");
            //url
            String url = (String) reference.get("url");
            //文件提交的的时间
            long fileDate = file.lastModified();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fileDateStr = format.format(new Date(fileDate));
            //文件大小
            DecimalFormat df = new DecimalFormat("0.00");
            String fileSize = "";
            if (file.length() < 1024) {
                fileSize = df.format((double) file.length()) + "B";
            } else if (file.length() < 1048576) {
                fileSize = df.format((double) file.length() / 1024) + "K";
            } else if (file.length() < 1073741824) {
                fileSize = df.format((double) file.length() / 1048576) + "M";
            } else {
                fileSize = df.format((double) file.length() / 1073741824) + "G";
            }

            Map<String,Object> referenceInfo = new HashMap<>();
            referenceInfo.put("fileName",file.getName());
            referenceInfo.put("url",url);
            referenceInfo.put("fileSize",fileSize);
            referenceInfo.put("upLoadDate",fileDateStr);
            referenceInfo.put("upLoadUser",instructor.getName());

            referencesInfoList.add(referenceInfo);
        }

        resp.getWriter().write(gson.toJson(referencesInfoList));
    }
}
