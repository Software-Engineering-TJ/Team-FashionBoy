package service.Impl;

import dao.impl.*;
import dao.inter.*;
import pojo.*;
import pojo.logicEntity.SectionInformation;
import service.inter.AdministrationService;

import java.util.*;

/**
 * AdministrationServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/21  14:08
 */

public class AdministrationServiceImpl implements AdministrationService {

    private StudentDao studentDao = new StudentDaoImpl();
    private InstructorDao instructorDao = new InstructorDaoImpl();
    private TakesDao takesDao = new TakesDaoImpl();
    private TeachesDao teachesDao = new TeachesDaoImpl();
    private CourseDao courseDao = new CourseDaoImpl();
    private SectionDao sectionDao = new SectionDaoImpl();
    //后续肯定还需要takes、teaches


    @Override
    public String AddStudent(String studentNumber,String email,String name,String phoneNumber,int sex) {
        String msg = null;  //用于记录添加结果是否成功的信息
        //1.先检查Email是否重复
        Student student = studentDao.QueryStudentByEmail(email);
        if(student != null){
            msg = "Email already exists!";
            return msg;
        }
        //2.email没问题再插入学生信息
        int insertResult = studentDao.InsertStudent(studentNumber,email,name,phoneNumber,sex);
        if(insertResult == 1){
            msg = "StudentNumber already exists!";
            return msg;
        }
        msg = "success";
        return msg;  //如果没有任何意外，msg为"success"
    }


    @Override
    public Map<String, Object> getTakesInfoByStudentNumber(String studentNumber) {

        Map<String,Object> map = new HashMap<>();

        //获取学生姓名和学号，并加入到map中
        Student student = studentDao.QueryStudentByStudentNumber(studentNumber);
        map.put("name",student.getName());
        map.put("studentNumber",studentNumber);
        //存储课程有关信息的集合
        List<SectionInformation> informationList = new ArrayList<>();
        //接着查询takes表中和该学号相关的表
        List<Takes> takesList = takesDao.QueryTakesByStudentNumber(studentNumber);
        Iterator<Takes> iterator = takesList.iterator();
        //遍历该学生所有课程
        while(iterator.hasNext()){
            Takes takes = iterator.next();
            SectionInformation information = new SectionInformation();
            //确定学生职责
            information.setDuty((takes.getStatus()==0)?"学生":"助教");
            //确定班级号
            information.setClassID(takes.getClassID());
            //确定课程名字和责任教师名
            Course course = courseDao.QueryCourseByCourseID(takes.getCourseID());
            information.setTitle(course.getTitle());
            Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(course.getInstructor());
            information.setChargingTeacher(instructor.getName());
            //确定课程时间
            Section section = sectionDao.QuerySectionByCourseIDAndClassID(takes.getCourseID(),takes.getClassID());
            information.setDay("星期"+section.getDay());
            information.setTime("第"+section.getDay()+"节课");
            //确定教师名（可能有多个任课老师，就像一些选修课，当然概率很低，但考虑到实际情况，逻辑上尽量完善吧）
            String allTeachers;  //所有老师名字，用","隔开
            List<Teaches> teachesList = teachesDao.QueryTeachesByCourseIDAndClassID(takes.getCourseID(),takes.getCourseID());
            Iterator<Teaches> iterator1 = teachesList.iterator();
            Instructor firstInstructor = instructorDao.QueryInstructorByInstructorNumber(iterator1.next().getInstructorNumber());
            allTeachers = firstInstructor.getName();
            while(iterator1.hasNext()){
                Instructor nextInstructor = instructorDao.QueryInstructorByInstructorNumber(iterator1.next().getInstructorNumber());
                allTeachers = allTeachers + ","+nextInstructor.getName();
            }
            informationList.add(information);
        }
        //informationList以对象数组的形式加入到map，方便后续转换成json
        map.put("sectionInformation",informationList.toArray());
        return map;
    }

    @Override
    public boolean DeleteStudent(String email) {
        return false;
    }

    @Override
    public boolean AddInstructor(String email, String password) {
        return false;
    }

    @Override
    public boolean DeleteInstructor(String email) {
        return false;
    }

    @Override
    public List<Takes> SearchTakesOfStudent(String email) {
        return null;
    }

    @Override
    public List<Teaches> SearchTeachesOfInstructor(String email) {
        return null;
    }

    @Override
    public boolean SetStudentStatus(String email, String courseID, String classID, int status) {
        return false;
    }

    @Override
    public boolean SetInstructorStatus(String email, String courseID, String classID, int status) {
        return false;
    }
}
