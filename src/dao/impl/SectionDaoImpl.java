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
    public Section QuerySectionBySectionID(String courseID,String classID) {
        String sql = "select `courseID`,`classID` from section where courseID = ?";
        return queryForOne(Section.class,sql,courseID,classID);
    }
}
