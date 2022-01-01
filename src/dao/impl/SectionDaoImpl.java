package dao.impl;

import dao.inter.SectionDao;
import pojo.Course;
import pojo.Section;

import java.util.List;

/**
 * SectionDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  23:03
 */

public class SectionDaoImpl extends BaseDao implements SectionDao {
    @Override
    public Section QuerySectionByCourseIDAndClassID(String courseID,String classID) {
        String sql = "select `courseID`,`classID`,`day`,`time`,`number`,`currentNumber` from section where courseID = ? and classID = ?";
        return queryForOne(Section.class,sql,courseID,classID);
    }

    @Override
    public List<Section> QuerySectionByCourseID(String courseID) {
        String sql = "select `courseID`,`classID`,`day`,`time`,`number`,`currentNumber` from section where courseID = ?";
        return queryForList(Section.class,sql,courseID);
    }
    @Override
    public int insertSection(String courseID, int day, int time) {
        String sql = "insert into section(`courseID`,`day`,`time`) values (?,?,?)";
        return update(sql, courseID, day, time);
    }

    @Override
    public int deleteSection(String courseID, String classID) {
        String sql = "delete from section where `courseID`=? and `classID`=?";
        return update(sql, courseID, classID);
    }
}
