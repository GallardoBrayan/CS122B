package fabflix;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class dbFunctions {
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
	public void make_connection(String path, String user_name, String pass) throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(path, user_name, pass);
	}

	public DatabaseMetaData get_metadata() throws Exception {
		return connection.getMetaData();
	}

	public ResultSet select(String stmt) throws Exception {
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		return results;
	}

	public ResultSet raw_select(String stmt) throws Exception {
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		// select.close();
		return results;
	}

	public int raw_update(String stmt) throws Exception {
		Statement update = connection.createStatement();
		int results = update.executeUpdate(stmt);
		update.close();
		return results;
	}

	public int update(String stmt, String[] args) throws Exception {
		PreparedStatement update = connection.prepareStatement(stmt);
		for (int i = 1; i <= args.length; ++i) {
			update.setObject(i, args[i - 1]);
		}
		int results = 0;

		try {
			results = update.executeUpdate();
		} catch (Exception ex) {

			// Skip handling exception. Result will return that no rows were
			// inserted
			// System.out.println("Exception: ");
			// System.out.println(ex.getMessage());
		}

		update.close();
		return results;

	}

	public ArrayList<Map<String, Object>> selectUsing(String table, String columns, String values) throws SQLException {

		String statementString = "SELECT * FROM " + table;
		List<String> listOfColums = Arrays.asList(columns.split(","));
		List<String> listOfvalues = Arrays.asList(values.split(","));
		if (listOfColums.size() > 0) {
			statementString += " WHERE " + listOfColums.get(0) + "=" + listOfvalues.get(0);
			for (int i = 0; i < listOfColums.size(); i++) {

				statementString += " AND " + listOfColums.get(i) + "=" + listOfvalues.get(i);
			}
		}
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		ArrayList<Map<String, Object>> output = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = results.getMetaData();
		int numCol = rsmd.getColumnCount();
		ArrayList<String> coulumNames = new ArrayList<String>();
		for (int i = 1; i <= numCol; i++) {
			coulumNames.add(rsmd.getColumnLabel(i));
		}
		while (results.next()) {

			Map<String, Object> temp = new TreeMap<String, Object>();
			for (String currCol : coulumNames) {
				temp.put(currCol, results.getObject(currCol));
			}
			output.add(temp);

		}
		results.close();
		statement.close();
		return output;
	}

	public void close() {
		try {
			if (connection != null)

				connection.close();
		} catch (SQLException e) {
			System.out.println("Warning:The database connection was not closed properly.");
		}
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

	public ArrayList<Movie> getMovieByGenre(int start, int limit, String Genre) throws SQLException {
		String statementString = "SELECT * FROM movies";
		if (!"".equals(Genre)) {
			statementString += " WHERE  id IN (SELECT movies_id FROM genres_in_movies where genres_id IN "
					+ " (SELECT id FROM genres WHERE name=\'" + Genre + "\'))";
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

	public Boolean add_constraint(StringBuilder query, String column, String cond_operator, boolean is_first) {
		if (!is_first) {
			query.append(" " + cond_operator + " ");
			is_first = false;
		}
		query.append(column + " = ?");
		return is_first;
	}

	public Boolean add_constraint(StringBuilder query, String column, String cond_operator, String start_wrap, String end_wrap,
			Boolean is_first) {
		if (!is_first) {
			query.append(" " + cond_operator + " ");
			is_first = false;
		}
		query.append(start_wrap + column + " = ?" + end_wrap);
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
		return tempOutput;
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
		return tempOutput;
	}
	
	public void populate_list(LinkedHashMap<Integer, Movie> ret_movies, ResultSet rs) throws Exception {
		while (rs.next()) {
			Integer movie_id = rs.getInt("id");

			Movie cMovie = new Movie(movie_id, rs.getString("title"), rs.getInt("year"), rs.getString("director"),
					rs.getString("banner_url"), rs.getString("trailer"), getGenreFromMovieId(movie_id),
					getStarFromMovieId(movie_id));

			ret_movies.put(movie_id, cMovie);
		}

	}

	public void build_query(StringBuilder query, SearchParameters curParams) {
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
				+ "INNER JOIN movies ON movies.id = stars_in_movies.movies_id "
				+ "INNER JOIN genres_in_movies ON genres_in_movies.movies_id = movies.id WHERE ");

		build_query(query, curSearch);

		LinkedHashMap<Integer, Movie> movie_list = new LinkedHashMap<Integer, Movie>();

		PreparedStatement ps = connection.prepareStatement(query.toString());
		add_ps_parameters(ps, curSearch);
		ResultSet rs = ps.executeQuery();
		populate_list(movie_list, rs);

		return movie_list;
	}

	private void add_ps_parameters(PreparedStatement ps, SearchParameters curParams) throws SQLException {
		int count = 1;
		if (!"".equals(curParams.getTitle())) {
			ps.setObject(count, curParams.getTitle());
			++count;
		}
		if (!"".equals(curParams.getYear())) {
			ps.setObject(count, curParams.getYear());
			++count;
		}
		if (!"".equals(curParams.getDirector())) {
			ps.setObject(count, curParams.getDirector());
			++count;

		}
		if (!"".equals(curParams.getFirstName())) {
			if (!"".equals(curParams.getLastName())) {
				ps.setObject(count, curParams.getFirstName());
				++count;
				ps.setObject(count, curParams.getLastName());
				++count;
			} else {
				ps.setObject(count, curParams.getFirstName());
				++count;
				ps.setObject(count, curParams.getFirstName());
				++count;
			}
		} else if (!"".equals(curParams.getLastName())) {
			ps.setObject(count, curParams.getLastName());
			++count;
			ps.setObject(count, curParams.getLastName());
			++count;
		}
		
	}
}
