package dao.inter;

import pojo.Counter;

public interface CounterDao {
    //获取课程、班级计数
    Counter QueryCounterById(int id);
    //更新计数器
    int UpdateCounter(int course,int classId);
}
