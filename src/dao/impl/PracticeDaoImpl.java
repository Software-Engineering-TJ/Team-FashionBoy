package dao.impl;

import dao.inter.PracticeDao;
import pojo.Practice;

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
}
