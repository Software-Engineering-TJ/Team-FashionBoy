package web;

import pojo.User;
import service.Impl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * UserServlet类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:47
 */

public class UserServlet extends BaseServlet{

    private UserService userService = new UserServiceImpl();

    protected void Login(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        //userService查找了三个表：student、instructor、administrator
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userService.Login(email,password);

        if(user == null){
            //转到登录界面
        }else{
            //转到登录成功后的界面
        }
    }

    protected void Register(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        String email = req.getParameter("email");
        String password = req.getParameter("password");


        if(userService.ExistEmail(email)){
            //执行注册失败操作
        }else{
            User user = userService.Register(email,password);
            //跳转到注册成功界面
        }

    }
}
