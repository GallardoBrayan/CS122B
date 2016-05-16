package fabflix;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DashboardFunctions
 */
public class DashboardFunctions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardFunctions() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sess = request.getSession();
		dbFunctions db = new dbFunctions();
		try {
			db.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");

			String submitValue = request.getParameter("submit");
			if ("Add Star".equals(submitValue)) {
				Star starToInsert = new Star();
				starToInsert.setDob(request.getParameter("DateOfBirth"));
				String fullName = request.getParameter("name").trim();
				if (fullName.contains(" ")) {
					starToInsert.setFirst_name(fullName.substring(0, fullName.lastIndexOf(" ")));
					starToInsert.setLast_name(fullName.substring(fullName.lastIndexOf(" "), fullName.length()));
				} else {
					starToInsert.setFirst_name("");
					starToInsert.setLast_name(fullName);
				}
				starToInsert.setPhotoUrl(request.getParameter("starphotourl"));
				sess.setAttribute("StarAdded", starToInsert);
				sess.setAttribute("fromStarAdd", db.insertStar(starToInsert));
			}
			else if ("Provide the metadata of the database".equals(submitValue)) {
				sess.setAttribute("metaData", db.get_metadata());
			}
			else if ("Add Movie".equals(submitValue)) {
				Movie newMovie = new Movie();
				newMovie.setTitle(request.getParameter("title"));
				newMovie.setDirector(request.getParameter("director"));
				newMovie.setYear(Integer.parseInt(request.getParameter("year").trim()));
				newMovie.setTrailer_url(request.getParameter("trailer"));
				newMovie.setBanner_url(request.getParameter("banner"));
				Star starToInsert = new Star();
				starToInsert.setDob(request.getParameter("DateOfBirth"));				 			
				starToInsert.setFirst_name(request.getParameter("firstname").trim());
				starToInsert.setLast_name(request.getParameter("lastname").trim());
				starToInsert.setPhotoUrl(request.getParameter("starphotourl"));
				String genre = request.getParameter("genre");
				String outmsg = db.addMovieViaStoredProceduce(newMovie, starToInsert, genre);
				sess.setAttribute("AddResult", outmsg);
			}
		} catch (Exception e) {
			sess.setAttribute("AddResult", "No changes made. Please Verify your input.");
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/_dashboard/Main");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
