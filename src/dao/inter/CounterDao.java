package dao.inter;

import pojo.Counter;

public interface CounterDao {
    //获取课程、班级计数
    Counter QueryCounterById(int id);
    //更新计数器
    int UpdateAllOfCounter(int courseID,int classID);
    //更新courseId
    int UpdateCourseIDOfCounter(int courseID);
    //更新classId
    int UpdateClassIDOfCounter(int classID);
}
