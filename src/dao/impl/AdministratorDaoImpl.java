package dao.impl;

import dao.AdministratorDao;
import pojo.Administrator;

/**
 * AdministratorDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/19  20:48
 */

public class AdministratorDaoImpl extends BaseDao implements AdministratorDao {
    @Override
    public Administrator QueryAdministratorByEmailAndPassword(String email, String password) {
        String sql = "select `email`,`password`,`nickname` from administrator where email = ? and password = ?";
        return queryForOne(Administrator.class,sql,email,password);
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
    public int SetNickname(String email, String nickname) {
        String sql = "update `administrator` set `nickname` = ? where (`email` = ?)";
        return update(sql,nickname,email);
    }
}
