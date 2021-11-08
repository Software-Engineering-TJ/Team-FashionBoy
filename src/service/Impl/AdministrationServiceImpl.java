package service.Impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.impl.AdministratorDaoImpl;
import dao.impl.TakesDaoImpl;
import dao.inter.AdministratorDao;
import dao.inter.InstructorDao;
import dao.inter.StudentDao;
import dao.impl.InstructorDaoImpl;
import dao.impl.StudentDaoImpl;
import dao.inter.TakesDao;
import pojo.Administrator;
import pojo.Student;
import pojo.Takes;
import pojo.Teaches;
import service.inter.AdministrationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AdministrationServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/21  14:08
 */

public class AdministrationServiceImpl implements AdministrationService {

    private StudentDao studentDao = new StudentDaoImpl();
    private InstructorDao instructorDao = new InstructorDaoImpl();
    private AdministratorDao administratorDao = new AdministratorDaoImpl();
    private TakesDao takesDao = new TakesDaoImpl();
    //后续肯定还需要takes、teaches


    @Override
    public String AddStudent(String studentNumber,String email,String password, String name,int sex, String phoneNumber, String status) {
        String msg = null;  //用于记录添加结果是否成功的信息
        //1.先检查Email是否重复
        Student student = studentDao.QueryStudentByEmail(email);
        if(student != null){
            msg = "Email already exists!";
            return msg;
        }
        //2.email没问题再插入学生信息
        int insertResult = studentDao.InsertStudent(studentNumber,email,password,name,sex,phoneNumber,status);
        if(insertResult == 1){
            msg = "StudentNumber already exists!";
        }
        msg = "success";
        return msg;  //如果没有任何意外，msg为"success"
    }

    @Override
    public Map<String, Object> getTakesByStudentNumber(String studentNumber) {
        //获取学生信息
        Student student = studentDao.QueryStudentByStudentNumber(studentNumber);
        String name = student.getName();
        //接着查询takes表中和该学号相关的表
        List<Takes> takesList = takesDao.QueryTakesByStudentNumber(studentNumber);
//        for()
        return null;
    }

    public List<JsonElement> getInfoByAdminNumber(String adminNumber) {
        Administrator admin = administratorDao.QueryAdministratorByNumber(adminNumber);
        List<JsonElement> info = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        info.add(jsonParser.parse(admin.getAdminNumber()));
        info.add(jsonParser.parse(admin.getName()));
        info.add(jsonParser.parse(admin.getEmail()));
        return info;
    }

    public boolean changeAdministrationInfo(JsonObject jsonObject) {
        String email = jsonObject.get("email").toString();
        String name = jsonObject.get("name").toString();
        String adminNumber = jsonObject.get("adminNumber").toString();
        String password = jsonObject.get("password").toString();

        return administratorDao.updateAdministrator(adminNumber, email, password, name) != -1;
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
