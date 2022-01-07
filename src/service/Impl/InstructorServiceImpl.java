package service.Impl;

import com.google.gson.reflect.TypeToken;
import dao.impl.*;
import dao.inter.*;
import pojo.*;
import service.inter.InstructorService;
import utils.RandomHandler;
import utils.RequestJsonUtils;

import javax.naming.directory.Attributes;
import java.util.*;

/**
 * InstructorServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/27  22:38
 */

public class InstructorServiceImpl implements InstructorService {

    private TeachesDao teachesDao = new TeachesDaoImpl();
    private CourseDao courseDao = new CourseDaoImpl();
    private CourseExpDao courseExpDao = new CourseExpDaoImpl();
    private ExperimentDao experimentDao = new ExperimentDaoImpl();
    private NoticeDao noticeDao = new NoticeDaoImpl();
    private ExpReportDao expReportDao = new ExpReportDaoImpl();
    private SectionDao sectionDao = new SectionDaoImpl();
    private InstructorDao instructorDao = new InstructorDaoImpl();
    private ExpScoreDao expScoreDao = new ExpScoreDaoImpl();
    private TakesDao takesDao = new TakesDaoImpl();
    private ReferenceDao referenceDao = new ReferenceDaoImpl();
    private AttendDao attendDao = new AttendDaoImpl();
    private ChoiceQuestionDao choiceQuestionDao = new ChoiceQuestionDaoImpl();
    private AttendScoreDao attendScoredao = new AttendScoreDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();
    private CounterDao counterDao = new CounterDaoImpl();

    @Override
    public Section getSection(String courseID, String classID) {
        return sectionDao.QuerySectionByCourseIDAndClassID(courseID, classID);
    }

    @Override
    public List<Map<String, String>> GetSections(String instructorNumber) {
        //创建存储sections信息的列表
        List<Map<String, String>> sectionInfoList = new ArrayList<>();
        List<Teaches> teachesList = teachesDao.QueryTeachesByInstructorNumber(instructorNumber);

        for (Teaches t : teachesList) {
            Map<String, String> sectionInfo = new HashMap<>();
            //获取courseID和classID
            sectionInfo.put("courseID", t.getCourseID());
            sectionInfo.put("classID", t.getClassID());
            //获取课程名
            Course course = courseDao.QueryCourseByCourseID(t.getCourseID());
            sectionInfo.put("title", course.getTitle());
            //获取duty信息
            String duty = "教师";
            if (course.getInstructorNumber().equals(instructorNumber)) {
                duty = duty + ",责任教师";
            }
            sectionInfo.put("duty", duty);

            sectionInfoList.add(sectionInfo);
        }

        return sectionInfoList;
    }

    @Override
    public List<Map<String, String>> GetCourseExpInfo(String courseID,String classID) {
        List<Map<String, String>> courseExpInfoList = new ArrayList<>();

        List<CourseExp> courseExpList = courseExpDao.QueryCourseExpsByCourseID(courseID);
        //开始整理信息
        for (CourseExp c : courseExpList) {
            if("考勤".equals(c.getExpname()) || "对抗练习".equals(c.getExpname())){
                continue;
            }
            Map<String, String> courseExpInfo = new HashMap<>();
            courseExpInfo.put("title", c.getExpname());
            courseExpInfo.put("priority", Integer.toString(c.getPriority()));
            courseExpInfo.put("difficulty", Integer.toString(c.getDifficulty()));
            courseExpInfo.put("weight", c.getPercent() + "%");
            String startDate = "";
            String status = "未发布";
            Experiment experiment = experimentDao.QueryExperiment(courseID,classID,c.getExpname());
            if(experiment!=null){
                startDate = experiment.getStartDate();
                status = "已发布";
            }
            courseExpInfo.put("startDate",startDate);
            courseExpInfo.put("status", status);
            //加入到信息列表
            courseExpInfoList.add(courseExpInfo);
        }

        return courseExpInfoList;
    }

    @Override
    public int ReleaseExperiment(String courseID, String expname, String classID,String startDate,String endDate, String expInfo) {
        return experimentDao.InsertExperiment(courseID, expname, classID, startDate,endDate, expInfo);
    }

