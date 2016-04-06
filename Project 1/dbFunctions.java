
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class dbFunctions
{
	private Connection connection;

	/**
		Establishes connection to database
		@param path - path to db
		@param user_name - mysql username
		@param pass - mysql password
	*/
	public void make_connection(String path, String user_name, String pass) throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(path, user_name, pass);
	}

	public DatabaseMetaData get_metadata() throws Exception
	{
		return connection.getMetaData();
	}

	public ResultSet select(String stmt) throws Exception
	{
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		return results;
	}

	public ResultSet raw_select(String stmt) throws Exception
	{
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		//select.close();
		return results;
	}

	public int raw_update(String stmt) throws Exception
	{
		Statement update = connection.createStatement();
		int results = update.executeUpdate(stmt);
		update.close();
		return results;
	}

	public int update(String stmt, String[] args) throws Exception
	{
		PreparedStatement update = connection.prepareStatement(stmt);
		for(int i = 1; i <= args.length; ++i)
		{
			update.setObject(i, args[i-1]);
		}
		int results = 0;

		try{
			results  = update.executeUpdate();
		}
		catch(Exception ex){ 
		
			//Skip handling exception.  Result will return that no rows were inserted
			//System.out.println("Exception: ");
			//System.out.println(ex.getMessage());
		}
		
		update.close();
		return results;

	}
	
	public ArrayList< Map< String, Object > > selectUsing(String table, String columns, String values) throws SQLException
	{

		String statementString = "SELECT * FROM " + table ;
		List<String> listOfColums =  Arrays.asList(columns.split(","));
		List<String> listOfvalues = Arrays.asList(values.split(","));
		if(listOfColums.size() > 0)
		{
			statementString += " WHERE " + listOfColums.get(0) + "=" + listOfvalues.get(0);	
			for(int i = 0; i < listOfColums.size(); i++)
			{

				statementString +=  " AND " + listOfColums.get(i) + "=" + listOfvalues.get(i);
			}
		}
		statementString += ";";
		PreparedStatement statement = connection.prepareStatement(statementString);
		ResultSet results = statement.executeQuery();
		ArrayList< Map< String, Object > > output = new ArrayList< Map< String, Object > >();
		ResultSetMetaData rsmd = results.getMetaData();
		int numCol = rsmd.getColumnCount();
		ArrayList<String> coulumNames = new  ArrayList<String>();
		for(int i =1; i <= numCol; i++)
		{
			coulumNames.add(rsmd.getColumnLabel(i));
		}
		while(results.next())
		{
			 
			Map< String, Object >temp =new TreeMap< String, Object >();
			for(String currCol : coulumNames)
			{
				temp.put(currCol, results.getObject(currCol));
			}
			output.add(temp);
			
		}
		results.close();
		statement.close();
		return output;
	}
	public void close()
	{
		try {
			if(connection != null)
			
				connection.close();
		} catch (SQLException e) 
		{
			System.out.println("Warning:The database connection was not closed properly.");
		}
	}
}

