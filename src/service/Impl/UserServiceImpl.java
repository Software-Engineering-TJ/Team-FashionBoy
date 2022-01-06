package service.Impl;

//import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import dao.impl.*;
import dao.inter.*;
import pojo.*;
import pojo.logicEntity.ClassInfo;
import service.inter.UserService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:59
 */

public class UserServiceImpl implements UserService{

    private AdministratorDao administratorDao = new AdministratorDaoImpl();
    private InstructorDao  instructorDao = new InstructorDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();
    private ExpReportDao expReportDao = new ExpReportDaoImpl();
    private CourseExpDao courseExpDao = new CourseExpDaoImpl();
    private ExpScoreDao expScoreDao = new ExpScoreDaoImpl();
    private ReferenceDao referenceDao = new ReferenceDaoImpl();
    private TakesDao takesDao = new TakesDaoImpl();
    private TeachesDao teachesDao = new TeachesDaoImpl();
    private AttendDao attendDao = new AttendDaoImpl();
    private AttendScoreDao attendScoreDao = new AttendScoreDaoImpl();

    @Override
    public User ExistEmail(String email) {
        Student student = studentDao.QueryStudentByEmail(email);
        if(student == null){
            Instructor instructor = instructorDao.QueryInstructorByEmail(email);
            if(instructor == null){
                Administrator administrator = administratorDao.QueryAdministratorByEmail(email);
                if(administrator == null){
                    return null; //三个表都不存在时才返回“不存在”
                }
                return administrator;
            }
            return instructor;
        }
        return student;
    }

    @Override
    public User ifActivated(String userNumber) {
        Student student = studentDao.QueryStudentByStudentNumber(userNumber);
        if(student == null){
            Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(userNumber);
            if(instructor == null){
                return administratorDao.QueryAdministratorByNumber(userNumber);
            }else {
                return instructor;
            }
        }else{
            return student;
        }
    }

    @Override
    public int alterInstructorInformation(String instructorNumber, String email, String phoneNumber) {
        return instructorDao.updateInstructor(instructorNumber, email, phoneNumber);
    }

    @Override
    public int alterInstructorInformation(String instructorNumber, String email, String name, Integer sex, String phoneNumber) {
        return instructorDao.updateInstructor(instructorNumber, email, name, sex, phoneNumber);
    }

    @Override
    public int alterStudentInformation(String studentNumber, String email, String name, Integer sex, String phoneNumber) {
        return studentDao.updateStudent(studentNumber, email, name, sex, phoneNumber);
    }

    @Override
    public int alterStudentInformation(String studentNumber, String email, String phoneNumber) {
        return studentDao.updateStudent(studentNumber, email, phoneNumber);
    }

    @Override
    public int alterUserInfo(String identity, String userNumber, String phoneNumber, String email) {
        int result = 0;

        if(identity.equals("student")){
            //修改者是“学生”
            result = alterStudentInformation(userNumber,email,phoneNumber);
        }else if(identity.equals("instructor")){
            //修改者是“教师”
            result = alterInstructorInformation(userNumber,email,phoneNumber);
        }else{
            //修改者是“管理员”
//            result = administratorDao.updateAdministrator(userNumber,email,phoneNumber);
        }

        return result;
    }

    @Override
    public String getPassword(String identity, String userNumber) {
        User user = null;
        if(identity.equals("student")){
            user = studentDao.QueryStudentByStudentNumber(userNumber);
        }else if(identity.equals("instructor")){
            user = instructorDao.QueryInstructorByInstructorNumber(userNumber);
        }else{
            user = administratorDao.QueryAdministratorByNumber(userNumber);
        }
        return user.getPassword();
    }

    @Override
    public int changePassword(String identity, String userNumber, String newPassword) {
        if(identity.equals("student")){
            return studentDao.UpdatePasswordByStudentNumber(userNumber,newPassword);
        }else if(identity.equals("instructor")){
            return instructorDao.UpdatePasswordByInstructorNumber(userNumber,newPassword);
        }else{
            return administratorDao.UpdatePasswordByAdminNumber(userNumber,newPassword);
        }
    }

