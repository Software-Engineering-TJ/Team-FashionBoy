package dao.inter;

import pojo.ExpScore;

import java.util.List;

public interface ExpScoreDao {
    int InsertExpScore(String studentNumber,String courseID,String expname,String classID);
    ExpScore QueryExpScoreByStudentNumber(String studentNumber);
    //便于统计某班某次实验的成绩
    List<ExpScore> QueryExpScoresByExperiment(String courseID,String expname,String classID);
    //批改作业后修改分数
    int UpdateExpScore(String studentNumber,String courseID,String expname,String classID,float score,String comment);
}
