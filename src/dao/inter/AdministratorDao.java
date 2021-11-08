package dao.inter;

import pojo.Administrator;

public interface AdministratorDao {
    Administrator QueryAdministratorByAdminNumberAndPassword(String adminNumber, String password);
    Administrator QueryAdministratorByEmail(String email);  //主要用于注册时检测
    int InsertAdministrator(String email,String password);
    int InsertAdministrator(String email);
    int DeleteAdministrator(String email);
    int SetNickname(String email,String name);
}
