package service.inter;

import pojo.Takes;
import pojo.Teaches;

import java.util.List;

public interface AdministrationService {
    //添加学生
    String AddStudent(String studentNumber,String email,String name,String phoneNumber,int sex);
    //删除学生
    boolean DeleteStudent(String email);
    //添加老师
    boolean AddInstructor(String email,String password);
    //删除老师
    boolean DeleteInstructor(String email);
    //查找某学生的所有课程
    List<Takes> SearchTakesOfStudent(String email);
    //查找某老师所有的课程
    List<Teaches> SearchTeachesOfInstructor(String email);
    //修改某学生在具体课程中的身份
    boolean SetStudentStatus(String email,String courseID,String classID,int status);
    //修改某老师在具体课程中的身份
    boolean SetInstructorStatus(String email,String courseID,String classID,int status);
}
