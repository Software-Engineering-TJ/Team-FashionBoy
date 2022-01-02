package dao.impl;

import dao.inter.CounterDao;
import pojo.Counter;

public class CounterDaoImpl extends BaseDao implements CounterDao {
    @Override
    public Counter QueryCounterById(int id) {
        String sql = "select * from counter where id = ?";
        return queryForOne(Counter.class,sql,id);
    }

    @Override
    public int UpdateAllOfCounter(int courseID, int classID) {
        String sql = "update counter set courseID = ? , classID = ? where id = 1";
        return update(sql,courseID,classID);
    }

    @Override
    public int UpdateCourseIDOfCounter(int courseID) {
        String sql = "update counter set courseID = ? where id = 1";
        return update(sql,courseID);
    }

    @Override
    public int UpdateClassIDOfCounter(int classID) {
        String sql = "update counter set classID = ? where id = 1";
        return update(sql,classID);
    }
}
