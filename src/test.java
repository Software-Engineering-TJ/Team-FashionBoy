import dao.impl.InstructorDaoImpl;
import dao.impl.StudentDaoImpl;
import dao.inter.InstructorDao;
import dao.inter.StudentDao;
import pojo.Administrator;
import pojo.Student;
import service.Impl.AdministrationServiceImpl;
import service.Impl.UserServiceImpl;
import service.inter.AdministrationService;
import service.inter.UserService;
import web.AdministrationServlet;
import web.UserServlet;

public class test {

    public static void main(String[] args) {

//        InstructorDaoImpl instructorDao = new InstructorDaoImpl();
//        instructorDao.InsertInstructor("1851234", "987654321@qq.com","牛掰","15898746321", 0);
//
//        StudentDao studentDao = new StudentDaoImpl();
//        studentDao.InsertStudent("1959874", "123456789@qq.com", "安妮", "98745632101", 0);
//
//
//        UserService userService = new UserServiceImpl();

//        String instructorNumber = "1851234"; //主码
//        String email = "email";
//        String password = "password";
//        int sex = 1;
//        String phoneNumber = "phoneNumber";
//        int msg = userService.alterInstructorInformation(instructorNumber, email, password, sex, phoneNumber);

//        String studentNumber = "1959874"; //主码
//        String email = "email";
//        String password = "password";
//        int sex = 1;
//        String phoneNumber = "phoneNumber";
//        int msg = userService.alterStudentInformation(studentNumber, email, password, sex, phoneNumber);

//        UserServlet userServlet = new UserServlet();
//        userServlet.sendEmail("939543598@qq.com");

        AdministrationService administrationService = new AdministrationServiceImpl();
        String studentNumber = "1959874";
        Student student = administrationService.getStudentByStudentNumber(studentNumber);
        System.out.println(student);


    }
}
