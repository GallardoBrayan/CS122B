package fabflix;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class dbFunctions 
{
	private Connection connection;

	/**
	 * Establishes connection to database
	 * 
	 * @param path
	 *            - path to db
	 * @param user_name
	 *            - mysql username
	 * @param pass
	 *            - mysql password
	 */
	public void make_connection(String path, String user_name, String pass) throws Exception 
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(path, user_name, pass);
	}

	public Map<String,Map<String,String>> get_metadata() throws Exception 
	{
		DatabaseMetaData dbmd = connection.getMetaData();
		Map<String,Map<String,String>> output = new HashMap<String,Map<String,String>>();
		
		ResultSet tables = dbmd.getTables(null,null,null,null);

		while(tables.next())
		{
			String table_name = tables.getString(3);
			HashMap<String, String> currentTable = new HashMap<String, String>();
			output.put(table_name, currentTable);
			ResultSet cols = dbmd.getColumns(null,null,table_name, null);

			while(cols.next())
			{
				currentTable.put(cols.getString("COLUMN_NAME"),cols.getString("TYPE_NAME"));
			}
			System.out.println("");
		}
		tables.close();
		return output;
	}

	public double getMoviePrice(Integer movie_id)
	{
		return 15.99;
	}
	
	public void close() 
	{
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Warning:The database connection was not closed properly.");
		}
	}

	public void insert_sale(Cart cart, CustomerCheckout info) throws SQLException
	{
		String insert_stmt = "INSERT INTO sales (customer_id, movies_id, sale_date) VALUES (?,?,?)";
		java.util.Date date = new java.util.Date();
		PreparedStatement ps = connection.prepareStatement(insert_stmt);
		
		ps.setObject(1, info.getCustomer_id());
		ps.setObject(3, new SimpleDateFormat("yyyy-MM-dd").format(date));
		HashMap<Integer, CartItem> basket = cart.getBasket();
		for(Integer movie_id: basket.keySet())
		{
			ps.setObject(2, movie_id);
			ps.executeUpdate();
		}

	}
	
	public boolean process_sale(Cart cart, CustomerCheckout info) throws SQLException
	{
		boolean success = false;
		String stmt = "SELECT COUNT(id) FROM creditcards WHERE id = ? AND first_name = ? "
				+ "AND last_name = ? AND expiration = ?";
		PreparedStatement ps = connection.prepareStatement(stmt);
		ps.setObject(1, info.getCc());
		ps.setObject(2, info.getFirst_name());
		ps.setObject(3, info.getLast_name());
		ps.setObject(4, info.getExp_date());
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		if(rs.getInt(1) > 0) //Grab first column which is the count
		{
			success = true;
			insert_sale(cart, info);
		}
		return success;
		
	}
	
	public Movie returnMovieFromID(Integer movie_id) throws SQLException 
	{
		Movie to_return = null;
		String stmt = "SELECT * from movies WHERE id = " + movie_id.toString();
		PreparedStatement ps = connection.prepareStatement(stmt);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			to_return  = new Movie(movie_id, rs.getString("title"), rs.getInt("year"), rs.getString("director"),
						rs.getString("banner_url"), rs.getString("trailer"),  getGenreFromMovieId(movie_id),
						getStarFromMovieId(movie_id));
		}
		return to_return;	
	}
	
	public int countMovieByTilte(String letterOfTitle) throws SQLException {
		String statementString = "SELECT COUNT(*) FROM movies";
		if (!"".equals(letterOfTitle)) {
			statementString += " WHERE  title LIKE \'" + letterOfTitle + "%\' ";
		}
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		int output = 0;
		if (results.next()) {
			output = results.getInt(1);
		}
		results.close();
		statement.close();
		return output;
	}

	public Star getStarFromMovieIdAndName(Integer movie_id, String first_name, String last_name) throws SQLException
	{
		String star_id_stmt = "SELECT stars.id, stars.dob, stars.photo_url FROM stars INNER JOIN stars_in_movies ON stars.id = stars_in_movies.star_id " +
				"INNER JOIN movies ON movies.id = stars_in_movies.movies_id WHERE movies.id = ? AND stars.first_name = ? AND stars.last_name = ? LIMIT 1";
		String movies_info_stmt = "SELECT movies.id, movies.title FROM stars INNER JOIN stars_in_movies ON stars.id = stars_in_movies.star_id "+
			   "INNER JOIN movies ON movies.id = stars_in_movies.movies_id WHERE stars.id = ?";
		
		PreparedStatement star_ps = connection.prepareStatement(star_id_stmt);
		star_ps.setObject(1, movie_id);
		star_ps.setObject(2, first_name);
		star_ps.setObject(3, last_name);
		
		ResultSet star_rs = star_ps.executeQuery(); 
		Integer star_id = -1;
		Star star = null;
		if(star_rs.next())
		{
			star_id = star_rs.getInt("id");
		
			PreparedStatement movie_ps = connection.prepareStatement(movies_info_stmt);
			movie_ps.setObject(1, star_id);
			ResultSet movie_rs = movie_ps.executeQuery();
			HashMap<Integer, String> movies = new HashMap<Integer, String>();
			
			while(movie_rs.next())
			{
				movies.put(movie_rs.getInt("id"), movie_rs.getString("title"));
			}
			star = new Star(star_id, first_name, last_name, star_rs.getString("dob"), 
					star_rs.getString("photo_url"), movies);
		}
		else
		{
			star = new Star(star_id, first_name, last_name, "", 
					"",  new HashMap<Integer, String>());
		}
		
		return star;
	}
	
	public LinkedHashMap<Integer, Movie> getMoviesByGenre(SearchParameters curParams) throws Exception  {
		String statementString = "SELECT * FROM movies  WHERE  id IN (SELECT movies_id FROM genres_in_movies where genres_id IN "
					+ " (SELECT id FROM genres WHERE name=\'" + curParams.getGenre() + "\'))";
		int offset = Integer.parseInt(curParams.getMoviePerPage()) * Integer.parseInt(curParams.getCurrentPage());
		statementString += (" ORDER BY " + curParams.getSortType() + " " + (curParams.getSortAccending() ? "ASC" : "DESC"));
		statementString += (" LIMIT " + curParams.getMoviePerPage() + " OFFSET " + offset);
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		LinkedHashMap<Integer, Movie> output = new LinkedHashMap<Integer, Movie>();

		populate_list(output, results);
		results.close();
		statement.close();
		return output;
	}

	public ArrayList<String> getGenreList() throws SQLException {
		String statementString = "SELECT name FROM genres;";

		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		ArrayList<String> output = new ArrayList<String>();
		while (results.next()) {
			output.add(results.getString("name"));
		}

		results.close();
		statement.close();
		return output;
	}

	public int countMovieByGenre(String Genre) throws SQLException {
		String statementString = "SELECT COUNT(*) FROM movies";
		if (!"".equals(Genre)) {
			statementString += " WHERE  id IN (SELECT movies_id FROM genres_in_movies where genres_id IN "
					+ " (SELECT id FROM genres WHERE name=\'" + Genre + "\'))";
		}
		statementString += " ORDER BY title ";
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		int output = 0;
		if (results.next()) {
			output = results.getInt(1);
		}
		results.close();
		statement.close();
		return output;
	}

	public User loginToFabFlix(String userName, String password) throws SQLException {
		User logged_in_user = null;
		if (userName == null || password == null)
			return null;
		String statementString = "SELECT * FROM customers WHERE email=\'" + userName + "\' AND password=\'" + password
				+ "\';";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		if (results.next()) {
			logged_in_user = new User(results.getInt("id"), results.getString("first_name"),
					results.getString("last_name"), results.getString("address"), results.getString("email"));

		}
		results.close();
		statement.close();
		return logged_in_user;
	}
	
	public User employeeLogin(String userName, String password) throws SQLException {
		User logged_in_user = null;
		if (userName == null || password == null)
			return null;
		String statementString = "SELECT * FROM employees WHERE email=\'" + userName + "\' AND password=\'" + password
				+ "\';";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		if (results.next()) {
			logged_in_user = new User(0, results.getString("fullname"),"", "Empolyee", results.getString("email"));

		}
		results.close();
		statement.close();
		return logged_in_user;
	}

	public ArrayList<Movie> getmovieByTilte(int start, int limit, String letterOfTitle) throws SQLException {
		String statementString = "SELECT * FROM movies";
		if (!"".equals(letterOfTitle)) {
			statementString += " WHERE  title LIKE \'" + letterOfTitle + "%\' ";
		}
		statementString += " ORDER BY title ";
		if (limit > 0) {
			statementString += " LIMIT " + limit + " OFFSET " + (start > 0 ? start : 0);
		}

		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		ArrayList<Movie> output = new ArrayList<Movie>();
		while (results.next()) {
			Movie newMovie = new Movie();
			newMovie.setTitle(results.getString("title"));
			newMovie.setId(results.getInt("id"));
			newMovie.setDirector(results.getString("director"));
			newMovie.setYear(results.getInt("year"));
			newMovie.setBanner_url(results.getString("banner_url"));
			newMovie.setTrailer_url(results.getString("trailer"));
			output.add(newMovie);

		}
		results.close();
		statement.close();
		return output;
	}

	private Boolean add_constraint(StringBuilder query, String column, String cond_operator, boolean is_first) {
		if (!is_first) {
			query.append(" " + cond_operator + " ");
		}
		is_first = false;
		query.append(column + " LIKE ?");
		return is_first;
	}

	private Boolean add_constraint(StringBuilder query, String column, String cond_operator, String start_wrap, String end_wrap,
			Boolean is_first) {
		if (!is_first) {
			query.append(" " + cond_operator + " ");
		}
		is_first = false;
		query.append(start_wrap + column + " LIKE ?" + end_wrap);
		return is_first;
	}

	public HashSet<String> getGenreFromMovieId(Integer id) throws SQLException 
	{
		String query = "select name FROM genres INNER JOIN genres_in_movies ON genres.id = genres_in_movies.genres_id"
				+ 	" WHERE genres_in_movies.movies_id =	" + id;
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		HashSet<String> tempOutput = new HashSet<String>();
		while(rs.next())
		{
			tempOutput.add(rs.getString(1));
		}
		rs.close();
		ps.close();
		return tempOutput;
	}
	
	public Integer getGenreIdFromName(String name) throws SQLException
	{
		Integer id = -1;
		String query = "Select id FROM genres WHERE name= ? LIMIT 1";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setObject(1, name);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			id = rs.getInt("id");
		}
		return id;
	}
	
	public Integer insert_genre(String genre) throws SQLException
	{
		String query = "INSERT INTO genres (name) VALUES (?)";
		PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setObject(1, genre);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}
	
	public ResultSet movie_batch_insert(ArrayList<Movie> values) throws SQLException
	{
		connection.setAutoCommit(false);
		String stmt = "INSERT INTO movies (title,year,director) VALUES (?,?,?)";
		PreparedStatement ps = connection.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		for(Movie mov : values)
		{
			ps.setString(1, mov.getTitle());
			ps.setInt(2, mov.getYear());
			ps.setString(3, mov.getDirector());
			ps.addBatch();
			
		}
		ps.executeBatch();
		connection.commit();
		connection.setAutoCommit(true);
		return ps.getGeneratedKeys();
	}
	public ResultSet star_batch_insert(ArrayList<Star> values) throws SQLException
	{
		connection.setAutoCommit(false);
		String stmt = "INSERT INTO stars (first_name,last_name,dob) VALUES (?,?,?)";
		PreparedStatement ps = connection.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
		for(Star star : values)
		{
			ps.setString(1, star.getFirst_name());
			ps.setString(2, star.getLast_name());
			ps.setObject(3, star.getDob());
			ps.addBatch();
			
		}
		ps.executeBatch();
		connection.commit();
		connection.setAutoCommit(true);
		return ps.getGeneratedKeys();
	}
	
	public void starid_movieid_batch(LinkedHashMap<Integer, Integer>starid_movieid) throws SQLException 
	{
		String query = "INSERT INTO stars_in_movies (star_id, movies_id) VALUES (?,?)";
		PreparedStatement ps = connection.prepareStatement(query);
		
		for(Map.Entry<Integer, Integer> c : starid_movieid.entrySet())
		{
			ps.setInt(1, c.getKey());
			ps.setInt(2, c.getValue());
			ps.addBatch();
		}
		ps.executeBatch();

		
	}
	
	public void gim_batch_insert(String final_genres_in_movies_query) throws SQLException
	{
		connection.setAutoCommit(false);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(final_genres_in_movies_query);
		connection.commit();
		connection.setAutoCommit(true);
		
	}
	
	public HashSet<String> getStarFromMovieId(Integer id) throws SQLException 
	{
		String query = "select CONCAT(first_name, ' ', last_name) as name FROM stars INNER JOIN stars_in_movies ON stars.id = stars_in_movies.star_id"
				+ 	" WHERE stars_in_movies.movies_id =	" + id;
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		HashSet<String> tempOutput = new HashSet<String>();
		while(rs.next())
		{
			tempOutput.add(rs.getString(1));
		}
		rs.close();
		ps.close();
		return tempOutput;
	}
	
	private void populate_list(LinkedHashMap<Integer, Movie> ret_movies, ResultSet rs) throws Exception {
		while (rs.next()) {
			Integer movie_id = rs.getInt("id");

			Movie cMovie = new Movie(movie_id, rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("banner_url"), rs.getString("trailer"), getGenreFromMovieId(movie_id),
					getStarFromMovieId(movie_id));

			ret_movies.put(movie_id, cMovie);
		}
	}

	private void build_query(StringBuilder query, SearchParameters curParams) {
		Boolean is_first = true;
		if (!"".equals(curParams.getTitle())) {
			is_first = add_constraint(query, "title", "AND", is_first);
		}
		if (!"".equals(curParams.getYear())) {
			is_first = add_constraint(query, "year", "AND", is_first);
		}
		if (!"".equals(curParams.getDirector())) {
			is_first = add_constraint(query, "director", "AND", is_first);

		}
		if (!"".equals(curParams.getFirstName())) {
			if (!"".equals(curParams.getLastName())) {
				is_first = add_constraint(query, "first_name", "AND", is_first);

				is_first = add_constraint(query, "last_name", "AND", is_first);
				;
			} else {
				is_first = add_constraint(query, "first_name", "AND", "(", "", is_first);
				is_first = add_constraint(query, "last_name", "OR", "", ")", is_first);
			}
		} else if (!"".equals(curParams.getLastName())) {
			is_first = add_constraint(query, "last_name", "AND", "(", "", is_first);
			is_first = add_constraint(query, "first_name", "OR", "", ")", is_first);
		}

		// add ORDER BY, LIMIT, OFFSET
		int offset = Integer.parseInt(curParams.getMoviePerPage()) * Integer.parseInt(curParams.getCurrentPage());
		query.append(" ORDER BY " + curParams.getSortType() + " " + (curParams.getSortAccending() ? "ASC" : "DESC"));
		query.append(" LIMIT " + curParams.getMoviePerPage() + " OFFSET " + offset);
	}

	public LinkedHashMap<Integer, Movie> search_movies(SearchParameters curSearch) throws Exception {
		StringBuilder query = new StringBuilder("SELECT DISTINCT movies.id,title,year,director,banner_url,trailer FROM stars INNER JOIN stars_in_movies ON stars.id = stars_in_movies.star_id "
				+ "LEFT OUTER JOIN movies ON movies.id = stars_in_movies.movies_id "
				+ "LEFT OUTER JOIN genres_in_movies ON genres_in_movies.movies_id = movies.id WHERE ");

		build_query(query, curSearch);

		LinkedHashMap<Integer, Movie> movie_list = new LinkedHashMap<Integer, Movie>();

		PreparedStatement ps = connection.prepareStatement(query.toString());
		add_ps_parameters(ps, curSearch);
		ResultSet rs = ps.executeQuery();
		populate_list(movie_list, rs);
		rs.close();
		ps.close();
		return movie_list;
	}

	private void add_ps_parameters(PreparedStatement ps, SearchParameters curParams) throws SQLException {
		int count = 1;
		if (!"".equals(curParams.getTitle())) {
			ps.setObject(count, (curParams.getFromBrowse() ? "" : "%") + curParams.getTitle() +  "%");
			++count;
		}
		if (!"".equals(curParams.getYear())) {
			ps.setObject(count, "%" + curParams.getYear()+ "%");
			++count;
		}
		if (!"".equals(curParams.getDirector())) {
			ps.setObject(count,"%" + curParams.getDirector()+ "%");
			++count;

		}
		if (!"".equals(curParams.getFirstName())) {
			if (!"".equals(curParams.getLastName())) {
				ps.setObject(count, "%" +curParams.getFirstName()+ "%");
				++count;
				ps.setObject(count,"%" + curParams.getLastName()+ "%");
				++count;
			} else {
				ps.setObject(count,"%" + curParams.getFirstName()+ "%");
				++count;
				ps.setObject(count, "%" +curParams.getFirstName()+ "%");
				++count;
			}
		} else if (!"".equals(curParams.getLastName())) {
			ps.setObject(count, "%" +curParams.getLastName()+ "%");
			++count;
			ps.setObject(count,"%" + curParams.getLastName()+ "%");
			++count;
		}
	}
	
	public Boolean insertStar(Star newStar) throws SQLException
	{
		PreparedStatement update = connection.prepareStatement("INSERT INTO stars (first_name, last_name, dob, photo_url) VALUES (?,?,?,?)");
		update.setObject(1,newStar.getFirst_name().trim());
		update.setObject(2, newStar.getLast_name().trim());
		update.setObject(3, newStar.getDob().trim());
		update.setObject(4, newStar.getPhotoUrl().trim());
		
		int results = 0;

		try{
			results  = update.executeUpdate();
		}
		catch(Exception ex){ 
		
			return false;
		}
		update.close();
		return (results > 0 ? true : false);
	}
	
	public String addMovieViaStoredProceduce(Movie newMovie, Star starInMovie, String genre) throws SQLException
	{
		CallableStatement cs = connection.prepareCall("{call add_movie(?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString("title", newMovie.getTitle().trim());
		cs.setInt("year", newMovie.getYear());
		cs.setString("director",newMovie.getDirector().trim());
		cs.setString("banner_url", newMovie.getBanner_url().trim());
		cs.setString("trailer", newMovie.getTrailer_url().trim());
		cs.setString("star_first_name",starInMovie.getFirst_name().trim());
		cs.setString("star_last_name",starInMovie.getLast_name().trim());
		cs.setString("star_dob",starInMovie.getDob().trim());
		cs.setString("star_photo_url",starInMovie.getPhotoUrl().trim());
		cs.setString("genre",genre.trim());
		cs.registerOutParameter(11, Types.VARCHAR);
		cs.execute();
		String out = "";
		out = cs.getString(11);
		return out;
		
	}
	
	public List<String> getTtiles(String search) throws SQLException
	{
		if(search == null || "".equals(search))
			return new ArrayList<String>();
		
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(search.split(" ")));
		String query = "SELECT DISTINCT title FROM movies WHERE ";
		
		int i = tokens.size();
		while(i --> 0)
		{
			if(i > 0)
				query += "title LIKE ? AND ";
			else
				query += "title LIKE ? LIMIT 10;";
		}
		PreparedStatement ps = connection.prepareStatement( query );
		
		while( ++i < tokens.size())
		{
			ps.setString(i+1, "%" + tokens.get(i) + "%");
		}
		
		ResultSet rs = ps.executeQuery();
		List<String> output = new ArrayList<String>();
		while(rs.next())
		{
			output.add(rs.getString(1));
		}
		if(rs != null)
		{
			rs.close();
		}
		if(ps!= null)
		{
			ps.close();
		}
		
		return output;
		
	}
}
