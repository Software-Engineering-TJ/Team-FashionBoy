package dao.impl;

import dao.inter.TakesDao;
import pojo.Student;
import pojo.Takes;

import java.util.List;

/**
 * TakseDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  22:23
 */

public class TakesDaoImpl extends BaseDao implements TakesDao {
    @Override
    public List<Takes> QueryTakesByStudentNumber(String studentNumber) {
        String sql = "select `studentNumber`,`courseID`,`classID`,`status` from takes where studentNumber = ?";
        return queryForList(Takes.class,sql,studentNumber);
    }

    @Override
    public int SetDuty(String studentNumber,String courseID,String classID,String duty) {
        Integer newDuty = ("学生".equals(duty))?1:0; //映射到数据库的身份表示形式
        String sql = "update `takes` set `status` = ? where (`studentNumber` = ?) and (`courseID` = ?) and (`classID` = ?)";
        return update(sql,newDuty,studentNumber,courseID,classID);
    }

    @Override
    public List<Takes> QueryTakesByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select `studentNumber`,`courseID`,`classID`,`status` from takes where courseID = ? and classID = ?";
        return queryForList(Takes.class,sql,courseID,classID);
    }

    @Override
    public Takes QueryTakesByCourseIDAndClassIDAndStudentNumber(String courseID, String classID, String studentNumber) {
        String sql = "select `studentNumber`,`courseID`,`classID`,`status` from takes where courseID = ? and classID = ? and studentNumber = ?";
        return queryForOne(Takes.class,sql,courseID,classID,studentNumber);
    }

    @Override
    public List<Takes> queryTakesNotInAttendScore(String courseID, String classID, String title) {
        String sql = "select * from takes where courseID = ? and classID = ? and studentNumber not in" +
                " (select studentNumber from attendscore where courseID = ? and classID = ? and title = ?)";
        return queryForList(Takes.class, sql, courseID, classID, courseID, classID, title);
    }
}
