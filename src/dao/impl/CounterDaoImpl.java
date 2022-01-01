package dao.impl;

import dao.inter.CounterDao;
import pojo.Counter;

public class CounterDaoImpl extends BaseDao implements CounterDao {
    @Override
    public Counter QueryCounterById(int id) {
        String sql = "select * from counter where id = ?";
        return queryForOne(Counter.class,sql,id);
    }
}
