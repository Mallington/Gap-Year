/**
 *itemsService offers services to get items from the database
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.techsails.db.DBConnection;


/**
 * The Class itemsService.
 */

@Path("")
public class itemsService {
	private DBConnection db;
	
	public itemsService() {
		try {
			db = new DBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {

		return "RESTful server for ML project "+ db.toString() + "online DB on Google Cloud!";
	}
	
	@GET
	@Path("/items/{itemNo}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getItem(@PathParam("itemNo") String id) {
		return "Test "+ id;
	}
	
	
}