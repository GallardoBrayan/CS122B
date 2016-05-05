package fabflix;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
 

public class Parser extends DefaultHandler{

	Movie NewMovie;
	Star NewStar;
	LinkedHashMap<String, Integer>Title_to_Genre = new LinkedHashMap<String, Integer>();
	LinkedHashMap<Integer, Integer>MovieID_GenreID = new LinkedHashMap<Integer, Integer>();
	ArrayList<ResultSet> movie_ids;
	ArrayList<String> movie_batch_values = new ArrayList<String>();
	ArrayList<String> genre_in_movie_batch_values = new ArrayList<String>();
	String DirectorName;
	String tempVal;
	String value_begin = "(";
	String value_end = ")";
	dbFunctions conn = new dbFunctions();
	
	public static void main(String[] args) {
		Parser parse = new Parser();
		parse.parseDocument();
	}
	private void parseDocument(){
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("mains243.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		try{
		conn.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
		}catch(Exception e)
		{}
	}

	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("film"))
		{
			NewMovie = new Movie();
			NewMovie.setDirector(DirectorName);
		}

	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{

		
		if(qName.equalsIgnoreCase("Dirname"))
		{
			DirectorName = tempVal;
		}
		else if (qName.equalsIgnoreCase("t"))
		{
			NewMovie.setTitle(tempVal);
		}
		else if (qName.equalsIgnoreCase("year"))
		{
			Integer year = 0;
			try
			{
				year = Integer.parseInt(tempVal);
			}catch(Exception e)
			{
				//Do Nothin;
				//User year zero if it fails
			}
				NewMovie.setYear(year);
		}
		else if (qName.equalsIgnoreCase("cat"))
		{
			//System.out.println("");
			NewMovie.addGenre(tempVal);
		}
		else if (qName.equalsIgnoreCase("film"))
		{
			try {
				append_to_queries();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DirectorName = "";
		}
		else if(qName.equalsIgnoreCase("movies"))
		{
			try {
				execute_batch_inserts();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void append_to_queries() throws Exception
	{
		//Make Movie querry
		add_movie_query();
		add_genre();
	}
	public void add_movie_query()
	{
		String val= value_begin + NewMovie.getTitle() + "," + NewMovie.getYear() + "," + NewMovie.getDirector() +
				value_end;
		movie_batch_values.add(val);
	}
	public void add_genre() throws Exception
	{	
		for(String genre: NewMovie.getGenres())
		{
			Integer genre_id = conn.getGenreIdFromName(genre);
			if(genre_id < 0)
			{
				genre_id = conn.insert_genre(genre);
			}
			Title_to_Genre.put(NewMovie.getTitle(), genre_id);
		}
	}
	
	public void execute_batch_inserts() throws SQLException
	{
		//do batch for movie
		String final_batch_query = get_final_movie_batch_query();
		ResultSet rs = conn.movie_batch_insert(final_batch_query);
		
		//do batch for genres_in_movies
		make_movieID_genreID_map(rs);
		build_genres_in_movies_values();
		String final_genres_in_movies_query = get_final_gim_query();
		conn.gim_batch_insert(final_genres_in_movies_query);
	}
	
	public String get_final_gim_query()
	{
		String ret = "INSERT INTO genres_in_movies (genres_id, movies_id) VALUES ";
		int count = genre_in_movie_batch_values.size();
		for(int i = 0; i < count; ++i)
		{
			ret += genre_in_movie_batch_values.get(i);
			if(i != count - 1)
			{
				ret += ",";
			}
		}
		return ret;
	}
	public void build_genres_in_movies_values()
	{
		String val;
		for(Map.Entry<Integer, Integer> c : MovieID_GenreID.entrySet())
		{
			val = value_begin + c.getKey() + "," + c.getValue() + value_end;
			genre_in_movie_batch_values.add(val);
		}
	}
	
	public void make_movieID_genreID_map(ResultSet rs) throws SQLException
	{
		while(rs.next()){
			Integer movie_id = rs.getInt("id");
			String title = rs.getString("title");
			MovieID_GenreID.put(Title_to_Genre.get(title), movie_id);
		}
	}
	public String get_final_movie_batch_query()
	{
		String ret = "INSERT INTO movies (title, year, director) VALUES ";
		int count = movie_batch_values.size();
		for(int i = 0; i < count; ++i)
		{
			ret += movie_batch_values.get(i);
			if(i != count - 1)
			{
				ret += ",";
			}
		}
		return ret;
	}
	
	public void error(SAXParseException e)
	{
		e.getMessage();
	}

	
}


	

