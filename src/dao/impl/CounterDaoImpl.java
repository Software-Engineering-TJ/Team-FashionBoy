package dao.impl;

import dao.inter.CounterDao;

public class CounterDaoImpl extends BaseDao implements CounterDao {
    @Override
    public pojo.Counter QueryCounterById(int id) {
        String sql = "select * from counter where id = ?";
        return queryForOne(pojo.Counter.class,sql,id);
    }

    @Override
    public int UpdateAllOfCounter(int courseID, int classID) {
        String sql = "update Counter set courseID = ? , classID = ? where id = 1";
        return update(sql,courseID,classID);
    }

    @Override
    public int UpdateCourseIDOfCounter(int courseID) {
        String sql = "update Counter set courseID = ? where id = 1";
        return update(sql,courseID);
    }

    @Override
    public int UpdateClassIDOfCounter(int classID) {
        String sql = "update Counter set classID = ? where id = 1";
        return update(sql,classID);
    }
}
