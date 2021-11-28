package service.inter;

import pojo.Notice;

import java.util.List;

public interface StudentService {
    //学生获取教师发布的通知
    List<Notice> getCourseNotice(String courseID, String classID);
    //学生获取自己某个实验报告的成绩,返回具体成绩or”助教尚未批改“
    String getExpScore(String courseID,String classID,String expname,String studentNumber);
}
