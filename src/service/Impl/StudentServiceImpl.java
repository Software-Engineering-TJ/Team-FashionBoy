package service.Impl;

import dao.impl.*;
import dao.inter.*;
import pojo.*;
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
    private TakesDao takesDao = new TakesDaoImpl();
    private AttendScoreDao attendScoreDao = new AttendScoreDaoImpl();
    private CourseExpDao courseExpDao = new CourseExpDaoImpl();
    private ExperimentDao experimentDao = new ExperimentDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();
    private AttendDao attendDao = new AttendDaoImpl();
    private PracticeDao practiceDao = new PracticeDaoImpl();
    private PracticeScoreDao practiceScoreDao = new PracticeScoreDaoImpl();
    private ReflectionDao reflectionDao = new ReflectionDaoImpl();

    @Override
    public List<Notice> getCourseNotice(String courseID, String classID) {
        //获取所有的通知
        return noticeDao.QueryNoticesByCourseIDAndClassID(courseID,classID);
    }

    @Override
    public ExpScore getExpScore(String courseID, String classID, String expname, String studentNumber) {
        String score = null;
        //查找数据库中该学生提交的实验对应的成绩信息。
        ExpScore expScore = expScoreDao.QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(courseID, classID, expname, studentNumber);

        return expScore;
    }

    @Override
    public int recordCommit(String courseID, String classID, String expname, String studentNumber,String fileUrl) {
        //首次提交，则“添加”提交记录
        if(expScoreDao.QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(courseID, classID, expname, studentNumber)==null){
            return expScoreDao.InsertExpScore(studentNumber,courseID,expname,classID,fileUrl);
        }
        //后续重交，则覆盖文件信息
        return expScoreDao.UpdateFileUrl(courseID, classID, expname, studentNumber, fileUrl);
    }

    @Override
    public String getDuty(String courseID, String classID, String studentNumber) {
        Takes takes = takesDao.QueryTakesByCourseIDAndClassIDAndStudentNumber(courseID,classID,studentNumber);
        return (takes.getStatus()==0)?"学生":"助教";
    }

    @Override
    public int addAttendScore(String courseID, String classID, String title, String studentNumber, int onTime) {
        return attendScoreDao.InsertAttendScore(courseID, classID, title, studentNumber, onTime);
    }

    @Override
    public int deleteCommit(String fileUrl) {
        return expScoreDao.DeleteByFileUrl(fileUrl);
    }

    @Override
    public List<CourseExp> getCoursesByCourseID(String courseID) {
        return courseExpDao.QueryCourseExpsByCourseID(courseID);
    }

    @Override
    public List<Experiment> getExperimentByCourseIDAndClassID(String courseID, String classID) {
        return experimentDao.QueryExperimentsByCourseIDAndClassID(courseID, classID);
    }

    @Override
    public CourseExp getCourseExpByCourseIDAndExpname(String courseID, String expname) {
        return courseExpDao.QueryCourseExpByCourseIDAndExpname(courseID, expname);
    }

    @Override
    public List<Takes> getTakesListByStudentNumber(String studentNumber) {
        return takesDao.QueryTakesByStudentNumber(studentNumber);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.QueryAllStudents();
    }

    @Override
    public List<Experiment> getExperimentListByCourseIDAndClassID(String courseID, String classID) {
        return experimentDao.QueryExperimentsByCourseIDAndClassID(courseID,classID);
    }

    @Override
    public Map<String, Object> getGradeAndRankingOfExperiment(Experiment experiment, String studentNumber) {
        //按照score倒叙排列的名单
        List<ExpScore> expScoreList = expScoreDao.QueryExpScoresByExperimentAndScoreDESC(experiment.getCourseID(),experiment.getClassID(),experiment.getExpname());
        Map<String,Object> map = new HashMap<>();
        map.put("name",experiment.getExpname());
        if(expScoreList == null){
            //还没有人提交报告，成绩为零分，默认第一名
            map.put("grade",0.0);
            map.put("ranking",1);
        }else{
            boolean exist = false;
            for(int i =0 ;i < expScoreList.size();i++){
                ExpScore expScore = expScoreList.get(i);
                //找到学生在排名中的位置
                if(expScore.getStudentNumber().equals(studentNumber)){
                    float score = expScore.getScore();
                    map.put("grade",(score==-1)?0:score);
                    map.put("ranking",i+1);
                    exist = true;
                    break;
                }
            }
            //没找到学生时，说明学生没有提交作业
            if(!exist) {
                map.put("grade", 0.0);
                map.put("ranking", expScoreList.size() + 1);
            }
        }
        return map;
    }

    @Override
    public float getGradeOfAttendance(String courseID, String classID, String studentNumber) {
        //该课的各项成绩占比分配情况
        List<CourseExp> courseExpList = courseExpDao.QueryCourseExpsByCourseID(courseID);
        //考勤成绩占比
        int percent = 0;
        for(CourseExp courseExp : courseExpList){
            if("考勤".equals(courseExp.getExpname())){
                percent = courseExp.getPercent();
            }
        }
        //该班发布的所有考勤
        List<Attend> attendList = attendDao.QueryAttendsByCourseIDAndClassID(courseID,classID);
        //考勤总次数
        float totalCount = attendList.size();
        if(totalCount == 0){
            //老师如果从来没有发布过考勤，则默认考勤满分
            return percent;
        }
        //学生参加的考勤数
        float attendCount = 0;
        if(attendList != null){
            for(Attend attend : attendList){
                AttendScore attendScore = attendScoreDao.getAttendScoreByCourseIDAndClassIDAndTitleAndStudentNumber(courseID, classID, attend.getTitle(), studentNumber);
                if(attendScore != null){
                    //该次签到准时参加了，计入分数
                    attendCount += 1;
                }
            }
        }
        //返回考勤分数
        return attendCount/totalCount*percent;
    }

    @Override
    public float getGradeOfExperiment(String courseID, String classID, String studentNumber) {
        //该课的各项成绩占比分配情况
        List<CourseExp> courseExpList = courseExpDao.QueryCourseExpsByCourseID(courseID);
        //开始算实验成绩
        float expGrade = 0;
        for(CourseExp courseExp : courseExpList){
            //该实验的百分比
            float expPercent = courseExp.getPercent();
            //靠学生在本实验的成绩
            ExpScore expScore = expScoreDao.QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(courseID,classID,courseExp.getExpname(),studentNumber);
            if(expScore!=null && expScore.getScore() != -1){
                //只计算已经提交且批改过的报告
                expGrade += expScore.getScore()*expPercent/100;
            }
        }

        return expGrade;
    }

    @Override
    public float getGradeOfPractice(String courseID, String classID, String studentNumber) {
        //该课的各项成绩占比分配情况
        List<CourseExp> courseExpList = courseExpDao.QueryCourseExpsByCourseID(courseID);
        //抗练习成绩总占比
        float percent = 0;
        for(CourseExp courseExp : courseExpList){
            if("对抗练习".equals(courseExp.getExpname())){
                percent = courseExp.getPercent();
            }
        }
        //对抗练习总成绩
        float sumScore = 0;
        //该班级发布的所有对抗练习
        List<Practice> practiceList = practiceDao.QueryPracticesByCourseIDAndClassID(courseID,classID);
        if(practiceList == null){
            //如果没有对抗练习，默认满分
            return percent;
        }
        //查找每一个对抗练习中在该学生的小组中的排名，确定成绩
        for(Practice practice : practiceList){
            //该学生在本次对抗练习中的成绩信息
            PracticeScore practiceScore = practiceScoreDao.QueryPracticeScoreByCourseIDAndClassIDAndPracticeNameAndStudentNumber(courseID,classID,practice.getPracticeName(),studentNumber);
            if(practiceScore == null){
                //没有参加该次对抗练习，成绩为0
                continue;
            }
            //排序后的小组成员
            List<PracticeScore> practiceScoreList = practiceScoreDao.QueryPracticeScoreByGroup(courseID,classID,practice.getPracticeName(),practiceScore.getGroupNumber());
            //算成绩
            if(practiceScoreList != null){
                for(int i=0;i<practiceScoreList.size();i++){
                    if(practiceScoreList.get(i).getStudentNumber().equals(practiceScore.getStudentNumber())){
                        //找到所在的组的名次i(第一名100，第二名60，第三名20)
                        sumScore += ((3-i)*2-1)*20;
                        break;
                    }
                }
            }
        }

        return sumScore/practiceList.size()*percent/100;
    }

    @Override
    public int writeReflection(String courseID, String classID, String studentNumber,String content,String date) {
        return reflectionDao.addReflection(courseID, classID, studentNumber, content, date);
    }
}