    @Override
    public Map<String, String> ExamineExperimentInfo(String courseID, String classID, String expname) {
        Map<String, String> experimentInfo = new HashMap<>();
        //根据主码锁定实验
        Experiment experiment = experimentDao.QueryExperiment(courseID, classID, expname);
        experimentInfo.put("startDate", experiment.getStartDate());
        experimentInfo.put("endDate", experiment.getEndDate());
        experimentInfo.put("expInfo", experiment.getExpInfo());

        return experimentInfo;
    }

    @Override
    public int ModifyExperiment(String courseID, String classID, String expname, String endDate, String expInfo) {
        return experimentDao.UpdateExperiment(courseID, classID, expname, endDate, expInfo);
    }

    @Override
    public int ReleaseNotice(String courseID, String classID, String instructorNumber, String content, String date, String title) {
        return noticeDao.InsertNotice(courseID, classID, date, content, instructorNumber, title);
    }

    @Override
    public int DeleteNotice(String courseID, String classID, String instructorNumber, String date) {
        return noticeDao.DeleteNotice(courseID, classID, instructorNumber, date);
    }

    @Override
    public int ReleaseReportDesc(String courseID, String classID, String expname, String reportName, String reportInfo, String startDate, String endDate, String fileType) {
        return expReportDao.InsertExpReport(courseID, expname, classID, reportName, endDate, reportInfo, fileType, startDate);
    }

    @Override
    public int DeleteReportDesc(String courseID, String classID, String expname, String reportName) {
        return expReportDao.DeleteExpReport(courseID, classID, expname, reportName);
    }

    @Override
    public int ModifyReportDesc(String courseID, String classID, String expname, String reportName, String reportInfo, String endDate, String fileType) {
        return expReportDao.UpdateReportDesc(courseID, classID, expname, reportName, reportInfo, endDate, fileType);
    }

    @Override
    public List<Map<String, String>> GetSectionInfoOfCourse(String courseID) {
        List<Map<String, String>> sectionInfoList = new ArrayList<>();
        //先找到课程的所有班级（有些班级可能暂时没有老师教授，也可能有多个教师教授）
        List<Section> sectionList = sectionDao.QuerySectionByCourseID(courseID);
        for (Section section : sectionList) {
            Map<String, String> sectionInfo = new HashMap<>();
            //classID,day,time,currentNumber,maxNubmer
            sectionInfo.put("classID", section.getClassID());
            sectionInfo.put("day", "星期" + section.getDay());
            sectionInfo.put("time", "第" + section.getTime() + "节课");
            sectionInfo.put("currentNumber", section.getCurrentNumber() + "人");
            sectionInfo.put("maxNumber", section.getNumber() + "人");
            //开始从teaches中找任课教师
            List<Teaches> instructorList = teachesDao.QueryTeachesByCourseIDAndClassID(courseID, section.getClassID());
            if (instructorList == null) {
                //没有任课教师
                sectionInfo.put("instructorName", "暂无");
                sectionInfo.put("instructorNumber", "暂无");
            } else {
                //有任课教师
                String instructorName = "";
                String instructorNumber = "";
                Iterator<Teaches> iterator = instructorList.iterator();
                while (iterator.hasNext()) {
                    Teaches t = iterator.next();
                    //找教师名
                    Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(t.getInstructorNumber());
                    instructorName = instructorName + instructor.getName();
                    instructorNumber = instructorNumber + instructor.getInstructorNumber();
                    if (iterator.hasNext()) {
                        instructorName += ",";
                        instructorNumber += ",";
                    }
                }
                sectionInfo.put("instructorName", instructorName);
                sectionInfo.put("instructorNumber", instructorNumber);
            }
            sectionInfoList.add(sectionInfo);
        }

        return sectionInfoList;
    }

    @Override
    public List<ExpScore> getSubmittedStudentList(String courseID, String classID, String expname) {
        return expScoreDao.QueryExpScoresByExperiment(courseID,expname,classID);
    }

    @Override
    public List<String> getStudentNumbersByCourseIDAndClassID(String courseID, String classID) {
        List<Takes> takesList = takesDao.QueryTakesByCourseIDAndClassID(courseID,classID);
        List<String> studentList = new ArrayList<>();
        for(Takes takes : takesList){
            studentList.add(takes.getStudentNumber());
        }
        return studentList;
    }

