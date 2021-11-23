package dao.inter;

import pojo.Notice;

import java.util.List;

public interface NoticeDao {
    int InsertNotice(String courseID,String classID,String date,String content);
    List<Notice> QueryNoticesByCourseIDAndClassID(String courseID,String classID);
}
