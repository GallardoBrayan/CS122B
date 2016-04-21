package fabflix;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession sess = request.getSession();
		User user_info = (User)sess.getAttribute("userToken");
		if(user_info == null)
		{
			response.sendRedirect("login.jsp");
			return;
		}

		boolean sale_success = false;
		
		CustomerCheckout info = new CustomerCheckout(request.getParameter("first_name"), request.getParameter("last_name"),
					user_info.getId(),request.getParameter("cc"), request.getParameter("exp"));
		
		dbFunctions sale_action = new dbFunctions();

		try 
		{
			sale_action.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
			sale_success = sale_action.process_sale((Cart)sess.getAttribute("shopping_cart"), info);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if(sale_success)
		{
			sess.setAttribute("sale_success_message", "Your sale has been processed successfully");
			sess.setAttribute("shopping_cart", new Cart());
		}
		else
		{
			sess.setAttribute("sale_success_message", "Your sale has not been processed.  Information entered is incorrect");
		}
		
		response.sendRedirect("confirmation.jsp");
		sale_action.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
