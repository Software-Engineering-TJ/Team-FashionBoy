package dao.inter;

import com.mysql.cj.util.DnsSrv;
import pojo.CourseExp;

import java.util.List;

public interface CourseExpDao {
    int InsertCourseExp(String courseID,String expname,int percent,int priority,int difficulty);
    List<CourseExp> QueryCourseExpsByCourseID(String courseID);
    CourseExp QueryCourseExpByCourseIDAndExpname(String courseID,String expname);
}
