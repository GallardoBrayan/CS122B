package fabflix;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// Implements Filter class
public class AuthFilter implements Filter  {
   public void  init(FilterConfig config) 
                         throws ServletException{
      // Get init parameter 
      String testParam = config.getInitParameter("test-param"); 

      //Print the init parameter 
      System.out.println("Test Param: " + testParam); 
   }
   public void  doFilter(ServletRequest req,  ServletResponse res,FilterChain chain) 
                 throws java.io.IOException, ServletException 
    {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
      if(null == session.getAttribute("user_name"))
      {
        String path = request.getRequestURI();
        if(path.startsWith("/test/login_V.jsp"))
        {
            chain.doFilter(request,response);
        }
        else
        {
           response.sendRedirect("/test/login_V.jsp");
        }
      }

    }
   public void destroy( ){
      /* Called before the Filter instance is removed 
      from service by the web container*/
   }
}