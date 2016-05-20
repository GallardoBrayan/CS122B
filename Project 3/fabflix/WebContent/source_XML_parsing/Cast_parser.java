
import fabflix.*;
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
 

public class Cast_parser extends DefaultHandler{

	private String tempVal;
	
	private LinkedHashMap<String, Integer> fid_movie = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<Integer, Integer> starid_movieid = new LinkedHashMap<Integer, Integer>();
	private LinkedHashMap<String, Integer> name_starid = new LinkedHashMap<String, Integer>();
	private String fid;
	private String movie_star;
	private dbFunctions conn = new dbFunctions();
	
	public void parseDocument(LinkedHashMap<String, Integer> fid_movie, LinkedHashMap<String, Integer> name_id){
		this.fid_movie = fid_movie;
		this.name_starid = name_id;
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			try{
				conn.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
				//conn.make_connection("jdbc:mysql://localhost:3306/moviedb_project3_grading", "classta", "classta");
				}catch(Exception e)
				{}
			//parse the file and also register this class for call backs
			sp.parse("casts124.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		conn.close();

	}

	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
	}

	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(qName.equalsIgnoreCase("f"))
		{
			fid = tempVal;
		}
		else if(qName.equalsIgnoreCase("a"))
		{
			movie_star = tempVal;
			Integer starid = name_starid.get(movie_star);
			Integer movieid = fid_movie.get(fid);

			if(starid != null && movieid != null)
			{
				starid_movieid.put(starid, movieid);
			}
			movie_star = "";
			fid = "";
		}
		else if(qName.equals("casts"))
		{
			execute_batch();
		}
	}
	public void execute_batch()
	{
		try {
			conn.starid_movieid_batch(starid_movieid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void error(SAXParseException e)
	{
		e.getMessage();
	}

	
}


	

