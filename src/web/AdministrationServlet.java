package web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pojo.Instructor;
import service.inter.AdministrationService;
import service.Impl.AdministrationServiceImpl;
import utils.RequestJsonUtils;

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

    /**
     * 创建新的学生账号 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createStudent(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String name = reqObject.get("name");
        String sexString = reqObject.get("sex");
        int sex = 0;
        if(sexString.equals("男")){
            sex = 1;
        }
        String studentNumber = reqObject.get("studentNumber");
        String email = reqObject.get("email");
        String phoneNumber = reqObject.get("phoneNumber");

        String msg;        //记录添加结果:成功”success“、失败的话msg包含错误提示
        msg = administrationService.AddStudent(studentNumber,email,name,phoneNumber,sex);
        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     * 创建教师账号 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected  void createTeacher(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String name = reqObject.get("name");
        String sexString = reqObject.get("sex");
        int sex = 0;
        if(sexString.equals("男")){
            sex = 1;
        }
        String instructorNumber = reqObject.get("instructorNumber");
        String email = reqObject.get("email");
        String phoneNumber = reqObject.get("phoneNumber");

        String msg;        //记录添加结果:成功”success“、失败的话msg包含错误提示
        msg = administrationService.AddInstructor(instructorNumber,email,name,phoneNumber,sex);
        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("result",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     * 根据学生的学号获得他参与的课程信息 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTakesByStudentNumber(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String studentNumber = reqObject.get("studentNumber");
        //获取查询结果
        Map<String,Object> map = administrationService.GetTakesInfoByStudentNumber(studentNumber);
        //返回响应
        String mapJson = gson.toJson(map);
        resp.getWriter().write(mapJson);
    }

    /**
     * 根据老师的工号获得老师信息，用于搜索对应的老师 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTeacherByTeacherNumber(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String instructorNumber = reqObject.get("instructorNumber");
        //将信息填入map
        Map<String,Object> map = administrationService.SearchInstructorByInstructorNumber(instructorNumber);
        //返回响应
        String mapJson = gson.toJson(map);
        resp.getWriter().write(mapJson);
    }

    /**
     * 根据教师工号获得教授的课程信息 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTeachesByTeacherNumber(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String instructorNumber = reqObject.get("instructorNumber");
        //获取任课信息
        Map<String,Object> map = administrationService.GetTeachesInfoByInstructorNumber(instructorNumber);
        //JSON化后传给前端
        String mapJson = gson.toJson(map);
        resp.getWriter().write(mapJson);
    }

    /**
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void changeStudentDuty(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String studentNumber = reqObject.get("studentNumber");
        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String duty = reqObject.get("duty");
        //获取修改结果
        String msg =  administrationService.ChangeStudentDuty(studentNumber,courseID,classID,duty);
        Map<String,Object> map = new HashMap<>();
        map.put("result",msg);
        if ("学生".equals(duty)){
            map.put("duty","助教");
        }else {
            map.put("duty","学生");
        }
        //JSON化
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 查看某门课程下的责任教师
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkTeacherDuty(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        String courseID = req.getParameter("courseID");
        //获取责任教师的工号和姓名
        Map<String,Object> map = administrationService.CheckTeacherDuty(courseID);
        //JSON化
        resp.getWriter().write(gson.toJson(map));
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
