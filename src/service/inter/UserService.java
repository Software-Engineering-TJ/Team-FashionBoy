package service.inter;

import pojo.Instructor;
import pojo.Student;
import pojo.User;

public interface UserService {
    User ExistEmail(String email);  //检测email是否已被注册
    User ifActivated(String userNumber); //通过userNumber获取用户是否存在
    int alterUserInfo(String identity,String userNumber, String phoneNumber, String email);//用户修改个人信息
}
