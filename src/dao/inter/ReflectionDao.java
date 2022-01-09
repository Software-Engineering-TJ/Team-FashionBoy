package dao.inter;

import pojo.Reflection;

import java.util.List;

public interface ReflectionDao {
    //添加评论
    int addReflection(String courseID, String classID, String studentNumber,String content,String date);
    //获取某班的评论
    List<Reflection> QueryReflectionListByCourseIDAndClassID(String courseID,String classID);
}
