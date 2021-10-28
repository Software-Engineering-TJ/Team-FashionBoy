package dao.inter;

import pojo.Teaches;

public interface TeachesDao {
    //根据courseID、classID查找
    Teaches QueryOneTeachesByCourseIDAndClassID(String courseID,String classID);
}
