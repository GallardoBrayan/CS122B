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
	  	long startTime = System.nanoTime();
    	HttpSession sess = request.getSession();
    	SearchParameters curSearch = (SearchParameters) sess.getAttribute("curSearch");

    	if(request.getParameter("submit") != null || request.getParameter("browse") != null)
    	{
    		sess.setAttribute("movie_list", null);
    		curSearch = new SearchParameters();
    		curSearch.setTitle(request.getParameter("title"));
    		curSearch.setYear(request.getParameter("year"));
    		curSearch.setDirector(request.getParameter("director"));
    		curSearch.setFirstName(request.getParameter("first_name"));
    		curSearch.setLastName(request.getParameter("last_name"));
    	}
    	if(request.getParameter("browse") != null )
    	{
    		sess.setAttribute("movie_list", null);
    		curSearch.setFromBrowse(true);
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

    	if(request.getParameter("genre") != null )
		{
			curSearch.setByTitle(false);
			curSearch.setGenre(request.getParameter("genre"));
	
		}
    	if(request.getParameter("jmeter") != null)
    		curSearch.setMoviePerPage("10");
    	
    	//If the user just changed a sorting, we can use the search critera that is already stored in session.

  		LinkedHashMap<Integer,Movie> movie_list = new LinkedHashMap<Integer,Movie>();
  		long TJ = 0;
    	try
    	{
    		long sartJDBC = System.nanoTime();
    		dbFunctions movie_actions = new dbFunctions();
    		if(request.getParameter("jmeter") != null && request.getParameter("jmeter").contains("NOPOOL"))
    			movie_actions.make_connection_without_pooling("jdbc:mysql://localhost:3306/moviedb", "root", "root");
    		else
    			movie_actions.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
    		if( curSearch.getFromBrowse() && ! curSearch.getByTitle())
	    	{
    			movie_list = movie_actions.getMoviesByGenre(curSearch);
	    	}
	    	else
	    	{
	    		if(request.getParameter("jmeter") != null && request.getParameter("jmeter").contains("NOPS"))
	    			movie_list = movie_actions.search_movies_without_PS(curSearch);
	    		else
	    			movie_list = movie_actions.search_movies(curSearch);
	    	}
    		movie_actions.close();
    		TJ = System.nanoTime() - sartJDBC;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception Occured");
    		System.out.println(e.getMessage());
    	}
    	
    	sess.setAttribute("curSearch", curSearch);
    	sess.setAttribute("movie_list", movie_list);
    	response.sendRedirect("movie_list");
    	long TS = System.nanoTime() - startTime;
    	
    	String fileName = "jMeterlog ";
    	if(request.getParameter("jmeter") != null)
    		fileName += request.getParameter("jmeter") + "  :\t";
    	System.out.println(fileName + TJ + "," + TS );
    }
}