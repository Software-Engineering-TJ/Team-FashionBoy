import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.impl.AdministratorDaoImpl;
import dao.impl.BaseDao;
import dao.impl.StudentDaoImpl;
import dao.inter.AdministratorDao;
import dao.inter.StudentDao;
import pojo.Administrator;
import pojo.Student;

public class Main {
    public static void main(String[] args) {
//        JsonParser jsonParser = new JsonParser();
//        JsonObject jsonObject = new JsonObject();
//        JsonElement jsonElement = jsonParser.parse("jack");
////        System.out.println(jsonElement);
//        jsonObject.add("name", jsonElement);
//        System.out.println(jsonObject);
//        String s = jsonObject.toString();

        AdministratorDao b = new AdministratorDaoImpl();
        StudentDao s = new StudentDaoImpl();
        //插入管理员
        int ret = b.insertAdministrator("1", "939543598@qq.com", "123456", "我嫩爹");

        //通过id返回信息
        Administrator admin = b.QueryAdministratorByNumber("1");
        System.out.println(admin);

        ret = b.updateAdministrator("1", "123456789@qq.com", "123456","我恁跌");

        s.QueryStudentByStudentNumber("")



    }
}
