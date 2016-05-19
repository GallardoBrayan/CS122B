package fabflix;

import java.awt.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

/**
 * Servlet implementation class PopupWindow
 */

public class PopupWindow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopupWindow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		LinkedHashMap<Integer, Movie> movie_list = (LinkedHashMap<Integer, Movie>) request.getSession().getAttribute("movie_list");
		
		Integer movie_id = Integer.parseInt((String)request.getParameter("movie_id"));
	    Movie curMovie = movie_list.get(movie_id);
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    String outputString = ""; 

	    outputString =  "<table class=\"pop_talbe\">\n\t<tr class=\"movie_pop_up_rc\"><img height=\"42\" width=\"42\" src=\"resources/letters/" +  curMovie.getTitle().toUpperCase().charAt(0) + ".jpg\"></tr>\n";
	    
	    if(curMovie.getBanner_url() != null )
	    {
		    try{
		      Image image = new ImageIcon(new URL(curMovie.getBanner_url())).getImage();
		      if(image.getWidth(null) != -1)
		      {
		    	  outputString =  "<table>\n\t<tr class=\"movie_pop_up_rc\"><img height=\"42\" width=\"42\" src=\"" + curMovie.getBanner_url() + "\"></tr>\n";
		      }
		    }catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
	    }

	    outputString += "<tr class=\"movie_pop_up_rc\"><td class=\"movie_pop_up_rc\"> Stars:</td><td class=\"movie_pop_up_rc\">"; 
	    int i = 1;
	    for (String star : curMovie.getStars()) {
	    	outputString += star.trim() ;

			if ( i++ != curMovie.getStars().size()) 
			{
				outputString += ", ";
			}
	    }
	    outputString += "</td>\n\t</tr>\n\t<tr class=\"movie_pop_up_rc\">\n\t\t<td class=\"movie_pop_up_rc\"> Release Year:</td>\n\t\t<td class=\"movie_pop_up_rc\">" + curMovie.getYear() + "</td>\n\t</tr></table>";
	    out.println(outputString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
