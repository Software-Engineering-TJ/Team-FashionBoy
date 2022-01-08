package dao.inter;

import pojo.Course;

import java.util.List;

public interface CourseDao {
    Course QueryCourseByCourseID(String courseID);
    int SetDutyInstructor(String courseID,String instructorNumber);
    int InsertCourse(String courseID,String title,String instructorNumber,String startDate,String endDate);
    List<Course> QueryCourseByInstructorNumber(String instructorNumber);
    //获取责任教师申请的课程
    List<Course> QueryCoursesByFlag(int flag);
    //设置课程状态
    int UpdateFlagOfCourseByCourseID(String courseID,int flag);
    //删除课程
    int DeleteCourseByCourseID(String courseID);
}
