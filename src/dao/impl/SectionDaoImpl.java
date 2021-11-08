package dao.impl;

import dao.inter.SectionDao;
import pojo.Course;
import pojo.Section;

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
}
