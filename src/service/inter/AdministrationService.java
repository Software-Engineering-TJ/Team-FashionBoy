package service.inter;

import pojo.*;

import java.util.List;
import java.util.Map;

public interface AdministrationService {
    //查找学生
    Student getStudentByStudentNumber(String studentNumber);
    //获取管理员身份信息
    Administrator getAdministrationInfo(String adminNumber);
    //辅助函数，检查注册邮箱是否已被使用
    boolean EmailExists(String email);
    //添加学生(有用)
    String AddStudent(String studentNumber,String email,String name,String phoneNumber,int sex);
    //删除学生
    boolean DeleteStudent(String email);
    //根据studentNumber返回学生姓名及所有课程信息
    Map<String,Object> GetTakesInfoByStudentNumber(String studentNumber);
    //添加老师
    String AddInstructor(String instructorNumber,String email,String name,String phoneNumber,int sex);
    //搜索老师相关信息
    Map<String,Object> SearchInstructorByInstructorNumber(String instructorNumber);
    //根据教师工号获取教授的课程信息
    Map<String, Object> GetTeachesInfoByInstructorNumber(String instructorNumber);
    //根据学号、课程号、班号来修改duty
    String ChangeStudentDuty(String studentNumber,String courseID,String classID,String duty);
    //获取某个课程的责任教师
    Map<String,Object> CheckTeacherDuty(String courseID);
    //删除教师
    boolean DeleteInstructor(String email);
    //查找某学生的所有课程
    List<Takes> SearchTakesOfStudent(String email);
    //查找某老师所有的课程
    List<Teaches> SearchTeachesOfInstructor(String email);
    //修改某学生在具体课程中的身份
    boolean SetStudentStatus(String email,String courseID,String classID,int status);
    //修改某老师在具体课程中的身份
    boolean SetInstructorStatus(String email,String courseID,String classID,int status);
    //管理员修改学生的信息：学号，姓名，电话，邮箱
    int alterStudentInformationByAdmin(String studentNumber, String name, String phoneNumber, String email, Integer sex);
}
