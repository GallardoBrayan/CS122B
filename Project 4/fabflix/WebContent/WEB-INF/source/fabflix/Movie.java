package fabflix;

import java.util.HashSet;

@SuppressWarnings("serial")
public class Movie implements java.io.Serializable
{
	private int id;
	private String title;
	private int year;
	private String director;
	private String banner_url;
	private String trailer_url;
	private HashSet<String> genres = new HashSet<String>();
	private HashSet<String> stars;
	
	/**
	 * @return the id
	 */
	public int getId() 
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() 
	{
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the year
	 */
	public int getYear() 
	{
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) 
	{
		this.year = year;
	}
	/**
	 * @return the director
	 */
	public String getDirector() 
	{
		return director;
	}
	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) 
	{
		this.director = director;
	}
	/**
	 * @return the banner_url
	 */
	public String getBanner_url() {
		return banner_url;
	}
	/**
	 * @param banner_url the banner_url to set
	 */
	public void setBanner_url(String banner_url) 
	{
		this.banner_url = banner_url;
	}
	/**
	 * @return the trailer_url
	 */
	public String getTrailer_url() 
	{
		return trailer_url;
	}
	/**
	 * @param trailer_url the trailer_url to set
	 */
	public void setTrailer_url(String trailer_url) 
	{
		this.trailer_url = trailer_url;
	}
	public HashSet<String> getGenres() {
		return genres;
	}
	public void setGenres(HashSet<String> genres) {
		this.genres = genres;
	}
	public void addGenre(String genre)
	{
		this.genres.add(genre);
	}
	public HashSet<String> getStars() {
		return stars;
	}
	public void setStars(HashSet<String> stars) {
		this.stars = stars;
	}
	/**
	 * @param id
	 * @param title
	 * @param year
	 * @param director
	 * @param banner_url
	 * @param trailer_url
	 * @param genres
	 * @param stars
	 */
	public Movie(int id, String title, int year, String director, String banner_url, String trailer_url,
			HashSet<String> genres, HashSet<String> stars) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner_url = banner_url;
		this.trailer_url = trailer_url;
		this.genres = genres;
		this.stars = stars;
	}
	public Movie() {
		// TODO Auto-generated constructor stub
	}
	

}
