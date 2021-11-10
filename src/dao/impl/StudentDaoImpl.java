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

    //自行修改
    public int updateStudent(String studentNumber, String email, String password, Integer sex, String phoneNumber) {
        String sql = "update `student` set `email` = ?, `password` = ?, `sex` = ?, `phoneNumber` = ? where `studentNumber` = ?";
        return update(sql, email, password, sex, phoneNumber, studentNumber);
    }

    //管理员修改
    public int updateStudent(String studentNumber, String name, String phoneNumber, String email, Integer sex) {
        String sql = "update `student` set `studentNumber` = ?, `name` = ?, `phoneNumber` = ?, `email` = ?, `sex` = ? where `studentNumber` = ?";
        return update(sql, studentNumber, name, phoneNumber, email, sex);
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
        String sql = "select select `studentNumber`,`email`,`password`,`name`,`sex`,`phoneNumber`,`status` from from student";
        return queryForList(Student.class,sql);
    }

    @Override
    public int InsertStudent(String studentNumber,String email,String name,String phoneNumber,int sex) {
        String sql = "insert ignore into student(`studentNumber`,`email`,`name`,`phoneNumber`,`sex`) values(?,?,?,?,?)";
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

}
