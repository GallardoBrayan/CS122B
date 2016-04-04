 
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

	public String append_string_array(String str, String[] to_append)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < to_append.length; ++i)
		{
   			sb.append(to_append[i]);
    		if (i != to_append.length - 1)
       			sb.append(",");
       	}
       	return str.concat(sb.toString());
    
	}

	public ResultSet select(String stmt) throws Exception
	{
		Statement select = connection.createStatement();
		ResultSet results = select.executeQuery(stmt);
		return results;
	}

	public ResultSet update(String stmt) throws Exception
	{
		Statement update = connection.createStatement();
		ResultSet results = update.executeQuery(stmt);
		return results;

	}

	public ArrayList< HashMap< String, Object > > selectUsing(String table, String columns, String values) throws SQLException
	{

		String statementString = "SELECT * FROM " + table ;
		ArrayList<String> listOfColums = (ArrayList<String>) Arrays.asList(columns.split(","));
		ArrayList<String> listOfvalues = (ArrayList<String>) Arrays.asList(values.split(","));
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
		ArrayList< HashMap< String, Object > > output = new ArrayList< HashMap< String, Object > >();
		ResultSetMetaData rsmd = results.getMetaData();
		int numCol = rsmd.getColumnCount();
		ArrayList<String> coulumNames = new  ArrayList<String>();
		for(int i =1; i <= numCol; i++)
		{
			coulumNames.add(rsmd.getColumnLabel(i));
		}
		while(results.next())
		{
			 
			HashMap< String, Object >temp =new  HashMap< String, Object >();
			for(String currCol : coulumNames)
			{
				temp.put(currCol, results.getObject(currCol));
			}
			output.add(temp);
			
		}
		return output;
	}
}


