package service.Impl;

import com.google.gson.reflect.TypeToken;
import dao.impl.*;
import dao.inter.*;
import pojo.*;
import service.inter.InstructorService;
import utils.RequestJsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InstructorServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/27  22:38
 */

public class InstructorServiceImpl implements InstructorService {

    private TeachesDao teachesDao = new TeachesDaoImpl();
    private CourseDao courseDao = new CourseDaoImpl();
    private CourseExpDao courseExpDao = new CourseExpDaoImpl();
    private ExperimentDao experimentDao = new ExperimentDaoImpl();
    private NoticeDao noticeDao = new NoticeDaoImpl();
    private ExpReportDao expReportDao = new ExpReportDaoImpl();

    @Override
    public List<Map<String, String>> GetSections(String instructorNumber) {
        //创建存储sections信息的列表
        List<Map<String,String>> sectionInfoList = new ArrayList<>();
        List<Teaches> teachesList = teachesDao.QueryTeachesByInstructorNumber(instructorNumber);

        for(Teaches t : teachesList){
            Map<String,String> sectionInfo = new HashMap<>();
            //获取courseID和classID
            sectionInfo.put("courseID",t.getCourseID());
            sectionInfo.put("classID",t.getClassID());
            //获取课程名
            Course course = courseDao.QueryCourseByCourseID(t.getCourseID());
            sectionInfo.put("title",course.getTitle());
            //获取duty信息
            String duty = "教师";
            if(course.getInstructorNumber().equals(instructorNumber)){
                duty = duty + ",责任教师";
            }
            sectionInfo.put("duty",duty);

            sectionInfoList.add(sectionInfo);
        }

        return sectionInfoList;
    }

    @Override
    public List<Map<String, String>> GetCourseExpInfo(String courseID) {
        List<Map<String,String>> courseExpInfoList = new ArrayList<>();

        List<CourseExp> courseExpList = courseExpDao.QueryCourseExpsByCourseID(courseID);
        //开始整理信息
        for(CourseExp c : courseExpList){
            Map<String,String> courseExpInfo = new HashMap<>();
            courseExpInfo.put("expName",c.getExpname());
            courseExpInfo.put("priority",Integer.toString(c.getPriority()));
            courseExpInfo.put("difficulty",Integer.toString(c.getDifficulty()));
            courseExpInfo.put("weight",c.getPercent()+"%");
            //加入到信息列表
            courseExpInfoList.add(courseExpInfo);
        }

        return courseExpInfoList;
    }

    @Override
    public int ReleaseExperiment(String courseID, String expname, String classID,
                                     String startDate, String endDate, String expInfo) {
        return experimentDao.InsertExperiment(courseID, expname, classID, startDate, endDate, expInfo);
    }

    @Override
    public Map<String, String> ExamineExperimentInfo(String courseID, String classID, String expname) {
        Map<String,String> experimentInfo = new HashMap<>();
        //根据主码锁定实验
        Experiment experiment = experimentDao.QueryExperiment(courseID, classID, expname);
        experimentInfo.put("startDate",experiment.getStartDate());
        experimentInfo.put("endDate",experiment.getEndDate());
        experimentInfo.put("expInfo",experiment.getExpInfo());

        return experimentInfo;
    }

    @Override
    public int ModifyExperiment(String courseID, String classID, String expname, String endDate, String expInfo) {
        return experimentDao.UpdateExperiment(courseID, classID, expname, endDate, expInfo);
    }

    @Override
    public int ReleaseNotice(String courseID, String classID, String instructorNumber, String content, String date, String title) {
        return noticeDao.InsertNotice(courseID, classID, date, content, instructorNumber, title);
    }

    @Override
    public int DeleteNotice(String courseID, String classID, String instructorNumber, String date) {
        return noticeDao.DeleteNotice(courseID, classID, instructorNumber, date);
    }

    @Override
    public int ReleaseReportDesc(String courseID, String classID, String expname, String reportName, String reportInfo, String startDate, String endDate, String fileType) {
        return expReportDao.InsertExpReport(courseID,expname,classID,reportName,endDate,reportInfo,fileType,startDate);
    }

    @Override
    public int DeleteReportDesc(String courseID, String classID, String expname, String reportName) {
        return expReportDao.DeleteExpReport(courseID, classID, expname, reportName);
    }
}
