package dao.inter;

import pojo.Administrator;

public interface AdministratorDao {
    Administrator QueryAdministratorByAdminNumberAndPassword(String adminNumber, String password);
    Administrator QueryAdministratorByEmail(String email);  //主要用于注册时检测
    Administrator QueryAdministratorByNumber(String number);
    int insertAdministrator(String adminNumber, String email, String password, String name);
    int updateAdministrator(String adminNumber, String email, String password, String name);

}
