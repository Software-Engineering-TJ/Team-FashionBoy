package service.Impl;

//import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import dao.impl.*;
import dao.inter.*;
import pojo.*;
import service.inter.UserService;

import javax.servlet.http.HttpServletRequest;
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
    public List<Map<String, Object>> getFilesOfExpname(Path directory,String courseID, String classID, String expname) {

        //找到提交作业的学生记录
        List<ExpScore> expScoreList = expScoreDao.QueryExpScoresByExperiment(courseID,expname,classID);
        List<Map<String,Object>> fileInfoList = new ArrayList<>();
        for(ExpScore expScore : expScoreList){
            //学号
            String studentNumber = expScore.getStudentNumber();
            //文件
            File file = null;
            //文件路径
            String url = "";
            Path fileDirectory = Paths.get(directory.toString(),studentNumber);
            //获取文件夹下的所有文件（其实只有一个文件）
            File fileList = new File(fileDirectory.toString());
            File[] files = fileList.listFiles();
            if(files.length!=0){
                //学生的文件
                file = files[0];
                url = courseID+"/"+classID+"/"+expname+"/"+file.getName();
            }

            Map<String,Object> fileInfo = new HashMap<>();
            fileInfo.put("studentNumber",studentNumber);
            fileInfo.put("file",file);
            fileInfo.put("url",url);

            fileInfoList.add(fileInfo);
        }

        return fileInfoList;
    }
}
