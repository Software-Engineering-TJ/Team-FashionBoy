package service.inter;

import pojo.User;

public interface UserService {
    User Login(String userNumber,String password);
    User ExistEmail(String email);  //检测email是否已被注册
    User ifActivated(String userNumber); //通过userNumber获取激活情况
    User Register(String email,String password); //一般用户注册时调用
    int alterStudentInformation(String studentNumber, String email, String password, Integer sex, String phoneNumber);
    int alterInstructorInformation(String InstructorNumber, String email, String password, Integer sex, String phoneNumber);
    User Register(String email);  //使用默认密码注册,一般管理员调用
}
