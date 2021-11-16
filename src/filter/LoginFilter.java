package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 用于过滤没有登录的用户
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //发向“/pages/login.html”的request不用检查
        String url = req.getRequestURI();
        if(!url.endsWith("login.html")) {
            String userNumber = (String) req.getSession().getAttribute("userNumber");
            //userNumber为空说明没有登录
            if(userNumber == null){
                resp.sendRedirect("/SoftwareEngineering/pages/login.html");
            }
        }

        chain.doFilter(request, response);
    }
}
