package service.inter;

import pojo.*;
import pojo.logicEntity.ClassInfo;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface UserService {
    User ExistEmail(String email);  //检测email是否已被注册
    User ifActivated(String userNumber); //通过userNumber获取用户是否存在
    int alterUserInfo(String identity,String userNumber, String phoneNumber, String email);//用户修改个人信息
    //学生自己修改
    int alterInstructorInformation(String instructorNumber, String email, String phoneNumber);
    //管理员修改
    int alterInstructorInformation(String instructorNumber, String email, String name, Integer sex, String phoneNumber);
    //学生自己修改
    int alterStudentInformation(String studentNumber, String email, String phoneNumber);
    //管理员修改
    int alterStudentInformation(String studentNumber, String email, String name, Integer sex, String phoneNumber);
    //获取用户密码
    String getPassword(String identity,String userNumber);
    //修改用户密码:返回修改结果
    int changePassword(String identity,String userNumber,String newPassword);
    //激活用户
    void activateAccount(String identity,String email);
    //获取某个课程班级下的所欲实验报告描述信息
    List<Map<String,String>> getExpReports(String courseID, String classID);
    //获取班级某个实验的所有提交的实验报告
    List<ExpScore> getExpScoresOfExpname(String courseID, String classID, String expname);
    //获取课程参考资料
    List<Reference> getReferencesOfSection(String courseID, String classID);
    ClassInfo getClassInfo(String courseID, String classID);
    List<Attend> getAttendInfo(String courseID, String classID);
    List<AttendScore> getAttendScoreByCourseIDAndClassIDAndStudentNumber(String courseID, String classID, String studentNumber);
    Attend getAttendByCourseIDAndClassIDAndTitle(String courseID, String classID, String title);
    Boolean judgeAttendScoreIfExist(String courseID, String classID, String title, String studentNumber);
}
