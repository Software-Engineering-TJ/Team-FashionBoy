package dao.impl;

import dao.inter.PracticeDao;
import pojo.Practice;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author HJK
 */
public class PracticeDaoImpl extends BaseDao implements PracticeDao {
    @Override
    public List<Practice> QueryPracticesByCourseIDAndClassID(String coureseID, String classID) {
        String sql = "select * from practice where courseID = ? and classID = ?";
        return queryForList(Practice.class,sql,coureseID,classID);
    }

    @Override
    public int insertPractice(String courseID, String classID, String practiceName, Timestamp startTime, Timestamp endTime) {
        String sql = "insert into practice(`courseID`,`classID`,`practiceName`,`startTime`,`endTime`)" +
                "values(?,?,?,?,?)";
        return update(sql,courseID, classID, practiceName, startTime, endTime);
    }
}
