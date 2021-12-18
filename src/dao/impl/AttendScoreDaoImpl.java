package dao.impl;

import dao.inter.AttendScoreDao;

public class AttendScoreDaoImpl extends BaseDao implements AttendScoreDao {
    @Override
    public int InsertAttendScore(String courseID, String classID, String title, String studentNumber, int onTime) {
        String sql = "insert into attendscore(`courseID`,`classID`,`title`,`studentNumber`,`onTime`) values(?,?,?,?,?)";
        return update(sql,courseID,classID,title,studentNumber,onTime);
    }
}