    @Override
    public void activateAccount(String identity, String email) {
        if(identity.equals("student")){
            studentDao.SetStatus(email,1);
        }else if(identity.equals("instructor")){
            instructorDao.SetStatus(email,1);
        }
    }

    @Override
    public List<Map<String, String>> getExpReports(String courseID, String classID) {
        List<Map<String,String>> expReportInfoList =  new ArrayList<>();
        //获取原生信息
        List<ExpReport> expReportList = expReportDao.QueryExpReportsByCourseIDAndClassID(courseID, classID);
        //进行字段加工处理
        for(ExpReport expReport : expReportList){
            Map<String,String> map = new HashMap<>();
            map.put("reportName",expReport.getReportName());
            map.put("reportDescription",expReport.getReportInfo());
            map.put("startDate",expReport.getStartDate());
            map.put("endDate",expReport.getEndDate());
            map.put("reportType",expReport.getFileType());
            map.put("expName",expReport.getExpname());
            //获取实验的成绩占比
            CourseExp courseExp = courseExpDao.QueryCourseExpByCourseIDAndExpname(expReport.getCourseID(),expReport.getExpname());
            map.put("weight",courseExp.getPercent()+"%");
            expReportInfoList.add(map);
        }
        return expReportInfoList;
    }

    @Override
    public List<ExpScore> getExpScoresOfExpname(String courseID, String classID, String expname) {

        //找到提交作业的学生记录
        List<ExpScore> expScoreList = expScoreDao.QueryExpScoresByExperiment(courseID,expname,classID);

        return expScoreList;
    }

    @Override
    public List<Reference> getReferencesOfSection(String courseID, String classID) {
        //该课程下的所有参考资料
        return referenceDao.QueryReferencesByCourseIDAndClassID(courseID,classID);
    }

    @Override
    public ClassInfo getClassInfo(String courseID, String classID) {
        ClassInfo classInfo = new ClassInfo();
        List<Teaches> teachesList = teachesDao.QueryTeachesByCourseIDAndClassID(courseID, classID);
        for (Teaches teaches : teachesList) {
            String instrutorNumber = teaches.getInstructorNumber();
            Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(instrutorNumber);
            classInfo.addInstructor(instructor);
        }

        List<Takes> takesList = takesDao.QueryTakesByCourseIDAndClassID(courseID, classID);
        for (Takes takes : takesList) {
            String studentNumber = takes.getStudentNumber();
            int status = takes.getStatus();//0,1
            Student person = studentDao.QueryStudentByStudentNumber(studentNumber);
            if (status == 0) {
                //学生
                classInfo.addStudent(person);
            }
            else if (status == 1) {
                //助教
                classInfo.addAssistant(person);
            }
        }

        return classInfo;
    }

    @Override
    public List<Attend> getAttendInfo(String courseID, String classID) {
        return attendDao.QueryAttendsByCourseIDAndClassID(courseID, classID);
    }

     @Override
    public List<AttendScore> getAttendScoreByCourseIDAndClassIDAndStudentNumber(String courseID, String classID, String studentNumber) {
        return attendScoreDao.getAttendScoreByCourseIDAndClassIDAndStudentNumber(courseID, classID, studentNumber);
    }

    @Override
    public Attend getAttendByCourseIDAndClassIDAndTitle(String courseID, String classID, String title) {
        return attendDao.QueryAttendByCourseIDAndClassIDAndTitle(courseID, classID, title);
    }

    @Override
    public Boolean judgeAttendScoreIfExist(String courseID, String classID, String title, String studentNumber) {
        AttendScore attendScore = attendScoreDao.getAttendScoreByCourseIDAndClassIDAndTitleAndStudentNumber(courseID, classID, title, studentNumber);
        return attendScore != null;
    }

}
