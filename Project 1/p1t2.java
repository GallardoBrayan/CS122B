
import java.util.*;
import java.sql.*;

public class p1t2
{
	private static dbFunctions db = new dbFunctions();
	public static void main(String[] args) throws Exception
	{
		while(true){
			String in = "";
			Boolean menu_exited = false;
			login();
			while(!menu_exited)
			{
				
				try
				{
					print_menu();
					in = System.console().readLine();
					int option = convert_string_to_int(in);

					if(option == 7)
					{
						menu_exited = true;
						continue;
					}
				
					run_chosen_option(option);
					System.out.println("");
				}
				catch(Exception ex)
				{
					System.out.println("Error: something went wrong");
					continue;
				}
			}
		}
	}

	/**
		Prints full menu to console
	*/
	public static void print_menu()
	{
			System.out.println("Select option, enter q when done:");
			System.out.println("(1) Search movies by star name or ID number.");
			System.out.println("(2) Insert new star into databse.");
			System.out.println("(3) Insert customer into databse.");
			System.out.println("(4) Delete customer from databse.");
			System.out.println("(5) Print databse metadata.");
			System.out.println("(6) Insert valid SQL command.");
			System.out.println("(7) Exit menu.");
			System.out.print(">");
	}

	/**
		Runs option from menu that user selects
		@param option_number - integer representing option user wants to use
	*/

	public static void run_chosen_option(int option_number) throws Exception
	{

		switch(option_number)
		{
			case -1:
				System.out.println("Please enter an integer value");
				break;
			case 1:
				search_movie_by_star();
				break;
			case 2:
				insert_new_star();
				break;
			case 3:
				insert_new_customer();
				break;
			case 4:
				delete_customer();
				break;
			case 5:
				print_metadata();
				break;
			case 6:
				exec_sql_statement();
				break;
			default:
				System.out.println("Incorrect option number entered");
		}
	}

	public static void login() throws Exception
	{
		Boolean login_accepted = false;
		while(!login_accepted)
		{
			System.out.println("=====FabFlix=====");
			System.out.print("User: ");
			String username = System.console().readLine();
			System.out.print("Password: ");
			String password = System.console().readLine();


			try{
				db.make_connection("jdbc:mysql:///moviedb",username, password);
				login_accepted = true;
			}
			catch(Exception ex)
			{
				System.out.println("Can't connect to database....exiting");
			}
		}
	}
	/**
		Search the database for movies that a star has been in
	*/
	public static void search_movie_by_star() throws Exception
	{
		System.out.println("(1) Search by name. ");
		System.out.print("(2) Search by ID. ");
		int option = convert_string_to_int(System.console().readLine());

		ArrayList<Map<String, Object>> results = null;
		if(option == 1)
		{
			String names[] = new String[2];
			System.out.print("First Name: ");
			names[0] = System.console().readLine();
			System.out.print("Last Name: ");
			names[1] = System.console().readLine();

			if("".equals(names[0]))
				results = db.selectUsing(" stars_in_movies as sim CROSS JOIN movies CROSS JOIN stars", "sim.movies_id,sim.star_id,stars.last_name", "movies.id,stars.id,\"" + names[1] + "\"");
			else
				results = db.selectUsing(" stars_in_movies as sim CROSS JOIN movies CROSS JOIN stars", "sim.movies_id,sim.star_id,stars.first_name,stars.last_name", "movies.id,stars.id,\"" + names[0] +"\",\"" + names[1] + "\"");

		}
		else if(option == 2)
		{
			System.out.print("ID number: ");
			String id = System.console().readLine();
			results = db.selectUsing("stars_in_movies as sim CROSS JOIN movies", "sim.movies_id,sim.star_id", "movies.id," + id );
		}
		else
		{
			System.out.println("Incorrect option entered.");
		}

		print_movie_info(results);
	}


	/**
		Print the information for movies that a star has been featured int
	*/
	public static void print_movie_info(ArrayList<Map<String, Object>> movies) throws Exception
	{

		if(movies.isEmpty())
		{
			System.out.println("No results found");
		}
		else
		{
			for(Map<String, Object> curRow : movies)
			{
				System.out.println("ID: " + curRow.get("id"));
				System.out.println("Title: " + curRow.get("title"));
				System.out.println("Year: " + curRow.get("year"));
				System.out.println("Director: " + curRow.get("director"));
				System.out.println("Banner URL: " + curRow.get("banner_url"));
				System.out.println("Trailer: " + curRow.get("trailer"));
				System.out.println("");
			}
		}
	}

	public static void insert_new_star() throws Exception
	{
		String[] star_info = new String[4];
		System.out.print("First Name: ");
		star_info[0] = System.console().readLine().trim();
		System.out.print("Last Name: ");
		star_info[1] = System.console().readLine().trim();
		System.out.print("Date of Birth: ");
		star_info[2] = System.console().readLine().trim();
		System.out.print("Banner Url: ");
		star_info[3] = System.console().readLine().trim();
		System.out.println("");

		int result = db.update("INSERT INTO stars (first_name, last_name, dob, banner_url) VALUES (?,?,?,?)", star_info);
		if(result > 0)
		{
			System.out.println("Inserted \"" + star_info[0] + "\" \"" + star_info[1] + "\" into stars ");
		}
		else
		{
			System.out.println("Inserting star failed.  A parameter was not formatted correctly");
		}
	}

