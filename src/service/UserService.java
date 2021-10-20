package service;

import pojo.User;

public interface UserService {
    User Login(String email,String password);
    Boolean ExistEmail(String email);  //检测email是否已被注册
    User Register(String email,String password); //一般用户注册时调用
    User Register(String email);  //使用默认密码注册,一般管理员调用
}
