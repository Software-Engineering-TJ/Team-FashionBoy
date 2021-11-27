package service.inter;

import pojo.Experiment;

import java.util.List;
import java.util.Map;

public interface InstructorService {
    //获取教师教授的课程
    List<Map<String,String>> GetSections(String instructorNumber);
    //查看责任教师发布的实验大纲（也就是所有的实验）
    List<Map<String,String>> GetCourseExpInfo(String courseID);
    //教师发布实验
    int releaseExperiment(String courseID,String expname,String classID,
                              String startDate,String endDate,String expInfo);
    //返回教师想查看的已发布的实验信息
    Map<String,String> examineExperimentInfo(String courseID,String classID,String expname);
}
