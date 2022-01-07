package service.Impl;

import dao.impl.*;
import dao.inter.*;
import pojo.*;
import service.inter.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/24  19:18
 */

public class StudentServiceImpl implements StudentService {
    private NoticeDao noticeDao = new NoticeDaoImpl();
    private ExpScoreDao expScoreDao = new ExpScoreDaoImpl();
    private TakesDao takesDao = new TakesDaoImpl();
    private AttendScoreDao attendScoreDao = new AttendScoreDaoImpl();
    private CourseExpDao courseExpDao = new CourseExpDaoImpl();
    private ExperimentDao experimentDao = new ExperimentDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();

    @Override
    public List<Notice> getCourseNotice(String courseID, String classID) {
        //获取所有的通知
        return noticeDao.QueryNoticesByCourseIDAndClassID(courseID,classID);
    }

    @Override
    public ExpScore getExpScore(String courseID, String classID, String expname, String studentNumber) {
        String score = null;
        //查找数据库中该学生提交的实验对应的成绩信息。
        ExpScore expScore = expScoreDao.QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(courseID, classID, expname, studentNumber);

        return expScore;
    }

    @Override
    public int recordCommit(String courseID, String classID, String expname, String studentNumber,String fileUrl) {
        //首次提交，则“添加”提交记录
        if(expScoreDao.QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(courseID, classID, expname, studentNumber)==null){
            return expScoreDao.InsertExpScore(studentNumber,courseID,expname,classID,fileUrl);
        }
        //后续重交，则覆盖文件信息
        return expScoreDao.UpdateFileUrl(courseID, classID, expname, studentNumber, fileUrl);
    }

    @Override
    public String getDuty(String courseID, String classID, String studentNumber) {
        Takes takes = takesDao.QueryTakesByCourseIDAndClassIDAndStudentNumber(courseID,classID,studentNumber);
        return (takes.getStatus()==0)?"学生":"助教";
    }

    @Override
    public int addAttendScore(String courseID, String classID, String title, String studentNumber, int onTime) {
        return attendScoreDao.InsertAttendScore(courseID, classID, title, studentNumber, onTime);
    }

    @Override
    public int deleteCommit(String fileUrl) {
        return expScoreDao.DeleteByFileUrl(fileUrl);
    }

    @Override
    public List<CourseExp> getCoursesByCourseID(String courseID) {
        return courseExpDao.QueryCourseExpsByCourseID(courseID);
    }
    @Override
    public List<ExpScore> getAllExpScore(String courseID, String classID, String studentNumber) {
        return expScoreDao.QueryExpScoreByCourseIDAndClassIDAndStudentNumber(courseID, classID, studentNumber);
    }
    @Override
    public List<Experiment> getExperimentByCourseIDAndClassID(String courseID, String classID) {
        return experimentDao.QueryExperimentsByCourseIDAndClassID(courseID, classID);
    }

    @Override
    public CourseExp getCourseExpByCourseIDAndExpname(String courseID, String expname) {
        return courseExpDao.QueryCourseExpByCourseIDAndExpname(courseID, expname);
    }

    @Override
    public List<Takes> getTakesListByStudentNumber(String studentNumber) {
        return takesDao.QueryTakesByStudentNumber(studentNumber);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.QueryAllStudents();
    }

}
