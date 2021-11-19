package service.inter;

import pojo.Instructor;
import pojo.Student;
import pojo.User;

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
}
