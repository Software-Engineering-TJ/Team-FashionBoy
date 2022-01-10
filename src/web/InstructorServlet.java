package web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
//import com.mysql.cj.util.DnsSrv;
import dao.impl.ChoiceQuestionDaoImpl;
import dao.impl.CourseDaoImpl;
import dao.inter.ChoiceQuestionDao;
import dao.inter.CourseDao;
import dao.inter.PracticeDao;
import pojo.Attend;
import pojo.ExpScore;
import pojo.Student;
import pojo.*;
import pojo.logicEntity.StudentAttendanceInfo;
import service.Impl.AdministrationServiceImpl;
import service.Impl.InstructorServiceImpl;
import service.inter.AdministrationService;
import service.inter.InstructorService;
import utils.RequestJsonUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * InstructorServlet类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/27  20:57
 */

public class InstructorServlet extends BaseServlet{

    InstructorService instructorService = new InstructorServiceImpl();

    AdministrationService administrationService = new AdministrationServiceImpl();

    //获取该考勤已经签到和未签到的学生，要做表的not in操作
    protected void viewAttendance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String title = reqObject.get("AttendanceName");

        List<AttendScore> attendScoreList = instructorService.getAttendScoreByCourseIDAndClassIDAndTitle(courseID, classID, title);
        StudentAttendanceInfo studentAttendanceInfo = new StudentAttendanceInfo();

        //能在attendScoreList里的都是已签到的，未签到的在整个班级里除去即可

        for (AttendScore attendScore : attendScoreList) {
            String studentNumber = attendScore.getStudentNumber();
            String studentName = instructorService.getStudentByStudentNumber(studentNumber).getName();
            studentAttendanceInfo.addSubmitted(studentNumber, studentName);
        }

        //未签到的
        List<Takes> takesList = instructorService.getTakesNotInAttendScore(courseID, classID, title);
        for (Takes takes : takesList) {
            String studentNumber = takes.getStudentNumber();
            String studentName = instructorService.getStudentByStudentNumber(studentNumber).getName();
            studentAttendanceInfo.addUnSubmitted(studentNumber, studentName);
        }

