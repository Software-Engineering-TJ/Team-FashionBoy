package web;

import com.google.gson.reflect.TypeToken;
import service.Impl.AdministrationServiceImpl;
import service.inter.AdministrationService;
import utils.RequestJsonUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "StudentServlet", value = "/studentServlet")
public class StudentServlet extends BaseServlet {
    AdministrationService administrationService = new AdministrationServiceImpl();

    protected void getTakes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String studentNumber = reqObject.get("studentNumber");
        //获取学生——课程详细信息
        Map<String,Object> map = administrationService.GetTakesInfoByStudentNumber(studentNumber);
        //返回必要信息:这里直接用了管理员获取学生课程的方法，所以信息量足够，前端可以根据需要读取
        Map<String,Object> takes = new HashMap<>();
        takes.put("sections",map.get("sectionInformation"));

        resp.getWriter().write(gson.toJson(takes));
    }
}
