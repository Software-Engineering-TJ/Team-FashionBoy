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
    public User Login(String userNumber, String password) {
        //先找学生
        Student student = studentDao.QueryStudentByStudentNumberAndPassword(userNumber,password);
        if(student==null) {
            //找不到学生找老师
            Instructor instructor = instructorDao.QueryInstructorByInstructorNumberAndPassword(userNumber,password);
            if(instructor==null){
                //管理员用邮箱登录，username不查找
                return null;
            }else{
                return instructor;
            }
        }else{
            return student;
        }
    }

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
                return null;
            }else {
                return instructor;
            }
        }else{
            return student;
        }
    }

    @Override
    public User Register(String email, String password) {
        return null;
    }

    @Override
    public User Register(String email) {
        return null;
    }
}
