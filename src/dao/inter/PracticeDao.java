package dao.inter;

import pojo.Practice;

import java.sql.Timestamp;
import java.util.List;

public interface PracticeDao {
    //查找某个课、某个班所有的对抗练习
    List<Practice> QueryPracticesByCourseIDAndClassID(String courseID,String classID);
    //插入practice
    int insertPractice(String courseID, String classID, String practiceName, Timestamp startTime, Timestamp endTime);
}
