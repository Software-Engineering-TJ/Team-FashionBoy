package web;

import service.AdministrationService;
import service.Impl.AdministrationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AdministrationServlet类的描述：
 * 处理管理员的请求：添加/删除/修改老师和学生的账户、浏览学生和老师相关的课程、设置老师和学生在具体课程的权限
 * @author 黄金坤（HJK）
 * @since 2021/10/21  14:03
 */

public class AdministrationServlet extends BaseServlet{

    private AdministrationService administrationService = new AdministrationServiceImpl();

    protected void AddUser(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        //1.根据req中的具体请求，确定是添加老师or学生
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String identity = req.getParameter("identity");
        boolean result;
        if(identity.equals("student")){
            result = administrationService.AddStudent(email,password);
        }else{
            result = administrationService.AddInstructor(email,password);
        }

        if(!result){
            //添加失败————因为有可能出现email已被使用的情况
            //返回警告信息
        }else{
            //添加成功
        }
    }



}
