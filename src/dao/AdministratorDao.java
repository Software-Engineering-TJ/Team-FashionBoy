package dao;

import pojo.Administrator;

public interface AdministratorDao {
    Administrator QueryAdministratorByEmailAndPassword(String email, String password);
    int InsertAdministrator(String email,String password);
    int InsertAdministrator(String email);
    int DeleteAdministrator(String email);
    int SetNickname(String email,String nickname);
}