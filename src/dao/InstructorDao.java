package dao;

import pojo.Instructor;
import java.util.List;

public interface InstructorDao {
    Instructor QueryInstructorByEmailAndPassword(String email, String password);
    List<Instructor> QueryAllInstructors();
    int InsertInstructor(String email,String password);
    int InsertInstructor(String email);
    int DeleteInstructor(String email);
    int SetStatus(String email,int status);
    int SetNickname(String email,String nickname);
}
