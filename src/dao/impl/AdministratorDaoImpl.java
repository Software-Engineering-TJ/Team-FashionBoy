package dao.impl;

import dao.inter.AdministratorDao;
import pojo.Administrator;

/**
 * AdministratorDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/19  20:48
 */

public class AdministratorDaoImpl extends BaseDao implements AdministratorDao {
    @Override
    public Administrator QueryAdministratorByNumber(String adminNumber) {
        String sql = "select * from administrator where adminNumber = ?";
        return queryForOne(Administrator.class,sql,adminNumber);
    }

    @Override
    public Administrator QueryAdministratorByAdminNumberAndPassword(String adminNumber, String password) {
        String sql = "select `email`,`password`,`name` from administrator where adminNumber = ? and password = ?";
        return queryForOne(Administrator.class,sql,adminNumber,password);
    }

    @Override
    public Administrator QueryAdministratorByEmail(String email) {
        String sql = "select `email`,`password`,`name` from administrator where email = ?";
        return queryForOne(Administrator.class,sql,email);
    }

    @Override
    public int InsertAdministrator(String email, String password) {
        String sql = "insert into administrator(`email`,`password`) values(?,?)";
        return update(sql,email,password);
    }

    @Override
    public int InsertAdministrator(String email) {
        String sql = "insert into administrator(`email`) values(?)";
        return update(sql,email);
    }

    @Override
    public int DeleteAdministrator(String email) {
        String sql = "delete from `administrator` where (`email` = ?);";
        return update(sql,email);
    }

    @Override
    public int SetNickname(String email, String name) {
        String sql = "update `administrator` set `name` = ? where (`email` = ?)";
        return update(sql,name,email);
    }
}
