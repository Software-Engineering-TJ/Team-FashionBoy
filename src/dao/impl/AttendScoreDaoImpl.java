package dao.impl;

import dao.inter.AttendScoreDao;
import pojo.AttendScore;

import java.util.List;

public class AttendScoreDaoImpl extends BaseDao implements AttendScoreDao {
    @Override
    public int InsertAttendScore(String courseID, String classID, String title, String studentNumber, int onTime) {
        String sql = "insert into attendscore(`courseID`,`classID`,`title`,`studentNumber`,`onTime`) values(?,?,?,?,?)";
        return update(sql,courseID,classID,title,studentNumber,onTime);
    }

    @Override
    public List<AttendScore> getAttendScoreByCourseIDAndClassIDAndTitle(String courseID, String classID, String title) {
        String sql = "select * from attendscore where courseID = ? and classID = ? and title=?";
        return queryForList(AttendScore.class, sql, courseID, classID, title);
    }

    @Override
    public List<AttendScore> getAttendScoreByCourseIDAndClassIDAndStudentNumber(String courseID, String classID, String studentNumber) {
        String sql = "select * from attendscore where courseID = ? and classID = ? and studentNumber=?";
        return queryForList(AttendScore.class, sql, courseID, classID, studentNumber);
    }

    @Override
    public AttendScore getAttendScoreByCourseIDAndClassIDAndTitleAndStudentNumber(String courseID, String classID, String title, String studentNumber) {
        String sql = "select * from attendscore where courseID = ? and classID = ? and title = ? and studentNumber= ?";
        return queryForOne(AttendScore.class, sql, courseID, classID, title, studentNumber);
    }
}
