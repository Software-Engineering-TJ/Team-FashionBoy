package web;

import com.google.gson.reflect.TypeToken;
import service.Impl.InstructorServiceImpl;
import service.inter.InstructorService;
import utils.RequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InstructorServlet类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/27  20:57
 */

public class InstructorServlet extends BaseServlet{

    InstructorService instructorService = new InstructorServiceImpl();

    /**
     * 教师获取教授的所有课程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getSections(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String instructorNumber = reqObject.get("instructorNumber");
        List<Map<String,String>> map = instructorService.GetSections(instructorNumber);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 获取某门课程下，责任教师发布的实验信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getExperimentInfo(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        List<Map<String,String>> map = instructorService.GetCourseExpInfo(courseID);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师发布实验
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void releaseExperiment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expName");
        String startDate = reqObject.get("startDate");
        String endDate = reqObject.get("endDate");
        String expInfo = reqObject.get("expInfo");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.ReleaseExperiment(courseID,classID,expname,startDate,endDate,expInfo)==1){
            //发布实验成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    protected void examineExperimentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expName");

        Map<String,String> map = instructorService.ExamineExperimentInfo(courseID,classID,expname);
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师修改自己发布的实验的相关信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void modifyExperimentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expName");
        String newEndDate = reqObject.get("newEndDate");
        String newExpInfo = reqObject.get("newExpInfo");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.ModifyExperiment(courseID,classID,expname,newEndDate,newExpInfo)==1){
            //修改已发布实验的内容成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师发布公告
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void releaseNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, Object> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, Object>>() {
        }.getType());

        String courseID = (String) reqObject.get("courseID");
        String classID = (String) reqObject.get("classID");
        Map<String,String> noticeInfo = (Map<String, String>) reqObject.get("noticeInfo");
        String title = noticeInfo.get("title");
        String content = noticeInfo.get("content");
        String instructorNumber = noticeInfo.get("issuer");
        String date = noticeInfo.get("date");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.ReleaseNotice(courseID,classID,instructorNumber,content,date,title)==1){
            //发布公告成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));

    }

    /**
     * 教师撤回(删除)公告
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void withdrawNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String instructorNumber = reqObject.get("instructorNumber");
        String date = reqObject.get("date");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.DeleteNotice(courseID,classID,instructorNumber,date)==1){
            //删除公告成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师发布实验报告提交说明
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void releaseReportDesc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, Object> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, Object>>() {
        }.getType());

        String courseID = (String) reqObject.get("courseID");
        String classID = (String) reqObject.get("classID");
        String expname = (String) reqObject.get("expName");
        Map<String,String> reportInfo = (Map<String, String>) reqObject.get("reportInfo");
        String reportName = reportInfo.get("reportName");
        String reportDescription = reportInfo.get("reportDescription");
        String startDate = reportInfo.get("startDate");
        String endDate = reportInfo.get("endDate");
        String fileType = reportInfo.get("reportType");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.ReleaseReportDesc(courseID,classID,expname,reportName,reportDescription,startDate,endDate,fileType)==1){
            //发布实验报告描述成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }
}