        String json = JSONObject.toJSONString(studentAttendanceInfo);
        resp.getWriter().write(json);
    }

    //教师发布对抗练习，将题目列表交给PracticeServer
    protected void createQuestionList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        int size = Integer.parseInt(reqObject.get("size"));
        List<ChoiceQuestion> choiceQuestionList = instructorService.getRandomQuestionList(size);

        String json = JSONObject.toJSONString(choiceQuestionList);
        resp.getWriter().write(json);
    }

    /**
     * 教师获取教授的所有课程 √
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
     * 获取某门课程下，责任教师发布的实验信息 √
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
        String classID = reqObject.get("classID");
        List<Map<String,String>> map = instructorService.GetCourseExpInfo(courseID,classID);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师发布实验 √
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
        String endDate = reqObject.get("endDate");
        String expInfo = reqObject.get("expInfo");
        //实验发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String startDate = simpleDateFormat.format(new Date());

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.ReleaseExperiment(courseID,expname,classID,startDate,endDate,expInfo)==1){
            //发布实验成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师查看自己发布的实验信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
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
     * 教师发布公告 √
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
        String title = (String) reqObject.get("title");
        String content = (String) reqObject.get("content");
        String instructorNumber = (String) reqObject.get("issuer");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = new Date();
        String date= simpleDateFormat.format(startDate);

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
     * 教师撤回(删除)公告 √
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
     * 教师发布实验报告提交说明 √
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

    /**
     * 教师撤回实验报告提交说明 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void withdrawReportDesc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expName");
        String reportName = reqObject.get("reportName");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.DeleteReportDesc(courseID,classID,expname,reportName)==1){
            //撤回实验报告描述成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师修改自己发布的实验报告提交说明的相关信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void modifyReportDesc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expName");
        String newReportName = reqObject.get("newReportName");
        String newEndDate = reqObject.get("newEndDate");
        String newReportDescription = reqObject.get("newReportDescription");
        String newFileType = reqObject.get("newReportType");

        Map<String,Integer> map = new HashMap<>();
        int result = 0;
        if(instructorService.ModifyReportDesc(courseID,classID,expname,newReportName,newReportDescription,newEndDate,newFileType)==1){
            //修改实验报告描述成功
            result = 1;
        }
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 某门课的责任教师查看该课程下的所有班级信息 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getClassInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        List<Map<String,String>> map = instructorService.GetSectionInfoOfCourse(courseID);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     *
     * @param req 教师查看实验报告提交情况 √
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void viewSubmission(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String expname = reqObject.get("expname");

        //提交记录
        List<ExpScore> expScoreList = instructorService.getSubmittedStudentList(courseID,classID,expname);
        //存学号
        List<String> studentSubmitList = new ArrayList<>();
        //存学号、姓名、成绩（-1表示没评分）
        List<Map<String,Object>> submitted = new ArrayList<>();
        List<Map<String,Object>> unSubmitted = new ArrayList<>();
        //班级所有学生的学号
        List<String> studentList = instructorService.getStudentNumbersByCourseIDAndClassID(courseID,classID);
        if(expScoreList != null){
            //有人交了
            for(ExpScore expScore : expScoreList){
                //已提交的学生
                String studentNumber = expScore.getStudentNumber();
                studentSubmitList.add(studentNumber);
                //存信息
                Student student = administrationService.getStudentByStudentNumber(studentNumber);
                Map<String,Object> map = new HashMap<>();
                map.put("studentNumber",studentNumber);
                map.put("studentName",student.getName());
                map.put("score",expScore.getScore());
                map.put("fileUrl",expScore.getFileUrl());
                submitted.add(map);
            }
            for(String studentNumber : studentList){
                if(!studentSubmitList.contains(studentNumber)){
                    //存信息
                    Student student = administrationService.getStudentByStudentNumber(studentNumber);
                    Map<String,Object> map = new HashMap<>();
                    map.put("studentNumber",studentNumber);
                    map.put("studentName",student.getName());
                    map.put("score",-1);
                    unSubmitted.add(map);
                }
            }
        }else{
            //都没交
            for(String studentNumber : studentList){
                //存信息
                Student student = administrationService.getStudentByStudentNumber(studentNumber);
                Map<String,Object> map = new HashMap<>();
                map.put("studentNumber",studentNumber);
                map.put("studentName",student.getName());
                map.put("score",-1);
                unSubmitted.add(map);
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("submitted",submitted);
        map.put("unSubmitted",unSubmitted);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师发布签到
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void releaseSignIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String attendanceName = reqObject.get("attendanceName");
        String endTime = reqObject.get("endTime");
        //签到发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = simpleDateFormat.format(new Date());

        Map<String,Object> map = new HashMap<>();
        //签到信息插入到数据库
        if(instructorService.addAttend(courseID,classID,attendanceName,startTime,endTime)==1){
            map.put("result",1);
            map.put("msg","考勤发布成功");
        }else{
            map.put("result",0);
            map.put("msg","考勤命名重复");
        }
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师添加班级
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addSection(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String instructorNumber = reqObject.get("instructorNumber");
        int day = Integer.parseInt(reqObject.get("day"));
        int time = Integer.parseInt(reqObject.get("time"));
        int number = Integer.parseInt(reqObject.get("number"));
        if(administrationService.SearchInstructorByInstructorNumber(instructorNumber)==null){
            resp.getWriter().write("添加课程失败");
            return;
        }

        int ret = instructorService.addSection(courseID,instructorNumber, day, time,number);

        resp.getWriter().write(ret != -1 ? "添加课程成功" : "添加课程失败");
    }

    /**
     * 责任教师开设课程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addCourse(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        //因为传过来的参数有数组，所以先转化成Map<String,Object>
        Map<String, Object> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, Object>>() {
        }.getType());

        String title = (String)reqObject.get("title");
        String startDate = (String)reqObject.get("startDate");
        String endDate = (String)reqObject.get("endDate");
        String instructorNumber = (String)reqObject.get("instructorNumber");
        //实验信息
//        String experimentForm = (String) reqObject.get("experimentForm");
//        List<Map<String,Object>> courseExpInfoList = gson.fromJson(experimentForm, new TypeToken<List<Map<String, Object>>>() {
//        }.getType());
        List<Map<String,Object>> courseExpInfoList = (List<Map<String,Object>>) reqObject.get("experimentForm");
        int attendanceWeight =  (int)Math.round((Double)reqObject.get("attendanceWeight"));
        int practiceWeight = (int)Math.round((Double)reqObject.get("practiceWeight"));

        String courseID = instructorService.createCourse(title,instructorNumber,startDate,endDate);
        int result = 1;
        Map<String, Integer> map = new HashMap<>();
        if("".equals(courseID)){
            result = 0;
        }

        instructorService.addCourseExp(courseID,courseExpInfoList,attendanceWeight,practiceWeight);

        map.put("result",result);
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 教师或助教用于批改学生成绩
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void registerGrade(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String studentNumber = reqObject.get("studentNumber");
        String expname = reqObject.get("expname");
        float score = Float.parseFloat(reqObject.get("score"));
        String comment  = reqObject.get("comment");

        int result = 0;
        if(instructorService.registerGrade(courseID,classID,studentNumber,expname,score,comment)==1){
            result = 1;
        }

        Map<String,Integer> map = new HashMap<>();
        map.put("result",result);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 查看每个班的学生反馈
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void viewReflection(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");

        List<Reflection> reflectionList = instructorService.getAllReflection(courseID,classID);

        List<Map<String, Object>> reflectionInfoList = new ArrayList<>();

        if(reflectionList != null){
            for(Reflection reflection : reflectionList){
                Student student = administrationService.getStudentByStudentNumber(reflection.getStudentNumber());
                Map<String, Object> map = new HashMap<>();
                map.put("studentNumber",student.getStudentNumber());
                map.put("studentName",student.getName());
                map.put("content",reflection.getContent());
                map.put("date",reflection.getDate());

                reflectionInfoList.add(map);
            }
        }

        resp.getWriter().write(gson.toJson(reflectionInfoList));
    }

    /**
     * 教师查看对抗练习
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void viewPractice(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");

        List<Practice> practiceList = instructorService.getPracticeListOfSection(courseID,classID);

        List<Map<String,Object>> practiceInfoList = new ArrayList<>();

        if(practiceList != null){
            for(Practice practice: practiceList){
                Map<String, Object> map = new HashMap<>();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                map.put("practiceName",practice.getPracticeName());
                map.put("startTime",df.format(practice.getStartTime()));
                map.put("endTime",df.format(practice.getEndTime()));
                //当前时间
                Timestamp now = new Timestamp(System.currentTimeMillis());
                if(now.before(practice.getEndTime())){
                    map.put("status","正在进行");
                }else if(now.after(practice.getEndTime())){
                    map.put("status","已结束");
                }else{
                    map.put("status","尚未开始");
                }
                practiceInfoList.add(map);
            }
        }

        resp.getWriter().write(gson.toJson(practiceInfoList));
    }

    /**
     * 教师添加对抗练习题目
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addQuestion(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, Object> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, Object>>() {
        }.getType());

        String choiceQuestion = (String) reqObject.get("choiceQuestion");
        String choiceOption = (String) reqObject.get("choiceOption");
        int choiceDifficulty = ((Double)reqObject.get("choiceDifficulty")).intValue();
        String choiceAnswer = (String) reqObject.get("choiceAnswer");
        String choiceAnalysis = (String) reqObject.get("choiceAnalysis");
        double choiceScore = (Double) reqObject.get("choiceScore");

        ChoiceQuestionDao choiceQuestionDao = new ChoiceQuestionDaoImpl();
        int result = choiceQuestionDao.addQuestion(choiceQuestion,choiceOption,choiceDifficulty,choiceAnswer,choiceAnalysis,choiceScore);
        if(result == 1){
            resp.getWriter().write("添加成功");
        }else{
            resp.getWriter().write("添加失败");
        }
    }

    /**
     * 教师查看题库
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getQuestion(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");

        ChoiceQuestionDao choiceQuestionDao = new ChoiceQuestionDaoImpl();
        List<ChoiceQuestion> choiceQuestionList = choiceQuestionDao.getAllQuestions();
        if(choiceQuestionList == null){
            choiceQuestionList = new ArrayList<>();
        }

        resp.getWriter().write(gson.toJson(choiceQuestionList));
    }

    /**
     * 责任教师关闭课程
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void shutDownCourse(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");

        //关闭课程
        CourseDao courseDao = new CourseDaoImpl();
        int result= courseDao.UpdateFlagOfCourseByCourseID(courseID,-1);
        resp.getWriter().write("课程已关闭");
    }

}
