package fabflix;

public class SearchParameters 
{

	private String title = "";
	private String year = "";
	private String director= "";
	private String firstName ="";
	private String lastName= "";
	private String sortType = "";
	private String moviePerPage = "";
	private String currentPage= "";
	private Boolean sortAccending = true;
	/**
	 * @param title
	 * @param year
	 * @param director
	 * @param fristName
	 * @param lastName
	 * @param sortType
	 * @param moviePerPage
	 * @param currentPage
	 * @param sortAccending
	 */
	public SearchParameters(String title, String year, String director, String fristName, String lastName,
			String sortType, String moviePerPage, String currentPage, Boolean sortAccending) {
		this.title = title;
		this.year = year;
		this.director = director;
		this.firstName = fristName;
		this.lastName = lastName;
		this.sortType = sortType;
		this.moviePerPage = moviePerPage;
		this.currentPage = currentPage;
		this.sortAccending = sortAccending;
	}
	public SearchParameters() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
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
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	/**
	 * @return the fristName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param fristName the fristName to set
	 */
	public void setFirstName(String fristName) {
		this.firstName = fristName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}
	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	/**
	 * @return the moviePerPage
	 */
	public String getMoviePerPage() {
		return moviePerPage;
	}
	/**
	 * @param moviePerPage the moviePerPage to set
	 */
	public void setMoviePerPage(String moviePerPage) {
		this.moviePerPage = moviePerPage;
	}
	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the sortAccending
	 */
	public Boolean getSortAccending() {
		return sortAccending;
	}
	/**
	 * @param sortAccending the sortAccending to set
	 */
	public void setSortAccending(Boolean sortAccending) {
		this.sortAccending = sortAccending;
	}
}
