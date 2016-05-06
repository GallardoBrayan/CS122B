package fabflix;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Star implements java.io.Serializable
{
	private Integer id;
	private String dob;
	private String first_name;
	private String last_name;
	private String photo_url;
	private HashMap<Integer, String> movies;
	public Star(Integer id,  String first_name, String last_name, String dob, String photo_url, HashMap<Integer, String> movies)
	{
		super();
		this.id = id;
		this.dob = dob;
		this.first_name = first_name;
		this.last_name = last_name;
		this.photo_url = photo_url;
		this.movies = movies;
	}
	
	public Star()
	{
		this.dob ="0000/00/00";
		this.first_name = "";
		this.last_name = "";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPhotoUrl()
	{
		return this.photo_url;
	}
	public void setPhotoUrl(String photo_url)
	{
		this.photo_url = photo_url;
	}
	public HashMap<Integer, String> getMovies() {
		return movies;
	}
	public void setMovies(HashMap<Integer, String> movies) {
		this.movies = movies;
	}
	
}
	
	