	public static void insert_new_customer() throws Exception
	{
		String[] info = get_customer_information();
		String[] cc_info = new String[]{info[0], info[1], info[2], info[3]}; //cc#, firstname, lastname, expiration
		String[] customer_info = new String[]{info[1], info[2], info[0], info[4], info[5], info[6]}; // firstname, lastname, cc#, address, email, password
		
		int cc_entry_sucess = add_cc_to_db(cc_info);
		if(cc_entry_sucess > 0)
		{
			add_customer_to_db(customer_info);
		}
		else
		{
			System.out.println("Error: Credit card information entered is incorrect.");
		}
	}

	public static String[] get_customer_information()
	{
		String[] info = new String[7];
		//info order: cc#, first name, last name, expiration, address, email, password;
		System.out.print("Credit Card Number: ");
		info[0] = System.console().readLine();
		System.out.print("First Name: ");
		info[1] = System.console().readLine();
		System.out.print("Last Name: ");
		info[2] = System.console().readLine();
		System.out.print("Expiration Date: ");
		info[3] = System.console().readLine();
		System.out.print("Address: ");
		info[4] = System.console().readLine();
		System.out.print("Email: ");
		info[5] = System.console().readLine();
		System.out.print("Password: ");
		info[6] = System.console().readLine();
		System.out.println("");

		return info;
	}

	public static int add_cc_to_db(String[] cc_info) throws Exception
	{
		int insert_result = 0;
		if("".equals(cc_info[0]))
			cc_info[0] = null;
		try
		{
			 insert_result = db.update("INSERT INTO creditcards VALUES (?,?,?,?)", cc_info);
		}
		catch(Exception ex){
			//Just don't handle the exception.  If the update fails, it is becuase a value entered was not formatted correctly or 
			//nothing was entered.  Either way, user will be informed that update failed and that the info was incorrect.
		}

		return insert_result;
	}

	public static void add_customer_to_db(String[] customer_info) throws Exception
	{
		int insert_result = db.update("INSERT INTO customers(first_name, last_name, cc_id, address, email, password) VALUES (?,?,?,?,?,?)", customer_info);
	}

	public static void delete_customer() throws Exception
	{
		String[] cc_info = new String[1];
		System.out.print("Eneter Credit Card ID: ");
		cc_info[0] = System.console().readLine().trim();
		System.out.println("");

		int result = db.update("DELETE FROM customers WHERE cc_id = ?", cc_info);
		if(result > 0)
		{
			System.out.println("Customer deleted successfully.");
		}
		else
		{
			System.out.println("Customer not found.");
		}

	}

	public static void print_metadata() throws Exception
	{
		DatabaseMetaData dbmd = db.get_metadata();
		ResultSet tables = dbmd.getTables(null,null,null,null);

		while(tables.next())
		{
			String table_name = tables.getString(3);
			System.out.println("Table: " + table_name);
			ResultSet cols = dbmd.getColumns(null,null,table_name, null);
			//int colCount = cols.getColumnCount();

			while(cols.next())
			{
				System.out.print(cols.getString("COLUMN_NAME"));
				System.out.print(" : ");
				System.out.print(cols.getString("TYPE_NAME"));
				System.out.println("");
			}
			System.out.println("");
		}
		tables.close();
	}

	public static void exec_sql_statement() throws Exception
	{
		System.out.print("Enter SQL statement: ");
		String input_statement = System.console().readLine().toLowerCase().trim(); //convert to lower to check for variations
		String[] tokes = input_statement.split(" ", 2);  

		ResultSet query_result = null;
		int update_result = 0;
		System.out.println(tokes[0]);
		if(tokes[0].equals("select"))
		{
			query_result = db.raw_select(input_statement);
		}
		else
		{
			update_result = db.raw_update(input_statement);
		}

		if(query_result != null)
		{
			ResultSetMetaData md = query_result.getMetaData();
			int colCount = md.getColumnCount();
			while(query_result.next())
			{
				for(int i = 1; i <= colCount; ++i)
				{
					String col_val = query_result.getString(i);
					String col_name = md.getColumnName(i);
					System.out.print(col_name + ": " + col_val + " ");
				}
				System.out.println("");
			}
		}
		else
		{
			System.out.println("Number of rows affected from statement: " + update_result);
		}

	}
	/**
		Converts string to integer
		@param input - string to convert
		@return int - string converted to integer, -1 on failure
	*/
	public static int convert_string_to_int(String input)
	{
		int ret_val = 0;
		try
		{
			ret_val = Integer.parseInt(input);
		}
		catch(NumberFormatException e)
		{
			ret_val = -1;
		}
		return ret_val;
	}

	public static String[] get_tokenized_input()
	{
		String in = System.console().readLine().trim();
		String[] tokes = in.split(" ",2);
		return tokes;
	}

	public static String[] resolve_first_and_last_name(String[] tokes)
	{
		String[] names = new String[2];
		if(tokes.length > 1)
		{
			names[0] = tokes[0];
			names[1] = tokes[1];
		}
		else
		{
			names[0] = "";
			names[1] = tokes[0];
		}

		return names;
	}
}