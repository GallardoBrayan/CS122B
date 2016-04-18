package fabflix;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;


public class run_search extends HttpServlet {

	public boolean resolve_sort(HttpSession sess, String title_sort, String year_sort)
	{
		boolean did_change = false;
		ArrayList<String> current_sort = (ArrayList<String>)sess.getAttribute("sort");

		if(current_sort == null)
		{
			current_sort = new ArrayList<String>();
			current_sort.add(0, "title");
			current_sort.add(1, "ASC");
		}
		else if(title_sort != null)
		{
			did_change = true;
			if(current_sort.get(0) == "title")
			{
				String dir = current_sort.get(1) == "ASC" ? "DESC": "ASC";
				current_sort.set(1, dir);
			}
			else
			{
				current_sort.set(0, "title");
				current_sort.set(1, "ASC");
			}
		}
		else if(year_sort != null)
		{
			did_change = true;
			if(current_sort.get(0) == "year")
			{
				String dir = current_sort.get(1) == "ASC" ? "DESC": "ASC";
				current_sort.set(1, dir);
			}
			else
			{
				current_sort.set(0, "year");
				current_sort.set(1, "ASC");
			}
		}
		sess.setAttribute("sort", current_sort);
		return did_change;	
	}

	public void	update_search_criteria_in_session(HttpSession sess, String title, String year, String director, String first_name,
		String last_name)
	{
		sess.setAttribute("title", title);
		sess.setAttribute("year", year);
		sess.setAttribute("director", director);
		sess.setAttribute("first_name", first_name);
		sess.setAttribute("last_name", last_name);
	}

	public boolean resolve_res_per_page(HttpSession sess, String movies_per_page, String page_number)
	{
		boolean did_change = false;
		ArrayList<String> current = (ArrayList<String>)sess.getAttribute("display_rules");

		if(current == null)
		{
			//System.out.println("current is null");
			current = new ArrayList<String>();
			current.add("10");
			current.add("0");
		}
		if(movies_per_page != null)
		{
			did_change = true;
			current.set(0, movies_per_page);
			current.set(1, "0");
		}
		else if(page_number != null)
		{
			did_change = true;
			current.set(1, page_number);
		}
		sess.setAttribute("display_rules", current);
		return did_change;
	}


  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
    {
    	HttpSession sess = request.getSession();
    	String title_sort = request.getParameter("title_sort");
    	String year_sort = request.getParameter("year_sort");
    	String movies_per_page = request.getParameter("movies_per_page");
    	String page_number = request.getParameter("page_number");

    	//If the user just changed a sorting, we can use the search critera that is already stored in session.
    	boolean sort_changed_by_user = resolve_sort(sess, title_sort, year_sort);
    	boolean results_per_page_change_by_user = resolve_res_per_page(sess, movies_per_page, page_number);

 //   	System.out.println("Scbu: " + sort_changed_by_user + ", results_per_page_change_by_user: " + results_per_page_change_by_user);
    	String title = (sort_changed_by_user || results_per_page_change_by_user) ? (String)sess.getAttribute("title") : request.getParameter("title");
    	String year = (sort_changed_by_user || results_per_page_change_by_user) ? (String)sess.getAttribute("year") : request.getParameter("year");
    	String director = (sort_changed_by_user || results_per_page_change_by_user) ? (String)sess.getAttribute("director") : request.getParameter("director");
    	String first_name = (sort_changed_by_user || results_per_page_change_by_user) ? (String)sess.getAttribute("first_name") : request.getParameter("first_name");
    	String last_name = (sort_changed_by_user || results_per_page_change_by_user) ? (String)sess.getAttribute("last_name") : request.getParameter("last_name");

    	MovieSearch movie_actions = new MovieSearch();
    	ArrayList<LinkedHashMap<String,ArrayList<String>>> selected_movies = new ArrayList<LinkedHashMap<String,ArrayList<String>>>();
    	ArrayList<String> sort_rules = (ArrayList<String>)sess.getAttribute("sort");

    	ArrayList<String> display_rules = (ArrayList<String>)sess.getAttribute("display_rules");
    	try
    	{
    		selected_movies = movie_actions.search_movies(title, year, director, first_name, last_name, display_rules, sort_rules);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception Occured");
    		System.out.println(e.getMessage());
    	}
    	System.out.println("updating session");
    	if(!sort_changed_by_user && !results_per_page_change_by_user)
    		update_search_criteria_in_session(sess, title, year, director, first_name, last_name);

    	sess.setAttribute("movies", selected_movies);
    	response.sendRedirect("/test/movie_list.jsp");
    }
}