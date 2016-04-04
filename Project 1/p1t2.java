
import java.util.*;
import java.sql.*;

public class p1t2
{
	private dbFunctions db = new dbFunctions();
	public static void main(String[] args) throws Exception
	{
		p1t2 ints = new p1t2();
		ints.run_menu();
	}
	/**
		Prompts meny to user until terminated
	*/

	public void run_menu() throws Exception
	{
		db.make_connection("jdbc:mysql:///moviedb","root", "!Mussy1243**");
		String in = "";
		do
		{
			print_menu();
			in = System.console().readLine();
			int option = convert_string_to_int(in);

			//If conversion succeeds, run the option user selected
			if(option > 0)
				run_chosen_option(option);

			System.out.println("");
		}while(!in.equals("q"));

		
	}

	/**
		Prints full menu to console
	*/
	public void print_menu()
	{
			System.out.println("Select option, enter q when done:");
			System.out.println("(1) Search movies by star name or ID number.");
			System.out.println("(2) Insert new star into databse.");
			System.out.println("(3) Insert customer into databse.");
			System.out.print(">");
	}

	/**
		Runs option from menu that user selects
		@param option_number - integer representing option user wants to use
	*/

	public  void run_chosen_option(int option_number) throws Exception
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
			default:
				System.out.println("Incorrect option number entered");
		}
	}

	/**
		Search the database for movies that a star has been in
	*/
	public void search_movie_by_star() throws Exception
	{
		System.out.println("(1) Search by name. ");
		System.out.print("(2) Search by ID. ");
		int option = convert_string_to_int(System.console().readLine());

		ResultSet results = null;
		if(option == 1)
		{
			String names[] = new String[2];
			System.out.print("First Name: ");
			names[0] = System.console().readLine();
			System.out.print("Last Name: ");
			names[1] = System.console().readLine();

			results = db.select("select * from movies where id in (select movies_id from stars_in_movies where star_id in " +
				" (select id from stars where stars.first_name = \""+ names[0] + "\" AND stars.last_name = \"" +names[1] + "\"))");

		}
		else if(option == 2)
		{
			System.out.print("ID number: ");
			String id = System.console().readLine();
			results = db.select("select movies from stars_in_movies as sim CROSS JOIN movies WHERE sim.movies_id = movies.id AND sim.star_id = " + id );
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
	public void print_movie_info(ResultSet movies) throws Exception
	{

		while(results.next())
		{
			System.out.println("ID: " + movies.getString("id"));
			System.out.println("Title: " + movies.getString("title"));
			System.out.println("Year: " + movies.getString("year"));
			System.out.println("Director: " + movies.getString("director"));
			System.out.println("Banner URL: " + movies.getString("banner_url"));
			System.out.println("Trailer: " + movies.getString("trailer"));
			System.out.println("");
		}
		/*while(star_ids.next())
		{
			ResultSet movie_ids = db.select("SELECT movies_id FROM stars_in_movies WHERE star_id = " + star_ids.getInt("id"));
			while(movie_ids.next())
			{
				ResultSet movie = db.select("SELECT * FROM movies WHERE id = " + movie_ids.getInt("movies_id"));
				if(movie.next())
				{
					System.out.println("Actor: " + star_ids.getString("first_name") + " " + star_ids.getString("last_name"));
					System.out.println("Title: " + movie.getString("title"));
					System.out.println("ID: " + movie.getString("id"));
					System.out.println("Year: " + movie.getString("year"));
					System.out.println("Director: " + movie.getString("director"));
					System.out.println("Banner URL: " + movie.getString("banner_url"));
					System.out.println("Trailer: " + movie.getString("trailer"));
					System.out.println("");
				}
			}
		}*/
	}

	public void insert_new_star() throws Exception
	{
		System.out.print("Enter name of star to be entered: ");
		String[] tokes = get_tokenized_input();
		int result;
		String[] names = resolve_first_and_last_name(tokes);
		
		result = db.update("INSERT INTO stars (first_name, last_name) VALUES (?,?)", names);
		if(result > 0)
		{
			System.out.println("Inserted \"" + names[0] + "\" \"" + names[1] + "\" into stars ");
		}
		else
		{
			System.out.println("Inserting star failed");
		}
	}

	public void insert_new_customer() throws Exception
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

	public String[] get_customer_information()
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

	public int add_cc_to_db(String[] cc_info) throws Exception
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

	public void add_customer_to_db(String[] customer_info) throws Exception
	{
		int insert_result = db.update("INSERT INTO customers(first_name, last_name, cc_id, address, email, password) VALUES (?,?,?,?,?,?)", customer_info);
	}

	/**
		Converts string to integer
		@param input - string to convert
		@return int - string converted to integer, -1 on failure
	*/
	public int convert_string_to_int(String input)
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

	public String[] get_tokenized_input()
	{
		String in = System.console().readLine().trim();
		String[] tokes = in.split(" ",2);
		return tokes;
	}

	public String[] resolve_first_and_last_name(String[] tokes)
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