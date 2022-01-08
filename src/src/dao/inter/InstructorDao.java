package dao.inter;

import pojo.Instructor;
import java.util.List;

public interface InstructorDao {
    Instructor QueryInstructorByInstructorNumberAndPassword(String instructorNumber, String password);
    Instructor QueryInstructorByEmail(String email);
    Instructor QueryInstructorByInstructorNumber(String instructorNumber);
    List<Instructor> QueryAllInstructors();
    int InsertInstructor(String instructorNumber,String email,String name,String phoneNumber,int sex);
    int InsertInstructor(String email);
    int DeleteInstructor(String email);
    int SetStatus(String email,int status);
    int SetNickname(String email,String name);

    int updateInstructor(String instructorNumber, String email, String phoneNumber);

    int updateInstructor(String instructorNumber, String email, String name, Integer sex, String phoneNumber);

    int UpdatePasswordByInstructorNumber(String instructorNumber,String password);
}
