package service.inter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import pojo.Takes;
import pojo.Teaches;

import java.util.List;
import java.util.Map;

public interface AdministrationService {
    //添加学生(有用)
    String AddStudent(String studentNumber,String email,String password, String name,int sex, String phoneNumber, String status);
    //删除学生
    boolean DeleteStudent(String email);
    //根据studentNumber返回学生姓名及所有课程信息
    Map<String,Object> getTakesByStudentNumber(String studentNumber);
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
    //根据管理员number获得管理员账户信息并返回
    List<JsonElement> getInfoByAdminNumber(String adminNumber);
    //更改管理员账号信息
    boolean changeAdministrationInfo(JsonObject jsonObject);
}
