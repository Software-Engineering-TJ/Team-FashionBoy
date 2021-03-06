package web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import dao.impl.*;
import dao.inter.*;
import pojo.*;
import pojo.logicEntity.ViewExperimentInfo;
import service.Impl.AdministrationServiceImpl;
import service.Impl.InstructorServiceImpl;
import service.Impl.StudentServiceImpl;
import service.inter.AdministrationService;
import service.inter.InstructorService;
import service.inter.StudentService;
import utils.RequestJsonUtils;

import javax.naming.directory.Attributes;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentServlet extends BaseServlet {
    AdministrationService administrationService = new AdministrationServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    InstructorService instructorService = new InstructorServiceImpl();

    protected void viewExperiment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");

        List<Experiment> experimentList = studentService.getExperimentByCourseIDAndClassID(courseID, classID);
        List<ViewExperimentInfo> viewExperimentInfoList = new ArrayList<ViewExperimentInfo>();

        for (Experiment experiment : experimentList) {
            ViewExperimentInfo viewExperimentInfo = new ViewExperimentInfo();

            String expname = experiment.getExpname();
            viewExperimentInfo.setExpName(expname);
            viewExperimentInfo.setStartDate(experiment.getStartDate());
            viewExperimentInfo.setEndDate(experiment.getEndDate());
            viewExperimentInfo.setExpInfo(experiment.getExpInfo());

            CourseExp courseExp = studentService.getCourseExpByCourseIDAndExpname(courseID, expname);
            viewExperimentInfo.setPriority(courseExp.getPriority());
            viewExperimentInfo.setDifficulty(courseExp.getDifficulty());
            viewExperimentInfo.setWeight(courseExp.getPercent());

            viewExperimentInfoList.add(viewExperimentInfo);
        }

        String json = JSONObject.toJSONString(viewExperimentInfoList);
        resp.getWriter().write(json);
    }

    protected void getTakes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String studentNumber = reqObject.get("studentNumber");
        //????????????????????????????????????
        Map<String, Object> map = administrationService.GetTakesInfoByStudentNumber(studentNumber);
        //??????????????????:???????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

        //?????????????????????
        List<Notice> notices = studentService.getCourseNotice(courseID,classID);
        //?????????????????????????????????
        List<Map<String,String>> noticesInfo = new ArrayList<>();
        for(Notice n : notices){
            Map<String,String> map = new HashMap<>();
            map.put("title",n.getTitle());
            map.put("content",n.getContent());
            map.put("date",n.getDate());
            //??????????????????
            String instructorNumber = n.getInstructorNumber();
            Map<String,Object> instructorInfo = administrationService.SearchInstructorByInstructorNumber(instructorNumber);
            map.put("issuer", (String) instructorInfo.get("name"));
            //????????????????????????List
            noticesInfo.add(map);
        }

        resp.getWriter().write(gson.toJson(noticesInfo));
    }

    /**
     * ????????????????????????????????? ???
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
        //???????????????score?????????????????????????????????or????????????????????????or????????????
        ExpScore expScore = studentService.getExpScore(courseID,classID,expname,studentNumber);

        String score = "";
        String comment= "";
        if(expScore == null){
            //???????????????????????????????????????????????????
            score = "??????????????????";
            comment = "????????????????????????????????????";
        }else{
            if(expScore.getScore()==-1) {
                //"-1"???????????????????????????
                score = "??????????????????";
                comment = "????????????????????????????????????????????????^-^";
            }else{
                //????????????
                score = Float.toString(expScore.getScore());
                comment = expScore.getComment();
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("grade",score);
        map.put("comment",comment);

        resp.getWriter().write(gson.toJson(map));
    }

    protected void getDuty(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String studentNumber = reqObject.get("studentNumber");

        Map<String,String> map = new HashMap<>();
        map.put("Duty",studentService.getDuty(courseID,classID,studentNumber));

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * ??????
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void signIn(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String title = reqObject.get("AttendanceName");
        String studentNumber = reqObject.get("studentNumber");
        //???????????????????????????
        Date currentTime = new Date();
        //??????????????????
        Date endTime = null;
        AttendDao attendDao = new AttendDaoImpl();
        Attend attend = attendDao.QueryAttendByCourseIDAndClassIDAndTitle(courseID,classID,title);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            endTime = simpleDateFormat.parse(attend.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //??????????????????
        int onTime = 0;
        if(currentTime.before(endTime)){
            onTime = 1;
        }
        //??????????????????
        Map<String,Object> map = new HashMap<>();
        if(studentService.addAttendScore(courseID,classID,title,studentNumber,onTime)==1){
            map.put("result",1);
        }else{
            map.put("result",0);
        }
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * ???????????????????????? ???
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getWeightOfGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseId = reqObject.get("courseID");
        List<CourseExp> courseExpList = studentService.getCoursesByCourseID(courseId);

        resp.getWriter().write(JSONObject.toJSONString(courseExpList));
    }

    /**
     * ????????????????????????????????? ???
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getExpGrades(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String studentNumber = reqObject.get("studentNumber");

        //???????????????????????????????????????
        List<Experiment> experimentList = studentService.getExperimentByCourseIDAndClassID(courseID,classID);
        //?????????????????????
        List<Map<String, Object>> expGradeInfoList = new ArrayList<>();
        if(experimentList != null){
            for(Experiment experiment : experimentList){
                Map<String, Object> map = studentService.getGradeAndRankingOfExperiment(experiment,studentNumber);
                expGradeInfoList.add(map);
            }
        }

        resp.getWriter().write(JSONObject.toJSONString(expGradeInfoList));
    }

    /**
     * ??????????????????????????????????????????
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTotalGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String studentNumber = reqObject.get("studentNumber");

        float attendGrade = studentService.getGradeOfAttendance(courseID,classID,studentNumber);
        float practiceGrade = studentService.getGradeOfPractice(courseID,classID,studentNumber);
        float expGrade = studentService.getGradeOfExperiment(courseID,classID,studentNumber);

        float totalGrade = attendGrade+practiceGrade+expGrade;

        Map<String,Float> map = new HashMap<>();
        map.put("attendScore",attendGrade);
        map.put("practiceScore",practiceGrade);
        map.put("expScore",expGrade);
        map.put("totalScore",totalGrade);

        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * ????????????????????????????????????????????????
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void viewPracticeStu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String studentNumber = reqObject.get("studentNumber");

        PracticeDao practiceDao = new PracticeDaoImpl();
        PracticeScoreDao practiceScoreDao = new PracticeScoreDaoImpl();

        //???????????????????????????
        List<Map<String, Object>> practiceInfoList = new ArrayList<>();

        //????????????????????????????????????
        List<Practice> practiceList = practiceDao.QueryPracticesByCourseIDAndClassID(courseID,classID);

        //?????????????????????????????????????????????????????????????????????
        for(Practice practice : practiceList){
            Map<String, Object> map = new HashMap<>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("practiceName",practice.getPracticeName());
            map.put("startTime",dateFormat.format(practice.getStartTime()));
            map.put("endTime",dateFormat.format(practice.getEndTime()));
            //????????????
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if(now.before(practice.getEndTime())){
                map.put("status","????????????");
            }else if(now.after(practice.getEndTime())){
                map.put("status","?????????");
            }else{
                map.put("status","????????????");
            }

            float grade = 0;
            //????????????????????????????????????????????????
            PracticeScore practiceScore = practiceScoreDao.QueryPracticeScoreByCourseIDAndClassIDAndPracticeNameAndStudentNumber(courseID,classID,practice.getPracticeName(),studentNumber);
            if(practiceScore == null){
                //??????????????????????????????????????????0
                map.put("grade",grade);
                practiceInfoList.add(map);
                continue;
            }
            //????????????????????????
            List<PracticeScore> practiceScoreList = practiceScoreDao.QueryPracticeScoreByGroup(courseID,classID,practice.getPracticeName(), practiceScore.getGroupNumber());
            //?????????
            if(practiceScoreList != null){
                for(int i=0;i<practiceScoreList.size();i++){
                    if(practiceScoreList.get(i).getStudentNumber().equals(practiceScore.getStudentNumber())){
                        //???????????????????????????i(?????????100????????????60????????????20)
                        grade = ((3-i)*2-1)*20;
                        map.put("grade",grade);
                        practiceInfoList.add(map);
                        break;
                    }
                }
            }
        }

        resp.getWriter().write(gson.toJson(practiceInfoList));
    }

    /**
     * ?????????????????????
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void writeReflection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String studentNumber = reqObject.get("studentNumber");
        String content = reqObject.get("content");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(date);

        if(studentService.writeReflection(courseID,classID,studentNumber,content,dateStr)==1){
            resp.getWriter().write("????????????");
        }else{
            resp.getWriter().write("????????????????????????");
        }
    }
}
