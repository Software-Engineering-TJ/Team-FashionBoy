package web;

import com.google.gson.reflect.TypeToken;
import dao.impl.InstructorDaoImpl;
import dao.inter.InstructorDao;
import pojo.ExpScore;
import pojo.Notice;
import service.Impl.AdministrationServiceImpl;
import service.Impl.StudentServiceImpl;
import service.inter.AdministrationService;
import service.inter.StudentService;
import utils.RequestJsonUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServlet extends BaseServlet {
    AdministrationService administrationService = new AdministrationServiceImpl();
    StudentService studentService = new StudentServiceImpl();

    protected void getTakes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String studentNumber = reqObject.get("studentNumber");
        //获取学生——课程详细信息
        Map<String, Object> map = administrationService.GetTakesInfoByStudentNumber(studentNumber);
        //返回必要信息:这里直接用了管理员获取学生课程的方法，所以信息量足够，前端可以根据需要读取
        Map<String, Object> takes = new HashMap<>();
        takes.put("sections", map.get("sectionInformation"));

        resp.getWriter().write(gson.toJson(takes));
    }

    protected void getCourseNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");

        //获取需要的字段
        List<Notice> notices = studentService.getCourseNotice(courseID,classID);
        //抽取前端需要的通知属性
        List<Map<String,String>> noticesInfo = new ArrayList<>();
        for(Notice n : notices){
            Map<String,String> map = new HashMap<>();
            map.put("title",n.getTitle());
            map.put("content",n.getContent());
            map.put("date",n.getDate());
            //获取老师信息
            String instructorNumber = n.getInstructorNumber();
            Map<String,Object> instructorInfo = administrationService.SearchInstructorByInstructorNumber(instructorNumber);
            map.put("issuer", (String) instructorInfo.get("name"));
            //将该条信息加入到List
            noticesInfo.add(map);
        }

        resp.getWriter().write(gson.toJson(noticesInfo));
    }

    /**
     * 获取某个实验报告的成绩
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getGrade(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expName");
        String studentNumber = reqObject.get("studentNumber");
        //获取成绩，score可能是”助教尚未批改“or”报告尚未提交“or正常成绩
        String score = studentService.getExpScore(courseID,classID,expname,studentNumber);

        Map<String,String> map = new HashMap<>();
        map.put("grade",score);

        resp.getWriter().write(gson.toJson(map));
    }
}
