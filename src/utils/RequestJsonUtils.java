package utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * RequestJsonUtils类的描述：
 * 读取axios传过来的json数据
 * @author 黄金坤（HJK）
 * @since 2021/11/2  16:30
 */

public class RequestJsonUtils {
   public static String getJson(HttpServletRequest request)throws ServletException, IOException{
       StringBuilder sb = new StringBuilder();
       try(BufferedReader reader = request.getReader()){
           char[] buff = new char[1024];
           int len;
           while((len = reader.read(buff)) != -1){
               sb.append(buff,0,len);
           }
       }catch(IOException e){
           e.printStackTrace();
       }
       return sb.toString();
   }
}
