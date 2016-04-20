package fabflix;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


@SuppressWarnings("serial")
public class CartServlet extends HttpServlet 
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException
	  {
		  HttpSession sess = request.getSession();
		  String movie_id = (String)request.getParameter("movie_id");
		  String price = (String)request.getParameter("price");
		  
		  try 
		  {
			add_to_cart(sess, movie_id, price);
		  } 
		  catch (Exception e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  response.sendRedirect("/cartHome");
	  }
	  
	  public void add_to_cart(HttpSession sess, String movie_id, String price) throws Exception
	  {
		  dbFunctions  cart_actions = new dbFunctions();
		  cart_actions.make_connection("moviedb", "root", "root");
		  Cart users_cart = (Cart)sess.getAttribute("shopping_cart");
		  if(users_cart == null)
		  {
			  users_cart = new Cart();
		  }
		  Movie new_item = cart_actions.returnMovieFromID(Integer.parseInt(movie_id));
		  users_cart.add_cart_item(new_item, Integer.parseInt(price));
		  sess.setAttribute("shopping_cart"), users_cart);
	  }


}