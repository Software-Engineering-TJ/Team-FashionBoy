package dao.inter;

import pojo.Course;

public interface CourseDao {
    Course QueryCourseByCourseID(String courseID);

}
