package dao;

import pojo.Student;

import java.util.List;

public interface StudentDao {
    Student QueryStudentByEmailAndPassword(String email, String password);
    List<Student> QueryAllStudents();
    int InsertStudent(String email,String password);
    int InsertStudent(String email);
    int DeleteStudent(String email);
    int SetStatus(String email,int status);
    int SetNickname(String email,String nickname);
}
