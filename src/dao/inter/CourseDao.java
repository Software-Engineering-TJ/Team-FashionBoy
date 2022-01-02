package dao.inter;

import pojo.Course;

public interface CourseDao {
    Course QueryCourseByCourseID(String courseID);
    int SetDutyInstructor(String courseID,String instructorNumber);
    int InsertCourse(String courseID,String title,String instructorNumber,String startDate,String endDate);
}
