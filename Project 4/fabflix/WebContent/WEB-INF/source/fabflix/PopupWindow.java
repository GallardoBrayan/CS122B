package fabflix;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	    
	    
	    String outputString =  "<table>\n\t<tr><img height=\"42\" width=\"42\" src=\"" + curMovie.getBanner_url() + "\"></tr>\n";

	    outputString += "\t<tr>\n\t\t<td> Stars:</td>\n\t\t<td>"; 
	    int i = 1;
	    for (String star : curMovie.getStars()) {
	    	outputString += "<a href=\"getStar?movieid=" + movie_id + "&amp;star_name=" + star +"\">"  + star +"</a>";

			if ( ++i != curMovie.getStars().size()) 
			{
				outputString += ", ";
			}
	    }
	    outputString += "</td>\n\t</tr>\n\t<tr>\n\t\t<td> Release Year:</td>\n\t\t<td>" + curMovie.getYear() + "</td>\n\t</tr></table>";
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
