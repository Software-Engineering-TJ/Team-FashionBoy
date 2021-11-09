package dao.impl;

import dao.inter.TeachesDao;
import pojo.Teaches;

import java.util.List;

/**
 * TechesDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  23:04
 */

public class TeachesDaoImpl extends BaseDao implements TeachesDao{
    @Override
    public List<Teaches> QueryTeachesByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select `instructorNumber`,`courseID`,`classID` from teaches where courseID = ? and classID = ?";
        return queryForList(Teaches.class,sql,courseID,classID);
    }

    @Override
    public List<Teaches> QueryTeachesByInstructorNumber(String instructorNumber) {
        String sql = "select `instructorNumber`,`courseID`,`classID` from teaches where instructorNumber = ?";
        return queryForList(Teaches.class,sql,instructorNumber);
    }
}
