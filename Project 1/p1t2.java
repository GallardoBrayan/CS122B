
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
		db.make_connection("jdbc:mysql:///moviedb","root", "root");
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
			System.out.print("> ");
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
			default:
				System.out.println("Incorrect option number entered");
		}
	}

	/**
		Search the database for movies that a star has been in
	*/
	public void search_movie_by_star() throws Exception
	{
		System.out.print("Please enter name or ID number of star to search: ");
		String user_input = System.console().readLine();
		String[] tokes = user_input.split(" ");
		ResultSet movie_ids  = null;
		ResultSet star_ids = null;

		//First case, check if first token is ID
		if(convert_string_to_int(tokes[0]) > 0)
		{
			star_ids = db.select("SELECT id,frist_name,last_name FROM stars WHERE id = " + tokes[0]);
		}
		else //search by name
		{
			if(tokes.length == 1)
			{
				//then we need to search for stars that have this name as a first or last name.  We will grab results where a star 
				//has either the frist name or last name, so it could return more than one star_id
				star_ids = db.select("SELECT id,frist_name,last_name FROM stars WHERE frist_name = \"" + tokes[0] + "\" OR last_name = \"" + tokes[0] + "\"");
			}
			else
			{
				star_ids = db.select("SELECT id,frist_name,last_name FROM stars WHERE frist_name = \"" + tokes[0] + "\" AND last_name = \"" + tokes[1] + "\"");
			}
		}

		print_movie_info(star_ids);
	}


	/**
		Print the information for movies that a star has been featured int
	*/
	public void print_movie_info(ResultSet star_ids) throws Exception
	{

		while(star_ids.next())
		{
			ResultSet movie_ids = db.select("SELECT movies_id FROM stars_in_movies WHERE star_id = " + star_ids.getInt("id"));
			while(movie_ids.next())
			{
				ResultSet movie = db.select("SELECT * FROM movies WHERE id = " + movie_ids.getInt("movies_id"));
				if(movie.next())
				{
					System.out.println("Actor: " + star_ids.getString("frist_name") + " " + star_ids.getString("last_name"));
					System.out.println("Title: " + movie.getString("title"));
					System.out.println("ID: " + movie.getString("id"));
					System.out.println("Year: " + movie.getString("year"));
					System.out.println("Director: " + movie.getString("director"));
					System.out.println("Banner URL: " + movie.getString("banner_url"));
					System.out.println("Trailer: " + movie.getString("trailer"));
					System.out.println("");
				}
			}
		}

	}

	public void insert_new_star() throws Exception
	{
		System.out.print("Enter name of star to be entered: ");
		String in = System.console().readLine();
		String[] tokes = in.split(" ");
		if(tokes.length == 1)
		{
			db.update("INSERT INTO stars (frist_name, last_name) VALUES (" + "\"\"," + tokes[0] + ")");
		}
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
}