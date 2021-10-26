package web;

import com.google.gson.Gson;
import service.inter.AdministrationService;
import service.Impl.AdministrationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AdministrationServlet类的描述：
 * 处理管理员的请求：添加/删除/修改老师和学生的账户、浏览学生和老师相关的课程、设置老师和学生在具体课程的权限
 * @author 黄金坤（HJK）
 * @since 2021/10/21  14:03
 */

public class AdministrationServlet extends BaseServlet{

    private AdministrationService administrationService = new AdministrationServiceImpl();

    protected void createStudent(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {

        String name = req.getParameter("name");
        String sexString = req.getParameter("sex");
        int sex = 0;
        if(sexString.equals("男")){
            sex = 1;
        }
        String studentNumber = req.getParameter("studentNumber");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");

        String msg;        //记录添加结果:成功”success“、失败的话msg包含错误提示
        msg = administrationService.AddStudent(studentNumber,email,name,phoneNumber,sex);
        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
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
