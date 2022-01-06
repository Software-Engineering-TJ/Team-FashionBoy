package dao.impl;

import dao.inter.CourseDao;
import pojo.Course;

import java.util.List;

/**
 * CourseDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  23:06
 */

public class CourseDaoImpl extends BaseDao implements CourseDao {
    @Override
    public Course QueryCourseByCourseID(String courseID) {
        String sql = "select `courseID`,`title`,`instructorNumber` from course where courseID = ?";
        return queryForOne(Course.class,sql,courseID);
    }

    @Override
    public int SetDutyInstructor(String courseID, String instructorNumber) {
        String sql = "update `course` set `instructorNumber` = ? where (`courseID` = ?)";
        return update(sql,instructorNumber,courseID);
    }

    @Override
    public int InsertCourse(String courseID, String title, String instructorNumber, String startDate, String endDate) {
        String sql = "insert into course(courseID,title,instructorNumber,startDate,endDate)";
        return update(sql,courseID,title,instructorNumber,startDate,endDate);
    }

    @Override
    public List<Course> QueryCourseByInstructorNumber(String instructorNumber) {
        String sql = "select * from course where instructorNumber = ?";
        return queryForList(Course.class,sql,instructorNumber);
    }
}
