package service.Impl;

import dao.AdministratorDao;
import dao.InstructorDao;
import dao.StudentDao;
import dao.impl.AdministratorDaoImpl;
import dao.impl.InstructorDaoImpl;
import dao.impl.StudentDaoImpl;
import pojo.Administrator;
import pojo.Instructor;
import pojo.Student;
import pojo.User;
import service.UserService;

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
    public User Login(String email, String password) {
        //先找学生
        Student student = studentDao.QueryStudentByEmailAndPassword(email,password);
        if(student==null) {
            //找不到学生找老师
            Instructor instructor = instructorDao.QueryInstructorByEmailAndPassword(email,password);
            if(instructor==null){
                //最后找管理员
                return administratorDao.QueryAdministratorByEmailAndPassword(email,password);
            }else{
                return instructor;
            }
        }else{
            return student;
        }
    }

    @Override
    public Boolean ExistEmail(String email) {
        if(studentDao.QueryStudentByEmail(email)==null){
            if(instructorDao.QueryInstructorByEmail(email)==null){
                if(administratorDao.QueryAdministratorByEmail(email)==null){
                    return false; //三个表都不存在时才返回“不存在”
                }
                return true;
            }
            return true;
        }
        return true;
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
