package dao.inter;

import pojo.CourseExp;

import java.util.List;

public interface CourseExpDao {
    int InsertCourseExp(String courseID,String expname,int percent);
    List<CourseExp> QueryCourseExpsByCourseID(String courseID);
}
