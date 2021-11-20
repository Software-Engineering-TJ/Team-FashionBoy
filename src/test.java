import dao.impl.InstructorDaoImpl;
import dao.impl.StudentDaoImpl;
import dao.inter.InstructorDao;
import dao.inter.StudentDao;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.Administrator;
import pojo.Student;
import service.Impl.AdministrationServiceImpl;
import service.Impl.UserServiceImpl;
import service.inter.AdministrationService;
import service.inter.UserService;
import web.AdministrationServlet;
import web.UserServlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {

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

//        AdministrationService administrationService = new AdministrationServiceImpl();
//        String studentNumber = "1959874";
//        Student student = administrationService.getStudentByStudentNumber(studentNumber);
//        System.out.println(student);


        //创建工作簿对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\93954\\Desktop\\test.xlsx"));
        //获取工作簿下sheet的个数
        int sheetNum = xssfWorkbook.getNumberOfSheets();
        System.out.println("该excel文件中总共有：" + sheetNum + "个sheet");
        //遍历工作簿中的所有数据
        for (int i = 0; i < sheetNum; i++) {
            //读取第i个工作表
            System.out.println("读取第" + (i + 1) + "个sheet");
            XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
            //获取最后一行的num，即总行数。此处从0开始
            int maxRow = sheet.getLastRowNum();
            for (int row = 0; row <= maxRow; row++) {
                //获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                int maxRol = sheet.getRow(row).getLastCellNum();
                System.out.println("--------第" + row + "行的数据如下--------");
                for (int rol = 0; rol < maxRol; rol++) {
                    System.out.print(sheet.getRow(row).getCell(rol) + "  ");
                }
                System.out.println();
            }
        }
    }
}
