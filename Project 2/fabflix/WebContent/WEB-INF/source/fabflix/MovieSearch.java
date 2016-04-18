package fabflix;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;


public class MovieSearch
{
	private Connection connection;

	public void establish_connection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection("jdbc:mysql:///moviedb", "root", "!Mussy1243**");
	}

	public void close_connection() throws Exception
	{
		connection.close();
	}

	public void add_constraint(ArrayList<String> queries, String column, String cond_operator, int param_count)
	{
		for(int i = 0; i < queries.size(); ++i)
		{
			if(param_count > 0)
				queries.set(i, (queries.get(i) + " " + cond_operator +" "));
			queries.set(i, (queries.get(i) + column + " = ?"));
			//System.out.println("query is " + queries.get(i));
		}
	}

	public void add_constraint(ArrayList<String> queries, String column, String cond_operator, int param_count, String start_wrap, String end_wrap)
	{
		for(int i = 0; i < queries.size(); ++i)
		{
			if(param_count > 0)
				queries.set(i, (queries.get(i) + " " + cond_operator +" " ));
			queries.set(i, (queries.get(i)+ start_wrap + column + " = ?" + end_wrap));
			//System.out.println("query is " + queries.get(i));
		}
	}

	public void arrange_mapping(LinkedHashMap<String,ArrayList<String>> ret_movies, ResultSet rs) throws Exception
	{
		if(rs.next())
		{
			rs.beforeFirst();
			int columnCount = rs.getMetaData().getColumnCount();
			while(rs.next())
			{
				String movie_id = rs.getString(1);
				if(ret_movies.containsKey(movie_id))
				{
					ArrayList<String> temp = ret_movies.get(movie_id);
					for(int i = 1; i < columnCount; ++i)
					{
					String val = rs.getString(i+1);
					if(!temp.contains(val))
						temp.add(rs.getString(i+1));
					}
				}
				else
				{
					ArrayList<String> toadd = new ArrayList<String>();
					for(int i = 1; i < columnCount; ++i)
						toadd.add(rs.getString(i+1));

					ret_movies.put(rs.getString(1), toadd);
				}	
			}
		}
	}

public void build_queries(ArrayList<String> queries, ArrayList<String> param_tracker, String title, String year, String director, String first_name, String last_name,
		 ArrayList<String> results_per_page, ArrayList<String> sort_rules)
{
	if(!"".equals(title))
	{
		add_constraint(queries, "title", "AND", param_tracker.size());
		param_tracker.add(title);
	} 
	if(!"".equals(year))
	{
		add_constraint(queries, "year", "AND", param_tracker.size());
		param_tracker.add(year);
	}
	if(!"".equals(director))
	{
		add_constraint(queries, "director", "AND", param_tracker.size());
		param_tracker.add(director);
	}
	if(!"".equals(first_name))
	{
		if(!"".equals(last_name))
		{
			add_constraint(queries, "first_name", "AND", param_tracker.size());
			param_tracker.add(first_name);
			add_constraint(queries, "last_name", "AND", param_tracker.size());
			param_tracker.add(last_name);
		}
		else
		{
			add_constraint(queries, "first_name", "AND", param_tracker.size(), "(", "");
			param_tracker.add(first_name);
			add_constraint(queries, "last_name", "OR", param_tracker.size(), "", ")");
			//----------------here we need to add the first name again to be check in the last_name column as well -------
			param_tracker.add(first_name);

		}
	}
	else if(!"".equals(last_name))
	{
		add_constraint(queries, "last_name", "AND",param_tracker.size(), "(", "");
		param_tracker.add(last_name);
		add_constraint(queries, "first_name", "OR",param_tracker.size(), "", ")");
		param_tracker.add(last_name);
	}

	//add ORDER BY, LIMIT, OFFSET
	int offset = Integer.parseInt(results_per_page.get(0)) * Integer.parseInt(results_per_page.get(1));
	queries.set(0, (queries.get(0) + " ORDER BY " + sort_rules.get(0) + " " + sort_rules.get(1)));
	queries.set(0, (queries.get(0) + " LIMIT " + results_per_page.get(0) + " OFFSET " + offset));
}

	public ArrayList<LinkedHashMap<String,ArrayList<String>>> search_movies(String title, String year, String director, String first_name, String last_name,
		 ArrayList<String> results_per_page, ArrayList<String> sort_rules)
		throws Exception
	{
		establish_connection();
		ArrayList<String> param_tracker = new ArrayList<String>();
		String joins = "FROM stars INNER JOIN stars_in_movies ON stars.id = stars_in_movies.star_id "+
					"INNER JOIN movies ON movies.id = stars_in_movies.movies_id " +
                    "INNER JOIN genres_in_movies ON genres_in_movies.movies_id = movies.id "+
                    "INNER JOIN genres ON genres_in_movies.genres_id = genres.id WHERE "; 


		String movie_query = "SELECT DISTINCT movies.id, movies.title, movies.year, movies.director " + joins;
		String star_query = "SELECT movies.id, CONCAT(stars.first_name, ' ',stars.last_name) as full_name " + joins;
		String genre_query = "SELECT movies.id, genres.name " + joins;
		ArrayList<String> queries = new ArrayList<String>();
		queries.addAll(Arrays.asList(movie_query, star_query, genre_query));
		build_queries(queries, param_tracker, title, year, director, first_name, last_name, results_per_page, sort_rules);


		LinkedHashMap<String,ArrayList<String>> movie_info = new LinkedHashMap<String,ArrayList<String>>();
		LinkedHashMap<String,ArrayList<String>> star_info = new LinkedHashMap<String,ArrayList<String>>();
		LinkedHashMap<String,ArrayList<String>> genre_info = new LinkedHashMap<String,ArrayList<String>>();
		ArrayList<LinkedHashMap<String,ArrayList<String>>> all_info = new ArrayList<LinkedHashMap<String,ArrayList<String>>>();
		all_info.addAll(Arrays.asList(movie_info, star_info, genre_info));

		for(int i = 0; i < queries.size(); ++i)
		{
			PreparedStatement ps = connection.prepareStatement(queries.get(i));
			
			for(int j = 0; j < param_tracker.size(); ++j)
				ps.setObject(j+1, param_tracker.get(j));

			ResultSet rs = ps.executeQuery();
			arrange_mapping(all_info.get(i), rs);
			
		}
		close_connection();
		return all_info;
	} 
}