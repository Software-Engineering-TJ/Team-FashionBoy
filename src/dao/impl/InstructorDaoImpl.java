package dao.impl;

import dao.inter.InstructorDao;
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
    public Instructor QueryInstructorByInstructorNumberAndPassword(String instructorNumber, String password) {
        String sql = "select `instructorNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from instructor where instructorNumber = ? and password = ?";
        return queryForOne(Instructor.class,sql,instructorNumber,password);
    }

    @Override
    public Instructor QueryInstructorByEmail(String email) {
        String sql = "select `instructorNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from instructor where email = ?";
        return queryForOne(Instructor.class,sql,email);
    }

    @Override
    public Instructor QueryInstructorByInstructorNumber(String instructorNumber) {
        String sql = "select `instructorNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from instructor where instructorNumber = ?";
        return queryForOne(Instructor.class,sql,instructorNumber);
    }

    @Override
    public List<Instructor> QueryAllInstructors() {
        String sql = "select select `instructorNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from from instructor";
        return queryForList(Instructor.class,sql);
    }

    @Override
    public int InsertInstructor(String instructorNumber,String email,String name,String phoneNumber,int sex) {
        String sql = "insert into instructor(`instructorNumber`,`email`,`name`,`phoneNumber`,`sex`) values(?,?,?,?,?)";
        return update(sql,instructorNumber,email,name,phoneNumber,sex);
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
    public int SetNickname(String email, String name) {
        String sql = "update `instructor` set `name` = ? where (`email` = ?)";
        return update(sql,name,email);
    }

    @Override
    public int updateInstructor(String instructorNumber, String email, String phoneNumber) {
        String sql = "update `instructor` set `email` = ?, `phoneNumber` = ? where (`instructorNumber` = ?)";
        return update(sql, email, phoneNumber, instructorNumber);
    }

    @Override
    public int updateInstructor(String instructorNumber, String email, String name, Integer sex, String phoneNumber) {
        String sql = "update `instructor` set `email` = ?, `name` = ?, `sex` = ?, `phoneNumber` = ? where (`instructorNumber` = ?)";
        return update(sql, email, name, sex, phoneNumber, instructorNumber);
    }
}
