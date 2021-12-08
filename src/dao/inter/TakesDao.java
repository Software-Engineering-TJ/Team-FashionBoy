package dao.inter;

import pojo.Student;
import pojo.Takes;

import java.util.List;

public interface TakesDao {
    //根据学生number查询所有相关课程
    List<Takes> QueryTakesByStudentNumber(String studentNumber);
    //设置学生在某个课程中的身份：学生or助教
    int SetDuty(String studentNumber,String courseID,String classID,String duty);
    //根据课程查学生
    List<Takes> QueryTakesByCourseIDAndClassID(String courseID,String classID);
}
