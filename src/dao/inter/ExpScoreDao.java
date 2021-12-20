package dao.inter;

import pojo.ExpScore;

import java.util.List;

public interface ExpScoreDao {
    //交完报告就会插入一条记录
    int InsertExpScore(String studentNumber,String courseID,String expname,String classID,String fileUrl);
    //获取某个学生的某次实验的成绩
    ExpScore QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(String courseID,String classID,
                                                                         String expname,String studentNumber);
    //查看某班的某个实验的所有成绩
    List<ExpScore> QueryExpScoresByExperiment(String courseID,String expname,String classID);
    //批改作业后修改分数
    int UpdateExpScore(String studentNumber,String courseID,String expname,String classID,float score,String comment);
    //更新实验报告位置
    int UpdateFileUrl(String courseID,String classID,String expname,String studentNumber, String fileUrl);
    //删除实验报告
    int DeleteByFileUrl(String fileUrl);
}
