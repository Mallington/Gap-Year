/**
 *itemsService offers services to get items from the database
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.techsails.Entites.Login;
import de.techsails.Entites.User;
import de.techsails.db.DBManager;

/**
 * The Class itemsService.
 */

@Path("")
public class itemsService {
	private DBManager dbm;

	public itemsService() {
		try {
			dbm = new DBManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {

		return "RESTful server for ML project " + dbm.toString() + "online DB on Google Cloud!";
	}

	@GET
	@Path("/items/{itemNo}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getItem(@PathParam("itemNo") String id) {
		return "Test " + id;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateDependencies(Login login) {
		return dbm.getUser(login.getEmail(), login.getPwd());
	}

}