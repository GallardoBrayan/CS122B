package ya;

import java.util.LinkedHashMap;

public class Parser_Main
{
	public static void main(String[] args)
	{
		Parser movie_parser = new Parser();
		movie_parser.parseDocument();
		LinkedHashMap<String, Integer> fid = movie_parser.fid_mid();
		
		Actors_parse a_parser = new Actors_parse();
		a_parser.parseDocument();
		LinkedHashMap<String, Integer> s_to_id = a_parser.get_star_id_map();
		
		Cast_parser cast = new Cast_parser();
		cast.parseDocument(fid, s_to_id);
	}



}