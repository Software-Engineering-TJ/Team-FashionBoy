package service.inter;

import pojo.Notice;

import java.util.List;

public interface StudentService {
    //学生获取教师发布的通知
    List<Notice> getCourseNotice(String courseID, String classID);
}
