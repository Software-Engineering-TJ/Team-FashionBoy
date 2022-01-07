package service.inter;

import pojo.*;

import java.util.List;
import java.util.Map;

public interface InstructorService {
    //获取这一门课程
    Section getSection(String courseID, String classID);
    //获取教师教授的课程
    List<Map<String,String>> GetSections(String instructorNumber);
    //查看责任教师发布的实验大纲（也就是所有的实验）
    List<Map<String,String>> GetCourseExpInfo(String courseID,String classID);
    //教师发布实验
    int ReleaseExperiment(String courseID,String expname,String classID,String startDate,String endDate,String expInfo);
    //返回教师想查看的已发布的实验信息
    Map<String,String> ExamineExperimentInfo(String courseID,String classID,String expname);
    //修改已经发布的实验的信息
    int ModifyExperiment(String courseID,String classID,String expname,String endDate,String expInfo);
    //教师发布公告
    int ReleaseNotice(String courseID,String classID,String instructorNumber,String content,String date,String title);
    //教师删除公告
    int DeleteNotice(String courseID,String classID,String instructorNumber,String date);
    //教师发布实验报告说明
    int ReleaseReportDesc(String courseID,String classID,String expname,String reportName,String reportInfo,
                          String startDate,String endDate,String fileType);
    //教师删除实验报告说明
    int DeleteReportDesc(String courseID,String classID,String expname,String reportName);
    //教师修改实验报告说明
    int ModifyReportDesc(String courseID,String classID,String expname,String reportName,String reportInfo,
                         String endDate,String fileType);
    //获取该课程下的所有班级信息
    List<Map<String,String>> GetSectionInfoOfCourse(String courseID);
    //获取报告提交信息
    List<ExpScore> getSubmittedStudentList(String courseID, String classID, String expname);
    //获得某个班级下的所有学生学号
    List<String> getStudentNumbersByCourseIDAndClassID(String courseID,String classID);
    //查询某个参考资料的是否已经提交
    boolean checkReference(String fileUrl);
    //添加上传参考资料的记录
    int recordCommit(String courseID,String classID,String instructorNumber,String fileUrl);
    //查看某课程的考勤占比
    int getCourseAttendPercent(String courseID);
    //获取某班已经发布的考勤情况
    List<Attend> getAttendsBefore(String courseID,String classID);
    //添加考勤记录
    int addAttend(String courseID,String classID,String attendName,String startTime,String endTime);
    //删除某个参考资料
    int deleteReference(String fileUrl);

    int addSection(String courseID,String instructorNumber, int day, int time, int number);
    //添加新课程到course表，返回courseID
    String createCourse(String title,String instructorNumber, String startDate,String endDate);
    //添加新课程的实验大纲（实验，考勤，对抗练习）
    void addCourseExp(String courseID,  List<Map<String,Object>> courseExpInfoList,int attendanceWeight,int practiceWeight);
    //批改成绩录入
    int registerGrade(String courseID,String classID,String studentNumber,String expname,float score,String comment);
    List<ChoiceQuestion> getRandomQuestionList(int size);
    List<AttendScore> getAttendScoreByCourseIDAndClassIDAndTitle(String courseID, String classID, String title);
    Student getStudentByStudentNumber(String studentNumber);
    List<Takes> getTakesNotInAttendScore(String courseID, String classID, String title);
    List<Teaches> getTeachesListByInstructorNumber(String instructorNumber);

}
