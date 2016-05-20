package fabflix;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import java.util.*;


@SuppressWarnings("serial")
public class AndroidMovieSearch extends HttpServlet 
{
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {
	  dbFunctions movie_search = new dbFunctions();
	  try {
		movie_search.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
		SearchParameters curSearch = new SearchParameters();

		JSONObject json = new JSONObject();
		ArrayList<Movie> search_res = movie_search.getmovieByTilte(0, 1000, request.getParameter("title"));
		Integer counter= 0;
		String key = null;
		for(Movie mov: search_res)
		{
			
			key = "movie" + counter.toString();
			json.put(key, mov.getTitle());
			++counter;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }
}