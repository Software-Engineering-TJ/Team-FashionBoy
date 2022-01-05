package service.inter;

import pojo.CourseExp;
import pojo.ExpScore;
import pojo.Notice;

import javax.naming.directory.Attributes;
import java.util.List;

public interface StudentService {
    //学生获取教师发布的通知
    List<Notice> getCourseNotice(String courseID, String classID);
    //学生获取自己某个实验报告的成绩,返回具体成绩or”助教尚未批改“
    String getExpScore(String courseID,String classID,String expname,String studentNumber);
    //记录学生作业提交记录
    int recordCommit(String courseID,String classID,String expname,String studentNumber,String fileUrl);
    //获取学生在某个课程中的身份
    String getDuty(String courseID,String classID,String studentNumber);
    //添加学生考勤记录
    int addAttendScore(String courseID,String classID,String title,String studentNumber,int onTime);
    //删除提交的实验报告
    int deleteCommit(String fileUrl);
    List<CourseExp> getCoursesByCourseID(String courseID);
    List<ExpScore> getAllExpScore(String courseID, String classID, String studentNumber);
    List<Experiment> getExperimentByCourseIDAndClassID(String courseID, String classID);
    CourseExp getCourseExpByCourseIDAndExpname(String courseID, String expname);
}
