package dao.inter;

import pojo.Teaches;

import java.util.List;

public interface TeachesDao {
    //根据courseID、classID查找
    List<Teaches> QueryTeachesByCourseIDAndClassID(String courseID, String classID);
}
