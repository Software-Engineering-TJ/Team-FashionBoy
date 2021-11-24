package dao.inter;

import pojo.Notice;

import java.util.List;

public interface NoticeDao {
    int InsertNotice(String courseID,String classID,String date,String content,String instructorNumber,String title);
    List<Notice> QueryNoticesByCourseIDAndClassID(String courseID,String classID);
}
