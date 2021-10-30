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
    public Map<String, Object> GetTakesInfoByStudentNumber(String studentNumber) {

        Map<String,Object> map = new HashMap<>();
        //存储课程有关信息的集合
        List<SectionInformation> informationList = new ArrayList<>();

        //获取学生姓名和学号，并加入到map中
        Student student = studentDao.QueryStudentByStudentNumber(studentNumber);
        map.put("name",student.getName());
        map.put("studentNumber",studentNumber);

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
            Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(course.getInstructorNumber());
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
        //informationList可以不用转换成数组而加入到map，因为底层实现本就是数组
        map.put("sectionInformation",informationList);
        return map;
    }

    @Override
    public Map<String, Object> GetTeachesInfoByInstructorNumber(String instructorNumber) {

        Map<String,Object> map = new HashMap<>();
        List<SectionInformation> informationList = new ArrayList<>();

        //获取老师的名字和学号
        Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(instructorNumber);
        map.put("name",instructor.getName());
        map.put("instructorNumber",instructorNumber);
        //与该教师相关的所有课程
        List<Teaches> teachesList = teachesDao.QueryTeachesByInstructorNumber(instructorNumber);
        //开始遍历课程
        Iterator<Teaches> iterator = teachesList.iterator();
        while(iterator.hasNext()){
            Teaches teaches = iterator.next();
            SectionInformation sectionInformation = new SectionInformation();
            //获取班级号
            sectionInformation.setClassID(teaches.getClassID());
            //获取班级名称
            Course course = courseDao.QueryCourseByCourseID(teaches.getCourseID());
            sectionInformation.setTitle(course.getTitle());
            //获取任课身份
            if(course.getInstructorNumber().equals(instructorNumber)){
                sectionInformation.setDuty("责任教师");
            }else{
                sectionInformation.setDuty("教师");
            }
            //获取上课时间
            Section section = sectionDao.QuerySectionByCourseIDAndClassID(teaches.getCourseID(),teaches.getClassID());
            sectionInformation.setDay("星期"+section.getDay());
            sectionInformation.setTime("第"+section.getTime()+"节课");

            //将课程相关信息加入到列表
            informationList.add(sectionInformation);
        }
        //将任课列表加入到map
        map.put("sectionInformation",informationList);
        return map;
    }

    @Override
    public String ChangeStudentDuty(String studentNumber, String courseID, String classID,String duty) {
        String msg = "changing duty failed!";  //用于记录修改结果是否成功的信息

        if(takesDao.SetDuty(studentNumber,courseID,classID,duty)==1){
            //修改成功
            msg = "success";
        }

        return msg;
    }

    @Override
    public boolean DeleteStudent(String email) {
        return false;
    }

    @Override
    public String AddInstructor(String instructorNumber,String email,String name,String phoneNumber,int sex) {
        String msg = null;  //用于记录添加结果是否成功的信息
        //1.先检查Email是否重复
        Instructor instructor = instructorDao.QueryInstructorByEmail(email);
        if(instructor != null){
            msg = "Email already exists!";
            return msg;
        }
        //2.email没问题再插入学生信息
        int insertResult = instructorDao.InsertInstructor(instructorNumber,email,name,phoneNumber,sex);
        if(insertResult == 1){
            msg = "StudentNumber already exists!";
            return msg;
        }
        msg = "success";
        return msg;  //如果没有任何意外，msg为"success"
    }

    @Override
    public Map<String,Object> SearchInstructorByInstructorNumber(String instructorNumber) {
        Map<String,Object> map = new HashMap<>();

        Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(instructorNumber);
        if(instructor != null){
            map.put("instructorNumber",instructorNumber);
            map.put("name",instructor.getName());
            map.put("sex",instructor.getSex());
            map.put("phoneNumber",instructor.getPhoneNumber());
            map.put("email",instructor.getEmail());
        }

        return map;
    }

    @Override
    public Map<String,Object> CheckTeacherDuty(String courseID) {
        Course course = courseDao.QueryCourseByCourseID(courseID);
        //课程的责任教师工号
        String instructorNumber = course.getInstructorNumber();
        //获取教师名
        Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(instructorNumber);
        String name = instructor.getName();
        //加入到map
        Map<String,Object> map = new HashMap<>();
        map.put("instructorNumber",instructorNumber);
        map.put("name",name);
        return map;
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
