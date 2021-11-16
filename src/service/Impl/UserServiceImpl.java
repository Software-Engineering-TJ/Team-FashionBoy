package service.Impl;

import dao.inter.AdministratorDao;
import dao.inter.InstructorDao;
import dao.inter.StudentDao;
import dao.impl.AdministratorDaoImpl;
import dao.impl.InstructorDaoImpl;
import dao.impl.StudentDaoImpl;
import pojo.Administrator;
import pojo.Instructor;
import pojo.Student;
import pojo.User;
import service.inter.UserService;

/**
 * UserServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:59
 */

public class UserServiceImpl implements UserService{

    private AdministratorDao administratorDao = new AdministratorDaoImpl();
    private InstructorDao  instructorDao = new InstructorDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();

    @Override
    public User ExistEmail(String email) {
        Student student = studentDao.QueryStudentByEmail(email);
        if(student == null){
            Instructor instructor = instructorDao.QueryInstructorByEmail(email);
            if(instructor == null){
                Administrator administrator = administratorDao.QueryAdministratorByEmail(email);
                if(administrator == null){
                    return null; //三个表都不存在时才返回“不存在”
                }
                return administrator;
            }
            return instructor;
        }
        return student;
    }

    @Override
    public User ifActivated(String userNumber) {
        Student student = studentDao.QueryStudentByStudentNumber(userNumber);
        if(student == null){
            Instructor instructor = instructorDao.QueryInstructorByInstructorNumber(userNumber);
            if(instructor == null){
                return administratorDao.QueryAdministratorByNumber(userNumber);
            }else {
                return instructor;
            }
        }else{
            return student;
        }
    }

    @Override
    public int alterUserInfo(String identity, String userNumber, String phoneNumber, String email) {
        int result = 0;

        if(identity.equals("student")){
            //修改者是“学生”
            result = studentDao.updateStudent(userNumber,email,phoneNumber);
        }else if(identity.equals("instructor")){
            //修改者是“教师”
            result = instructorDao.updateInstructor(userNumber,email,phoneNumber);
        }else{
            //修改者是“管理员”
//            result = administratorDao.updateAdministrator(userNumber,email,phoneNumber);
        }

        return result;
    }
}
