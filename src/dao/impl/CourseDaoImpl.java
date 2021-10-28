package dao.impl;

import dao.inter.CourseDao;
import pojo.Course;

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
}
