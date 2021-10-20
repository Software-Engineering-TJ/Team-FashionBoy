package dao;

import pojo.Administrator;

public interface AdministratorDao {
    Administrator QueryAdministratorByEmailAndPassword(String email, String password);
    Administrator QueryAdministratorByEmail(String email);  //主要用于注册时检测
    int InsertAdministrator(String email,String password);
    int InsertAdministrator(String email);
    int DeleteAdministrator(String email);
    int SetNickname(String email,String nickname);
}
