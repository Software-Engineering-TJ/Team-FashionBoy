package dao.impl;

import dao.inter.ReflectionDao;
import pojo.Reflection;

import java.util.List;

public class ReflectionDaoImpl extends BaseDao implements ReflectionDao {
    @Override
    public int addReflection(String courseID, String classID, String studentNumber, String content,String date) {
        String sql = "insert into reflection(courseID,classID,studentNumber,date,content) value(?,?,?,?,?)";
        return update(sql,courseID,classID,studentNumber,content,date);
    }

    @Override
    public List<Reflection> QueryReflectionListByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select * from reflection where courseID = ? and classID = ?";
        return queryForList(Reflection.class,sql,courseID,classID);
    }
}
