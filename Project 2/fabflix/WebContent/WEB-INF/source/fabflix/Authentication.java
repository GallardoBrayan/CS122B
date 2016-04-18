package fabflix;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Authentication
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

	//Checks email/pass combo.  Return true if correct and false otherwise
	public boolean login_authentication(String email, String password) throws Exception
	{
		establish_connection();
		String query = "SELECT COUNT(id) from customers where email = ? AND password = ? LIMIT 1";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setObject(1, email);
		ps.setObject(2, password);
		ResultSet rs = ps.executeQuery();
		rs.next();
		boolean success = (rs.getInt(1) == 1);
		close_connection();
		return success;
	}
}