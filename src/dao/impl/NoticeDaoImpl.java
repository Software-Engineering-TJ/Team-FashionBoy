package dao.impl;

import dao.inter.NoticeDao;
import pojo.Notice;

import java.util.List;

/**
 * NoticeDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/23  21:38
 */

public class NoticeDaoImpl extends BaseDao implements NoticeDao {
    @Override
    public int InsertNotice(String courseID, String classID, String date, String content,String instructorNumber,String title) {
        String sql = "insert into notice(`courseID`,`classID`,`date`,`content`,`instructorNumber`,`title`) values(?,?,?,?,?,?)";
        return update(sql,courseID,classID,date,content,instructorNumber,title);
    }

    @Override
    public List<Notice> QueryNoticesByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select * from notice where courseID = ? and classID = ? or classID = '0'";
        return queryForList(Notice.class,sql,courseID,classID);
    }

    @Override
    public int DeleteNotice(String courseID, String classID, String instructorNumber, String date) {
        String sql = "delete from notice where(`courseID` = ? and `classID` = ? and " +
                                                "`instructorNumber` = ? and `date` = ?)";
        return update(sql,courseID,classID,instructorNumber,date);
    }
}
