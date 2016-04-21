package fabflix;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginHandler
 */

public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHandler() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{	
			
			dbFunctions dbConnection = new dbFunctions();
			
			dbConnection.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
			
			User userToLogin = (User) request.getSession().getAttribute("userToken");
			
			if (request.getParameter("username") != null) 
			{
				userToLogin = dbConnection.loginToFabFlix(request.getParameter("username"),
						request.getParameter("pass"));
				
				if (userToLogin == null) {
					request.getSession().setAttribute("error", "loginError");
					response.sendRedirect("Login");
					 
				}else
				{
					request.getSession().setAttribute("userToken", userToLogin);
					response.sendRedirect("MainPage");
				}
			}
			if (request.getParameter("logout") != null)
			{
				request.getSession().setAttribute("userToken", null);
				response.sendRedirect("Login");
			}
			dbConnection.close();
			
		}catch (Exception ex)
		{
			ex.printStackTrace();
			request.getSession().setAttribute("error", "loginError");
			request.getSession().setAttribute("userToken", null);
			response.sendRedirect("Login");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
