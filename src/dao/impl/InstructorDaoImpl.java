package dao.impl;

import dao.InstructorDao;
import pojo.Instructor;

import java.util.List;

/**
 * InstructorDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/19  20:41
 */

public class InstructorDaoImpl extends BaseDao implements InstructorDao {
    @Override
    public Instructor QueryInstructorByEmailAndPassword(String email, String password) {
        String sql = "select `email`,`password`,`nickname`,`status` from instructor where email = ? and password = ?";
        return queryForOne(Instructor.class,sql,email,password);
    }

    @Override
    public Instructor QueryInstructorByEmail(String email) {
        String sql = "select `email`,`password`,`nickname`,`status` from instructor where email = ?";
        return queryForOne(Instructor.class,sql,email);
    }

    @Override
    public List<Instructor> QueryAllInstructors() {
        String sql = "select * from instructor";
        return queryForList(Instructor.class,sql);
    }

    @Override
    public int InsertInstructor(String email, String password) {
        String sql = "insert into instructor(`email`,`password`) values(?,?)";
        return update(sql,email,password);
    }

    @Override
    public int InsertInstructor(String email) {
        String sql = "insert into instructor(`email`) values(?)";
        return update(sql,email);
    }

    @Override
    public int DeleteInstructor(String email) {
        String sql = "delete from `instructor` where (`email` = ?)";
        return update(sql,email);
    }

    @Override
    public int SetStatus(String email,int status) {
        String sql = "update `instructor` set `status` = ? where (`email` = ?)";
        return update(sql,status,email);
    }

    @Override
    public int SetNickname(String email, String nickname) {
        String sql = "update `instructor` set `nickname` = ? where (`email` = ?)";
        return update(sql,nickname,email);
    }
}
