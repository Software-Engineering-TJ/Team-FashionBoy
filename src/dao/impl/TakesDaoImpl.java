package dao.impl;

import dao.inter.TakesDao;
import pojo.Takes;

import java.util.List;

/**
 * TakseDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  22:23
 */

public class TakesDaoImpl extends BaseDao implements TakesDao {
    @Override
    public List<Takes> QueryTakesByStudentNumber(String studentNumber) {
        String sql = "select `studentNumber`,`courseID`,`classID`,`status` from takes where studentNumber = ?";
        return queryForList(Takes.class,sql,studentNumber);
    }
}