    @Override
    public boolean checkReference(String fileUrl) {
        if(referenceDao.QueryReferenceByfileUrl(fileUrl)!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int recordCommit(String courseID, String classID, String instructorNumber, String fileUrl) {
        return referenceDao.InsertReference(courseID, classID, instructorNumber, fileUrl);
    }

    @Override
    public int getCourseAttendPercent(String courseID) {
        CourseExp courseExp = courseExpDao.QueryCourseExpByCourseIDAndExpname(courseID,"考勤");
        return courseExp.getPercent();
    }

    @Override
    public List<Attend> getAttendsBefore(String courseID, String classID) {
        return attendDao.QueryAttendsByCourseIDAndClassID(courseID,classID);
    }

    @Override
    public int addAttend(String courseID, String classID, String attendName, String startTime, String endTime) {
        return attendDao.InsertAttend(courseID,classID,attendName,startTime,endTime);
    }

    @Override
    public int deleteReference(String fileUrl) {
        return referenceDao.DeleteReferenceByFileUrl(fileUrl);
    }
    @Override
    public int addSection(String courseID,String instructorNumber, int day, int time, int number) {
        Counter counter = counterDao.QueryCounterById(1);
        //上一个classID
        int classID = counter.getClassID();
        //本课程的classID
        String newClassID = classID + 1 + "";
        if(sectionDao.insertSection(courseID,newClassID,day, time,number)==0){
            return 0;
        }
        //更新courseID计数
        counterDao.UpdateCourseIDOfCounter(classID+1);
        return teachesDao.insertTeaches(instructorNumber,courseID,newClassID);
    }

    @Override
    public String createCourse(String title, String instructorNumber, String startDate, String endDate) {
        Counter counter = counterDao.QueryCounterById(1);
        //上一个courseID
        int courseId = counter.getCourseID();
        //本课程的courseID
        String newCourseID = courseId + 1 + "";
        //添加课程
        if(courseDao.InsertCourse(newCourseID,title,instructorNumber,startDate,endDate)!=1) {
            return "";
        }
        //更新courseID计数
        counterDao.UpdateCourseIDOfCounter(courseId+1);
        return newCourseID;
    }

    @Override
    public void addCourseExp(String courseID, List<Map<String,Object>> courseExpInfoList,int attendanceWeight,int practiceWeight) {
        //添加实验
        for(Map<String, Object> c : courseExpInfoList){
            courseExpDao.InsertCourseExp(courseID,(String)c.get("expname"),(int)c.get("percent"),(int)c.get("priority"),(int)c.get("difficulty"));
        }
        //添加考勤
        courseExpDao.InsertCourseExp(courseID,"考勤",attendanceWeight,1,1);
        //添加对抗练习
        courseExpDao.InsertCourseExp(courseID,"对抗练习",attendanceWeight,1,1);
    }

    @Override
    public int registerGrade(String courseID, String classID, String studentNumber, String expname, float score, String comment) {
        return expScoreDao.UpdateExpScore(studentNumber,courseID,expname,classID,score,comment);
    }

    @Override
    public List<ChoiceQuestion> getRandomQuestionList(int size) {
        //随机数的上限
        int count = choiceQuestionDao.getCount();
        List<ChoiceQuestion> choiceQuestionList = new ArrayList<ChoiceQuestion>();
        HashSet<Integer> set = RandomHandler.createNonRepeatingRandom(size, 1, count);
        for (Integer questionId : set) {
            choiceQuestionList.add(choiceQuestionDao.getQuestionByQuestionId(questionId));
        }

        return choiceQuestionList;

    }

    @Override
    public List<AttendScore> getAttendScoreByCourseIDAndClassIDAndTitle(String courseID, String classID, String title) {
        return attendScoredao.getAttendScoreByCourseIDAndClassIDAndTitle(courseID, classID, title);
    }

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        return studentDao.QueryStudentByStudentNumber(studentNumber);
    }

    @Override
    public List<Takes> getTakesNotInAttendScore(String courseID, String classID, String title) {
        return takesDao.queryTakesNotInAttendScore(courseID, classID, title);
    }

    @Override
    public List<Teaches> getTeachesListByInstructorNumber(String instructorNumber) {
        return teachesDao.QueryTeachesByInstructorNumber(instructorNumber);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorDao.QueryAllInstructors();
    }

}
