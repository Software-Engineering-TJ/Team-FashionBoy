package service.inter;

import pojo.Instructor;
import pojo.Student;
import pojo.User;

public interface UserService {
    User Login(String userNumber,String password);
    User ExistEmail(String email);  //检测email是否已被注册
    User ifActivated(String userNumber); //通过userNumber获取用户是否存在
    User Register(String email,String password); //一般用户注册时调用
    User Register(String email);  //使用默认密码注册,一般管理员调用
    Student getStudentByStudentNumber(String studentNumber);
    Instructor getInstructorByInstructorNumber(String instructorNumber);
    //学生自己修改
    int alterInstructorInformation(String instructorNumber, String email, String phoneNumber);
    //管理员修改
    int alterInstructorInformation(String instructorNumber, String email, String name, Integer sex, String phoneNumber);
    //学生自己修改
    int alterStudentInformation(String studentNumber, String email, String phoneNumber);
    //管理员修改
    int alterStudentInformation(String studentNumber, String email, String name, Integer sex, String phoneNumber);
}
