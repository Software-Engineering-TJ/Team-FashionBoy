package dao.impl;

import dao.StudentDao;
import pojo.Student;

import javax.management.Query;
import java.util.List;

/**
 * StudentDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/19  18:38
 */

public class StudentDaoImpl extends BaseDao implements StudentDao {
    @Override
    public Student QueryStudentByEmailAndPassword(String email, String password) {
        String sql = "select `email`,`password`,`nickname`,`status` from student where email = ? and password = ?";
        return queryForOne(Student.class,sql,email,password);
    }

    @Override
    public Student QueryStudentByEmail(String email) {
        String sql = "select `email`,`password`,`nickname`,`status` from student where email = ?";
        return queryForOne(Student.class,sql,email);
    }

    @Override
    public List<Student> QueryAllStudents() {
        String sql = "select * from student";
        return queryForList(Student.class,sql);
    }

    @Override
    public int InsertStudent(String email,String password) {
        String sql = "insert into student(`email`,`password`) values(?,?)";
        return update(sql,email,password);
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
    public int SetNickname(String email, String nickname) {
        String sql = "update `student` set `nickname` = ? where (`email` = ?)";
        return update(sql,nickname,email);
    }
}
