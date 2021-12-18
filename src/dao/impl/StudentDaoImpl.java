package dao.impl;

import dao.inter.StudentDao;
import pojo.Student;

import java.util.List;

/**
 * StudentDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/19  18:38
 */

public class StudentDaoImpl extends BaseDao implements StudentDao {

    @Override
    public int updateStudent(String studentNumber, String email, String name, Integer sex, String phoneNumber) {
        String sql = "update `student` set `email` = ?, `name` = ?, `sex` = ?, `phoneNumber` = ? where `studentNumber` = ?";
        return update(sql, email, name, sex, phoneNumber, studentNumber);
    }

    @Override
    public int updateStudent(String studentNumber, String email, String phoneNumber) {
        String sql = "update `student` set `email` = ?, `phoneNumber` = ? where `studentNumber` = ?";
        return update(sql, email, phoneNumber, studentNumber);
    }

    @Override
    public Student QueryStudentByStudentNumberAndPassword(String studentNumber, String password) {
        String sql = "select `studentNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from student where studentNumber = ? and password = ?";
        return queryForOne(Student.class,sql,studentNumber,password);
    }


    @Override
    public Student QueryStudentByEmail(String email) {
        String sql = "select `studentNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from student where email = ?";
        return queryForOne(Student.class,sql,email);
    }

    @Override
    public Student QueryStudentByStudentNumber(String studentNumber) {
        String sql = "select `studentNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from student where studentNumber = ?";
        return queryForOne(Student.class,sql,studentNumber);
    }

    @Override
    public List<Student> QueryAllStudents() {
        String sql = "select `studentNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from from student";
        return queryForList(Student.class,sql);
    }

    @Override
    public int InsertStudent(String studentNumber,String email,String name,String phoneNumber,int sex) {
        String sql = "insert into student(`studentNumber`,`email`,`name`,`phoneNumber`,`sex`) values(?,?,?,?,?)";
        return update(sql,studentNumber,email,name,phoneNumber,sex);
    }

    @Override
    public int InsertStudent(String email) {
        String sql = "insert into student(`email`) values(?)";
        return update(sql,email);
    }

    @Override
    public int DeleteStudent(String email) {
        String sql = "delete from `student` where (`email` = ?);";
        return update(sql,email);
    }

    @Override
    public int SetStatus(String email,int status) {
        String sql = "update `student` set `status` = ? where (`email` = ?)";
        return update(sql,status,email);
    }

    @Override
    public int SetNickname(String email, String name) {
        String sql = "update `student` set `name` = ? where (`email` = ?)";
        return update(sql,name,email);
    }

    @Override
    public int UpdatePasswordByStudentNumber(String studentNumber, String password) {
        String sql = "update `student` set `password` = ? where (`studentNumber` = ?)";
        return update(sql,password,studentNumber);
    }
}
