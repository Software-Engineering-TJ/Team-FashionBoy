package service.Impl;

import dao.impl.NoticeDaoImpl;
import dao.inter.NoticeDao;
import pojo.Notice;
import service.inter.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/24  19:18
 */

public class StudentServiceImpl implements StudentService {
    private NoticeDao noticeDao = new NoticeDaoImpl();

    @Override
    public List<Notice> getCourseNotice(String courseID, String classID) {
        //获取所有的通知
        return noticeDao.QueryNoticesByCourseIDAndClassID(courseID,classID);
    }
}
