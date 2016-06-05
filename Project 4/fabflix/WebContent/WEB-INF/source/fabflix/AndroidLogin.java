package fabflix;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.*;

@SuppressWarnings("serial")
public class AndroidLogin extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException
	  {
		JSONObject json = new JSONObject();
		dbFunctions auth_login = new dbFunctions();
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		try {
			auth_login.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
			boolean result = auth_login.auth_creditionals(email, pass);
			if(result == true)
			{
				json.put("auth", true);
			}
			else
			{
				json.put("auth", false);
			}
		} catch (Exception e) {
			
			json.put("auth", "error");
			e.printStackTrace();
		}


		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		
		
	  }
	
}