package fabflix;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


@SuppressWarnings("serial")
public class run_search extends HttpServlet 
{
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {
    	HttpSession sess = request.getSession();
    	SearchParameters curSearch = (SearchParameters) sess.getAttribute("curSearch");
    	if(curSearch == null)
    	{
    		curSearch = new SearchParameters();
    		curSearch.setTitle(request.getParameter("title"));
    		curSearch.setYear(request.getParameter("year"));
    		curSearch.setDirector(request.getParameter("director"));
    		curSearch.setFirstName(request.getParameter("first_name"));
    		curSearch.setLastName(request.getParameter("last_name"));
    	}
    	if(sess.getAttribute("movie_list") == null)
    	{
    		curSearch.setSortType("title");
    		curSearch.setMoviePerPage("10");
    		curSearch.setCurrentPage("0");
    		curSearch.setSortAccending(true);
    	}
    	else if(request.getParameter("sort_type") != null)
    	{
    		if(curSearch.getSortType().equals((String)request.getParameter("sort_type")))
    		{
    			curSearch.setSortAccending(!curSearch.getSortAccending());
    		}else
    		{
	    		curSearch.setSortType((String)request.getParameter("sort_type"));
	    		curSearch.setSortAccending(true);
    		}
    	}
    	else if(request.getParameter("movies_per_page") != null)
    	{
    		curSearch.setMoviePerPage((String)request.getParameter("movies_per_page"));
    	}
    	else if(request.getParameter("page_number") != null)
    	{
    		curSearch.setCurrentPage((String)request.getParameter("page_number"));
    	}

    	//If the user just changed a sorting, we can use the search critera that is already stored in session.
	
  		dbFunctions movie_actions = new dbFunctions();
  		
  		LinkedHashMap<Integer,Movie> movie_list = new LinkedHashMap<Integer,Movie>();
    	try
    	{
    		movie_actions.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
    		movie_list = movie_actions.search_movies(curSearch);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception Occured");
    		System.out.println(e.getMessage());
    	}
    	System.out.println("updating session");
    	sess.setAttribute("curSearch", curSearch);

    	sess.setAttribute("movie_list", movie_list);
    	movie_actions.close();
    	response.sendRedirect("movie_list");
    }
}