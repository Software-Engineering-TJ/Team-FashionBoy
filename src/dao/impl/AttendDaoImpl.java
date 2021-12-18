package dao.impl;

import dao.inter.AttendDao;
import pojo.Attend;

import java.util.List;

public class AttendDaoImpl extends BaseDao implements AttendDao {
    @Override
    public Attend QueryAttendByCourseIDAndClassIDAndTitle(String courseID, String classID, String title) {
        String sql = "select * from attend where courseID =? and classID = ? and title = ?";
        return queryForOne(Attend.class,sql,courseID,classID,title);
    }

    @Override
    public List<Attend> QueryAttendsByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select * from attend where courseID = ? and classID = ?";
        return queryForList(Attend.class,sql,courseID,classID);
    }

    @Override
    public int InsertAttend(String courseID, String classID, String attendName, int percent, String startTime, String endTime) {
        String sql = "insert into attend(`courseID`,`classID`,`title`,`percent`,`startTime`,`endTime`) values(?,?,?,?,?,?)";
        return update(sql,courseID,classID,attendName,percent,startTime,endTime);
    }
}
