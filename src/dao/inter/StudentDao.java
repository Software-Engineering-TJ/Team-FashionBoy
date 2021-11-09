package dao.inter;

import pojo.Student;

import java.util.List;

public interface StudentDao {
    Student QueryStudentByStudentNumberAndPassword(String studentNumber, String password);
    Student QueryStudentByEmail(String email);
    Student QueryStudentByStudentNumber(String studentNumber);
    List<Student> QueryAllStudents();
    int InsertStudent(String studentNumber,String email,String name,String phoneNumber,int sex);
    int updateStudent(String studentNumber, String email, String password, Integer sex, String phoneNumber);
    int InsertStudent(String email);
    int DeleteStudent(String email);
    int SetStatus(String email,int status);
    int SetNickname(String email,String name);
}
