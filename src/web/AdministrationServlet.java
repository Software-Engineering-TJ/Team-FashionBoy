package web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pojo.Administrator;
import pojo.Instructor;
import pojo.Student;
import service.Impl.UserServiceImpl;
import service.inter.AdministrationService;
import service.Impl.AdministrationServiceImpl;
import service.inter.UserService;
import utils.RequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AdministrationServlet类的描述：
 * 处理管理员的请求：添加/删除/修改老师和学生的账户、浏览学生和老师相关的课程、设置老师和学生在具体课程的权限
 * @author 黄金坤（HJK）
 * @since 2021/10/21  14:03
 */

public class AdministrationServlet extends BaseServlet{

    private AdministrationService administrationService = new AdministrationServiceImpl();
    protected UserService userService = new UserServiceImpl();


    /**
     * @author Strange
     * @date: 2021/11/8 20:53
     * @description: 接受网页的参数，保存进数据库中。不可变参数：status, studentNumber, name
     * @param: req
     * @param: resp
     * @return: 无
     */
    protected void alterUserInformation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String userNumber = reqObject.get("userNumber");; //主码
        String email = reqObject.get("email");
        String name = reqObject.get("name");
        int sex = (reqObject.get("sex").equals("男"))?1:0;
        String phoneNumber = reqObject.get("phoneNumber");
        String identify = reqObject.get("identify");
        int msg = 0;
        if ("student".equals(identify)){
            msg = userService.alterStudentInformation(userNumber, email, name, sex, phoneNumber);
        }
        else if ("teacher".equals(identify)){
            msg = userService.alterInstructorInformation(userNumber, email, name, sex, phoneNumber);
        }

        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }


    protected void getAdministrationInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String adminNumber = reqObject.get("adminNumber");
        Administrator administrator = administrationService.getAdministrationInfo(adminNumber);

        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("admin",administrator);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     *
     * @param req √
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getStudentByStudentNumber(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String studentNumber = reqObject.get("studentNumber");
        Student student = administrationService.getStudentByStudentNumber(studentNumber);

        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("student",student);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     * 创建新的学生账号 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createStudent(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String name = reqObject.get("name");
        String sexString = reqObject.get("sex");
        int sex = 0;
        if(sexString.equals("男")){
            sex = 1;
        }
        String studentNumber = reqObject.get("studentNumber");
        String email = reqObject.get("email");
        String phoneNumber = reqObject.get("phoneNumber");

        String msg;        //记录添加结果:成功”success“、失败的话msg包含错误提示
        msg = administrationService.AddStudent(studentNumber,email,name,phoneNumber,sex);
        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("msg",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     * 创建教师账号 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected  void createTeacher(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String name = reqObject.get("name");
        String sexString = reqObject.get("sex");
        int sex = 0;
        if(sexString.equals("男")){
            sex = 1;
        }
        String instructorNumber = reqObject.get("instructorNumber");
        String email = reqObject.get("email");
        String phoneNumber = reqObject.get("phoneNumber");

        String msg;        //记录添加结果:成功”success“、失败的话msg包含错误提示
        msg = administrationService.AddInstructor(instructorNumber,email,name,phoneNumber,sex);
        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("result",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     * 根据学生的学号获得他参与的课程信息 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTakesByStudentNumber(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String studentNumber = reqObject.get("studentNumber");
        //获取查询结果
        Map<String,Object> map = administrationService.GetTakesInfoByStudentNumber(studentNumber);
        //返回响应
        String mapJson = gson.toJson(map);
        resp.getWriter().write(mapJson);
    }

    /**
     * 根据老师的工号获得老师信息，用于搜索对应的老师 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTeacherByTeacherNumber(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String instructorNumber = reqObject.get("instructorNumber");
        //将信息填入map
        Map<String,Object> map = administrationService.SearchInstructorByInstructorNumber(instructorNumber);
        //返回响应
        String mapJson = gson.toJson(map);
        resp.getWriter().write(mapJson);
    }

    /**
     * 根据教师工号获得教授的课程信息 √
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getTeachesByTeacherNumber(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());
        String instructorNumber = reqObject.get("instructorNumber");
        //获取任课信息
        Map<String,Object> map = administrationService.GetTeachesInfoByInstructorNumber(instructorNumber);
        //JSON化后传给前端
        String mapJson = gson.toJson(map);
        resp.getWriter().write(mapJson);
    }

    /**
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void changeStudentDuty(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String studentNumber = reqObject.get("studentNumber");
        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String duty = reqObject.get("duty");
        //获取修改结果
        String msg =  administrationService.ChangeStudentDuty(studentNumber,courseID,classID,duty);
        Map<String,Object> map = new HashMap<>();
        map.put("result",msg);
        if ("学生".equals(duty)){
            map.put("duty","助教");
        }else {
            map.put("duty","学生");
        }
        //JSON化
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 修改某个课程下该教师的职务。（将普通老师改为责任教师）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void changeDutyInstructor(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        //获取 哪个老师 想设置为 哪个课程 的责任教师
        String instructorNumber = reqObject.get("instructorNumber");
        String courseID = reqObject.get("courseID");
        String duty = reqObject.get("duty");
        //获取修改结果
        String msg = administrationService.ChangeDutyInstructor(instructorNumber,courseID);
        Map<String,Object> map = new HashMap<>();
        map.put("result",msg);
        if ("教师".equals(duty)){
            map.put("duty","责任教师");
        }else {
            map.put("duty","教师");
        }
        //JSON化
        resp.getWriter().write(gson.toJson(map));
    }

    /**
     * 查看某门课程下的责任教师
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkTeacherDuty(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        String courseID = req.getParameter("courseID");
        //获取责任教师的工号和姓名
        Map<String,Object> map = administrationService.CheckTeacherDuty(courseID);
        //JSON化
        resp.getWriter().write(gson.toJson(map));
    }

    protected void DeleteUser(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {

        String email = req.getParameter("email");
        //1.根据req中的具体请求，确定删除的是老师or学生
        String identity = req.getParameter("identity");

        boolean result;
        if(identity.equals("student")){
            result = administrationService.DeleteStudent(email);
        }else{
            result = administrationService.DeleteInstructor(email);
        }

        if(!result){
            //删除失败，提醒重新尝试
        }else{
            //成功
        }

    }

    protected void createStudentFromExcel(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

        //前端将本地文件拖拽到上传区，程序将文件拷贝一份存在web-inf路径下（此处应当调用上传文件的接口）

        File file = new File("获取相对路径的excel文件");
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 1; i < sheet.getRows(); i++) {
                    // sheet.getColumns()返回该页的总列数
                    String studentNumber = sheet.getCell(0, i).getContents();
                    String email = sheet.getCell(1, i).getContents();
                    String name = sheet.getCell(2, i).getContents();
                    int sex = Integer.parseInt(sheet.getCell(3, i).getContents());
                    String phoneNumber = sheet.getCell(4, i).getContents();
                    administrationService.AddStudent(studentNumber, email, name, phoneNumber, sex);

                }
            }
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
    }

}
