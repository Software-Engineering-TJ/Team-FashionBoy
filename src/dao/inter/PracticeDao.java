package dao.inter;

import pojo.Practice;

import java.util.List;

public interface PracticeDao {
    //查找某个课、某个班所有的对抗练习
    List<Practice> QueryPracticesByCourseIDAndClassID(String coureseID,String classID);
}
