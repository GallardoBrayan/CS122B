import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import fabflix.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Parser extends DefaultHandler{

	Movie NewMovie;
	Star NewStar;
	ArrayList<ResultSet> movie_ids;
	String DirectorName;
	String tempVal;
	String movie_batch_query = "INSERT INTO movies (title, year, director) VALUES ";
	String genre_batch_query = "INSERT INTO genres (name) VALUES ";
	String genre_in_moves_batch_query = "INSERT INTO genres_in_movies (genres_id, movies_id) VALUES ";
	String value_begin = "(";
	String value_end = ")";
	dbFunctions conn;
	private void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("employee.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("film"))
			NewMovie = new Movie();

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
			NewMovie.setYear(Integer.parseInt(tempVal));
		}
		else if (qName.equalsIgnoreCase("cat"))
		{
			NewMovie.addGenre(tempVal);
		}
		else if (qName.equalsIgnoreCase("film"))
		{
			append_to_queries();
			DirectorName = "";
		}
		
	}
	
	public void append_to_queries()
	{
		//Make Movie querry
		add_movie_query();
		add_genre_query();
	}
	public void add_movie_query()
	{
		String to_append = value_begin + NewMovie.getTitle() + "," + NewMovie.getYear() + "," + NewMovie.getDirector() +
				value_end + ",";
		movie_batch_query += to_append;
	}
	public void add_genre_query()
	{
		conn.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
		
		boolean genre_exists = conn.getGenreIdFromName("temp_val");
	}
	
	public static void main(String[] args){
		Parser parse = new Parser();
	}
	
}


	

