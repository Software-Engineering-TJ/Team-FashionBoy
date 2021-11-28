package service.Impl;

import dao.impl.ExpScoreDaoImpl;
import dao.impl.NoticeDaoImpl;
import dao.inter.ExpScoreDao;
import dao.inter.NoticeDao;
import pojo.ExpScore;
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
    private ExpScoreDao expScoreDao = new ExpScoreDaoImpl();

    @Override
    public List<Notice> getCourseNotice(String courseID, String classID) {
        //获取所有的通知
        return noticeDao.QueryNoticesByCourseIDAndClassID(courseID,classID);
    }

    @Override
    public String getExpScore(String courseID, String classID, String expname, String studentNumber) {
        String score = null;
        //查找数据库中该学生提交的实验对应的成绩信息。
        ExpScore expScore = expScoreDao.QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(courseID, classID, expname, studentNumber);
        if(expScore == null){
            //数据库中没有找到，说明没有提交作业
            score = "报告尚未提交";
        }else{
            if(expScore.getScore()==-1) {
                //"-1"意味着报告尚未批改
                score = "助教尚未批改";
            }else{
                //成绩正常
                score = Float.toString(expScore.getScore());
            }
        }
        return score;
    }
}
