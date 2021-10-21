package web;

import pojo.Takes;
import pojo.Teaches;
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

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        //1.根据req中的具体请求，确定是添加老师or学生
        String identity = req.getParameter("identity"); //用于标明希望添加的用户的身份的字段
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

    protected void DeleteUser(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {

        String email = req.getParameter("email");
        //1.根据req中的具体请求，确定删除的是老师or学生
        String identity = req.getParameter("identity");

        boolean result;
        if(identity.equals("student")){
            result = administrationService.DeleteStudent(email);
        }else{
            result = administrationService.DeleteInstructor(email);
        }

        if(!result){
            //删除失败，提醒重新尝试
        }else{
            //成功
        }

    }

    protected void SetUserSectionStatus(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {

        String email = req.getParameter("email");
        String courseID = req.getParameter("courseID");
        String classID = req.getParameter("classID");
        String status = req.getParameter("status");  //修改后的身份
        //获取目标账号身份（学生or老师）
        String identity = req.getParameter("identity");

        boolean result;
        if(identity.equals("student")){
            result = administrationService.SetStudentStatus(email,courseID,classID,Integer.parseInt(status));
        }else{
            result = administrationService.SetInstructorStatus(email,courseID,classID,Integer.parseInt(status));
        }

        if(!result){
            //修改失败
        }else{
            //修改成功
        }

    }
}
