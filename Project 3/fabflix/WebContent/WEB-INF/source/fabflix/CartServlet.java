package fabflix;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class CartServlet extends HttpServlet 
{
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException
	  {
		  HttpSession sess = request.getSession();
		  
		  if(sess.getAttribute("userToken") == null)
			{
				response.sendRedirect("login.jsp");
				return;
			}
		  if(request.getParameter("cartOp") != null)
		  {
			  process_cart_op(sess, request);
		  }
		  else
		  {
			  String movie_id = (String)request.getParameter("movie_id");
			  process_item(sess, movie_id);
		  }
		  response.sendRedirect("cart.jsp");
	  }
	  
	  private void process_item(HttpSession sess, String movie_id)
	  {
		  try 
		  {
			add_to_cart(sess, movie_id);
		  } 
		  catch (Exception e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	  }
	  
	  public void process_cart_op(HttpSession sess, HttpServletRequest req)
	  {
		  String op = req.getParameter("cartOp");
		  switch(op)
		  {
		  case "empty_cart":
			  sess.setAttribute("shopping_cart", new Cart());
			  break;
		  case "Update":
			  try{
				  int qty = Integer.parseInt(req.getParameter("qty"));
				  Integer movie_id = Integer.parseInt(req.getParameter("movie_id"));
				  Cart to_edit = (Cart)sess.getAttribute("shopping_cart");
				  to_edit.setQty(movie_id, qty);
				  sess.setAttribute("shopping_cart", to_edit);
			  }catch (Exception e)
			  {
				  //Had to throw this in so that way if user enters to large of a number
				  //system won't crash
			  }

			  break;
		  case "Remove":
			  Integer moveid = Integer.parseInt(req.getParameter("movie_id"));
			  Cart edit = (Cart)sess.getAttribute("shopping_cart");
			  edit.remove_item(moveid);
			  break;
		  }
	  }

	public void add_to_cart(HttpSession sess, String movie_id) throws Exception
	  {
		  dbFunctions cart_actions = new dbFunctions();
		  cart_actions.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
		  Cart users_cart = (Cart)sess.getAttribute("shopping_cart");
		  if(users_cart == null)
		  {
			  users_cart = new Cart();
		  }
		  Movie new_item = cart_actions.returnMovieFromID(Integer.parseInt(movie_id));
		  users_cart.add_cart_item(new_item);
		  sess.setAttribute("shopping_cart", users_cart);
		  cart_actions.close();
	  }


}