package service.inter;

import pojo.Student;
import pojo.User;

public interface UserService {
    //登陆邮箱时，根据学号获取目标邮箱
    Student getStudentByStudentNumber(String studentNumber);
    User Login(String userNumber,String password);
    User ExistEmail(String email);  //检测email是否已被注册
    User ifActivated(String userNumber); //通过userNumber获取激活情况
    User Register(String email,String password); //一般用户注册时调用
    //学生自行修改个人信息
    int alterStudentInformation(String studentNumber, String email, String password, Integer sex, String phoneNumber);
    //教师自行修改个人信息
    int alterInstructorInformation(String InstructorNumber, String email, String password, Integer sex, String phoneNumber);
    User Register(String email);  //使用默认密码注册,一般管理员调用
}
