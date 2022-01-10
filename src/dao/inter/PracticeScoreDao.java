package dao.inter;

import pojo.PracticeScore;

import java.util.List;

public interface PracticeScoreDao {
    //查找某对抗联系同一组的排名列表（分组、成绩倒序，时间顺序）
    List<PracticeScore> QueryPracticeScoreByGroup(String courseID,String classID,String practiceName,int groupNumber);
    //找某次练习中，某个学生的成绩
    PracticeScore QueryPracticeScoreByCourseIDAndClassIDAndPracticeNameAndStudentNumber(String courseID,String classID,String practiceName,String studentNumber);
    //写入对抗联系成绩
    int insertPracticeScore(String courseID, String classID, String practiceName, String studentNumber, double score, String time, int groupNumber);
}
