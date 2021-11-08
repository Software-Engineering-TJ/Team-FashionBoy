package dao.impl;

import com.google.gson.JsonObject;
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
    public Administrator QueryAdministratorByAdminNumberAndPassword(String adminNumber, String password) {
        String sql = "select `email`,`password`,`name` from administrator where adminNumber = ? and password = ?";
        return queryForOne(Administrator.class,sql,adminNumber,password);
    }

    @Override
    public Administrator QueryAdministratorByEmail(String email) {
        String sql = "select `email`,`password`,`name` from administrator where email = ?";
        return queryForOne(Administrator.class,sql,email);
    }

    public Administrator QueryAdministratorByNumber(String number) {
        String sql = "select `email`,`password`,`name` from administrator where adminNumber = ?";
        return queryForOne(Administrator.class,sql,number);
    }

    public int updateAdministrator(String adminNumber, String email, String password, String name) {
        String sql = "update administrator set `email` = ?, `password` = ?, `name` = ? where adminNumber = ?";
        return update(sql, email, password, name, adminNumber);
    }

    //测试用
    public int insertAdministrator(String adminNumber, String email, String password, String name) {
        String sql = "insert ignore into administrator(`adminNumber`, `email`, `password`, `name`) values(?, ?, ?, ?)";
        return update(sql, adminNumber, email, password, name);
    }

}
