package web;

import com.google.gson.Gson;
import pojo.User;
import service.Impl.UserServiceImpl;
import service.inter.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * UserServlet类的描述：
 * 处理用户请求：登录、注册、退出
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:47
 */

public class UserServlet extends BaseServlet{

    private UserService userService = new UserServiceImpl();

    /**
     * 获取email对应的账号的密码和激活状态
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserStatus(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        String userNumber = req.getParameter("userNumber");
        User user = userService.ifActivated(userNumber);

        String email,password;
        Integer status;

        if(user != null) {
            email = user.getEmail();
            password = user.getPassword();
            status = user.getStatus();
        }else{
            //如果没有该用户，则返回如下内容
            email = password = null;
            status = 0;
        }
        //需要返回的信息
        Map<String,Object> userInformation = new HashMap<>();
        userInformation.put("email",email);
        userInformation.put("password",password);
        userInformation.put("status",status);
        //转Json-String格式
        Gson gson = new Gson();
        String userInformationJson = gson.toJson(userInformation);
        //返回响应
        resp.getWriter().write(userInformationJson);
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        //userService查找了三个表：student、instructor
        String userNumber = req.getParameter("userNumber");
        String password = req.getParameter("password");
        User user = userService.Login(userNumber,password);

        if(user == null){
            //转到登录界面
        }else{
            //转到登录成功后的界面
        }
    }

    protected void Logout(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        //1.清除网页记录的用户信息
        //2.转到登录界面
    }

    protected void Register(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(userService.ExistEmail(email)==null){
            //执行注册失败操作
        }else{
            User user = userService.Register(email,password);
            //跳转到注册成功界面
        }
    }

}
