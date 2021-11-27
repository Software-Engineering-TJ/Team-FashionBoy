package dao.impl;

import dao.inter.CourseExpDao;
import pojo.CourseExp;

import java.util.List;

/**
 * CourseExpDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/23  22:11
 */

public class CourseExpDaoImpl extends BaseDao implements CourseExpDao {
    @Override
    public int InsertCourseExp(String courseID, String expname, int percent,int priority,int difficulty) {
        String sql = "insert into courseexp(`courseID`,`expname`,`percent`,`priority`,`difficulty`) values(?,?,?,?,?)";
        return update(sql,courseID,expname,percent,priority,difficulty);
    }

    @Override
    public List<CourseExp> QueryCourseExpsByCourseID(String courseID) {
        String sql = "select * from courseexp where courseID = ?";
        return queryForList(CourseExp.class,sql,courseID);
    }

    @Override
    public CourseExp QueryCourseExpByCourseIDAndExpname(String courseID, String expname) {
        String sql = "select * from courseexp where courseID = ? and expname = ?";
        return queryForOne(CourseExp.class,sql,courseID,expname);
    }
}
