/**
 *itemsService offers services to get items from the database
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.techsails.Control.FlightPlanning.FlightPlanner;
import de.techsails.Entites.FlightPlan;
import de.techsails.Entites.FlightQuote;
import de.techsails.Entites.Login;
import de.techsails.Entites.User;
import de.techsails.db.DBManager;
import jersey.repackaged.com.google.common.base.Joiner;

/**
 * The Class itemsService.
 */

@Path("")
public class itemsService {
	private DBManager dbm;
	private FlightPlanner fp;

	public itemsService() {
		try {
			dbm = new DBManager();
			fp = new FlightPlanner();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {

		return "RESTful server for ML project Version " + 22;
	}

	@GET
	@Path("/items/{itemNo}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getItem(@PathParam("itemNo") String id) {
		return "Test " + id;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User login(Login login) {
		return dbm.getUser(login.getEmail(), login.getPwd());
	}

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(User user) {
		return dbm.createUser(user);
	}

	@POST
	@Path("/countries")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<FlightQuote> register(FlightPlan flightPlan) throws ParseException {
		return fp.getFlightPlan(flightPlan.getCountries(), null, flightPlan.getDepartureDate(),
				flightPlan.getNumOfDaysInbetween());
	}

}