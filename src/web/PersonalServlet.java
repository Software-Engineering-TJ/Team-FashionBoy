package web;

import com.google.gson.reflect.TypeToken;
import dao.impl.AdministratorDaoImpl;
import dao.inter.AdministratorDao;
import pojo.User;
import service.Impl.UserServiceImpl;
import service.inter.UserService;
import utils.RequestJsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Strange
 * @date 2021/11/8 21:01
 * @description: 编辑本人（student, instructor）账户登记信息
 */

public class PersonalServlet extends BaseServlet{

    protected UserService userService = new UserServiceImpl();

    /**
     * @author Strange
     * @date: 2021/11/8 20:53
     * @description: 接受网页的参数，保存进数据库中。不可变参数：status, studentNumber, name
     * @param: req
     * @param: resp
     * @return: 无
     */
    protected void alterStudentInformation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String studentNumber = req.getParameter("studentNumber");; //主码
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        int sex = Integer.parseInt(req.getParameter("sex"));
        String phoneNumber = req.getParameter("phoneNumber");
        int msg = userService.alterStudentInformation(studentNumber, email, password, sex, phoneNumber);

        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("stu_info_msg",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

    /**
     * @author Strange
     * @date: 2021/11/8 20:53
     * @description: 接受网页的参数，保存进数据库中。不可变参数：status, studentNumber, name
     * @param: req
     * @param: resp
     * @return: 无
     */
    protected void alterInstructorInformation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String instructorNumber = "1"; //主码
        String email = "11";
        String password = req.getParameter("password");
        int sex = 1;
        String phoneNumber = req.getParameter("phoneNumber");
        int msg = userService.alterInstructorInformation(instructorNumber, email, password, sex, phoneNumber);

        //返回响应
        Map<String,Object> map = new HashMap<>();
        map.put("ins_info_msg",msg);
        String msgJson = gson.toJson(map);
        resp.getWriter().write(msgJson);
    }

}
