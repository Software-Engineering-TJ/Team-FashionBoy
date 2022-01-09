package service.inter;

import pojo.*;

import javax.naming.directory.Attributes;
import java.util.List;
import java.util.Map;

public interface StudentService {
    //学生获取教师发布的通知
    List<Notice> getCourseNotice(String courseID, String classID);
    //学生获取自己某个实验报告的成绩,返回具体成绩or”助教尚未批改“
    ExpScore getExpScore(String courseID,String classID,String expname,String studentNumber);
    //记录学生作业提交记录
    int recordCommit(String courseID,String classID,String expname,String studentNumber,String fileUrl);
    //获取学生在某个课程中的身份
    String getDuty(String courseID,String classID,String studentNumber);
    //添加学生考勤记录
    int addAttendScore(String courseID,String classID,String title,String studentNumber,int onTime);
    //删除提交的实验报告
    int deleteCommit(String fileUrl);
    List<CourseExp> getCoursesByCourseID(String courseID);
    List<Experiment> getExperimentByCourseIDAndClassID(String courseID, String classID);
    CourseExp getCourseExpByCourseIDAndExpname(String courseID, String expname);
    //获取学生上的课
    List<Takes> getTakesListByStudentNumber(String studentNumber);
    //获取所有的学生信息
    List<Student> getAllStudents();
    //某个班发布的实验
    List<Experiment> getExperimentListByCourseIDAndClassID(String courseID,String classID);
    //某个学生在某个班已发布的某个实验中的成绩和排名
    Map<String,Object> getGradeAndRankingOfExperiment(Experiment experiment,String studentNumber);
    //获取某个课程、某个班级、某个学生的"所有考勤"总分数
    float getGradeOfAttendance(String courseID,String classID,String studentNumber);
    //获取某个课程、某个班级、某个学生的"所有实验"总分数
    float getGradeOfExperiment(String courseID,String classID,String studentNumber);
    //获取某个课程、某个班级、某个学生的"所有对抗"总分数
    float getGradeOfPractice(String courseID,String classID,String studentNumber);
}
