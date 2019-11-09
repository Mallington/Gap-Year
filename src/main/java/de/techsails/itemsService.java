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

import de.techsails.db.DBManager;

/**
 * The Class itemsService.
 */

@Path("")
public class itemsService {

	//private static Logger log = Logger.getLogger(FileService.class);
	DBManager dbItemManaer;
	//CSVItemManager csvItemManager;
	public itemsService() throws SQLException, IOException, URISyntaxException {
		dbItemManaer = new DBManager();
		//csvItemManager = new CSVItemManager();
		//itemManager.createDependenciesTable();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {

		return "RESTful server for ML project"
				+ "\n\n"
				+ "Usage:\n"
				+ "GET\n\n"
				+ "to get one item: ./items/{itemNo}\n"
				+ "to get the item rating : ./itemRating/{itemNo}\n"
				+ "to get a specific item: ./specificItem/{itemNo} you will get extended information (properties) about the item\n"
				+ "to get specific items by type: ./specificItem/itemtype/{itemtype}\n"
				+ "to get multiple items: ./items/{itemFrom}/{itemTo}\n"
				+ "to get one or multiple items by name, type, manufacturer oder category: .getItemsBy/{getItemsBy}/{params:(.*)}\n"
				+ "to set the price range add /price/{priceMin}/{priceMax} and to sort add /{sort}, sort can be: 'ASC' or 'DESC'\n"
				+ "to get items in a price range: ./price/{priceMin}/{priceMax}\n"
				+ "to get items that cost more than the price given: ./pricemin/{priceMin}\n"
				+ "to get items that cost less than the price given: ./pricemax/{priceMax}\n"
				+ "to get the best n recommended items for itemId: ./itemRec/{itemId}/{n}\n"
				+ "to set recommendation type add /{recommendationType}, it can be: 'sim' fro similar or 'comp' for compatible keep it empty if you don't need it\n"
				+ "to set recommendation level add /{recommendationLevel}, it can be: 'low' or 'high' keep it empty if you don't need it\n"
				+ "to get n random recommended items for itemId: ./itemRandomRec/{itemId}/{n}\n"
				+ "same for recommendation type and level here\n"
				+ "to get items sorted by rating getItemsByRating/{sort} sort can be: 'ASC' or 'DESC'\n"
				+ "to get one or multiple items by name, type, manufacturer oder category sorted by rating getItemsByRating/{sort}/{getItemsBy}/{params:(.*)}\n"
				+ "to get information about the brand, item type or category use ./getInfo/{info} and replace info with: manufacturer, item_type or category\n"
				+ "\nPOST\n\n"
				+ "to update the dependency value between two items: ./update/{itemFrom}/{itemTo}/{token}\n"
				+ "to send the session ID use /update/user/{sessionID}/{token} and you will get the user information\n"
				+ "to set a user name use /update/user/{sessionID}/{username}/{token}\n"
				+ "to update ratings use /update/rating/{sessionID}/{itemID}/{rating}/{token}\n"
				+ "\n\n"
				+ "Machine Learning based resommendation:\n"
				+ "for content based recommendation use /itemCBRec/{itemId}/{size}\n"
				+ "for user based collaborative filtering use /itemUBCFRec/{sessionID}/{itemId}/{size}\n"
				+ "\n\n"
				+ "other services are coming soon!\n"
				+ "created by: Yousuf Amanuel\n"
				+ "last update: 07:54 p.m. (CET) 03.Apr.2019\n";
	}
	
	@GET
	@Path("/items/{itemNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem(@PathParam("itemNo") int id) {
		return dbItemManaer.getItem(id);
	}
	
	@GET
	@Path("/itemRating/{itemNo}")
	public int getItemRating(@PathParam("itemNo") int id) {
		return dbItemManaer.getItemRating(id);
	}
	
	@GET
	@Path("/specificItem/{itemNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificItemm(@PathParam("itemNo") int id) {
		return dbItemManaer.getSpecificItem(id);
	}
	
	@GET
	@Path("/specificItem/itemtype/{itemtype}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificItem(@PathParam("itemtype") String itemtype) {
		return dbItemManaer.getSpecificItemByType(itemtype);
	}
	
    @GET
	@Path("/items/{itemFrom}/{itemTo}")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItems(@PathParam("itemFrom") int itemFromId, @PathParam("itemTo") int itemToId){
        return dbItemManaer.getItems(itemFromId, itemToId);
    }

    @GET
	@Path("/itemCBRec/{itemId}/{size}")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Item> getContentBasedRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size){
        return dbItemManaer.contentBasedRecommendation(itemId, size);
    }
    
    @GET
   	@Path("/itemUBCFRec/{sessionID}/{itemId}/{size}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getContentBasedRecommendation(@PathParam("sessionID") String sessionID, @PathParam("itemId") int itemId, @PathParam("size") int size){
           return dbItemManaer.userBasedCollaborativeFiltering(sessionID, itemId, size);
       }
    
    @GET
	@Path("getItemsBy/{getItemsBy}/{params:(.*)}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItemsBy(@Context UriInfo uriInfo, @PathParam("getItemsBy") String getItemsBy){
			return dbItemManaer.getItemsBy(getItemsBy, uriInfo.getPathParameters().getFirst("params"));
    }
	
    @GET
	@Path("getItemsBy/{getItemsBy}/{params:(.*)}/price/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItemsBy(@Context UriInfo uriInfo, @PathParam("getItemsBy") String getItemsBy,
			@PathParam("priceMin") double priceMin, @PathParam("priceMax") double priceMax){
			return dbItemManaer.getItemsBy(getItemsBy, uriInfo.getPathParameters().getFirst("params"), priceMin, priceMax, "");
    }
    
    @GET
	@Path("getItemsBy/{getItemsBy}/{params:(.*)}/price/{priceMin}/{priceMax}/{sort}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItemsBy(@Context UriInfo uriInfo, @PathParam("getItemsBy") String getItemsBy,
			@PathParam("priceMin") double priceMin, @PathParam("priceMax") double priceMax, @PathParam("sort") String sort){
			return dbItemManaer.getItemsBy(getItemsBy, uriInfo.getPathParameters().getFirst("params"), priceMin, priceMax, sort);
    }
    
    @GET
	@Path("/price/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItemsByPrice(@PathParam("priceMin") double priceMin, @PathParam("priceMax") double priceMax){
        return dbItemManaer.getItemsByPrice(priceMin, priceMax);
    }
    
    @GET
   	@Path("/pricemin/{priceMin}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getItemsByMinPrice(@PathParam("priceMin") double priceMin){
           return dbItemManaer.getItemsByPrice(priceMin, Item.PRICE_MAX);
       }
    
    @GET
   	@Path("/pricemax/{priceMax}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getItemsByMaxPrice(@PathParam("priceMax") double priceMax){
           return dbItemManaer.getItemsByPrice(Item.PRICE_MIN, priceMax);
       }
    
    @GET
	@Path("/itemRec/{itemId}/{size}")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Item> getRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size){
        return dbItemManaer.getRecommendation(itemId, size, "", "");
    }
    
    @GET
   	@Path("/itemRec/{itemId}/{size}/{recommendationType}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size, @PathParam("recommendationType") String recommendationType){
           return dbItemManaer.getRecommendation(itemId, size, recommendationType, "");
       }
    
    @GET
   	@Path("/itemRec/{itemId}/{size}/{recommendationType}/{recommendationLevel}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size, @PathParam("recommendationType") String recommendationType, @PathParam("recommendationLevel") String recommendationLevel){
           return dbItemManaer.getRecommendation(itemId, size, recommendationType, recommendationLevel);
       }
    
    @GET
   	@Path("/itemRandomRec/{itemId}/{size}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getRandomRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size){
           return dbItemManaer.getRandomRecommendation(itemId, size, "", "");
       }
    
    @GET
   	@Path("/itemRandomRec/{itemId}/{size}/{recommendationType}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getRandomRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size, @PathParam("recommendationType") String recommendationType){
           return dbItemManaer.getRandomRecommendation(itemId, size, recommendationType, "");
       }
    
    @GET
   	@Path("/itemRandomRec/{itemId}/{size}/{recommendationType}/{recommendationLevel}")
   	@Produces(MediaType.APPLICATION_JSON)
       public List<Item> getRandomRecommendation(@PathParam("itemId") int itemId, @PathParam("size") int size, @PathParam("recommendationType") String recommendationType, @PathParam("recommendationLevel") String recommendationLevel){
           return dbItemManaer.getRandomRecommendation(itemId, size, recommendationType, recommendationLevel);
       }
    
    @GET
	@Path("getItemsByRating/{sort}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItemsByRating(@PathParam("sort") String sort){
			return dbItemManaer.getItemsByRating(sort,"", "");
    }
    
    @GET
	@Path("getItemsByRating/{sort}/{getItemsBy}/{params:(.*)}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItemsByRating(@Context UriInfo uriInfo, @PathParam("sort") String sort, @PathParam("getItemsBy") String getItemsBy){
			return dbItemManaer.getItemsByRating(sort, getItemsBy, uriInfo.getPathParameters().getFirst("params"));
    }
    
    @POST
	@Path("/update/{itemFrom}/{itemTo}/{token}")
	@Produces(MediaType.TEXT_PLAIN)
    public String updateDependencies(@PathParam("itemFrom") int itemFromId, @PathParam("itemTo") int itemToId, @PathParam("token") String token){
        return dbItemManaer.updateDependencies(itemFromId, itemToId, token);
    }
    
    @POST
	@Path("/update/{itemFrom}/{itemTo}")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateDependencies(@PathParam("itemFrom") int itemFromId, 
    								 @PathParam("itemTo") int itemToId,
    								 Token token){
        return dbItemManaer.updateDependencies(itemFromId, itemToId, token);
    }
    
    @GET
	@Path("/getInfo/{info}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getItemTypes(@PathParam("info") String info) {
		return dbItemManaer.getInfo(info);
	}
    
    @POST
	@Path("/update/user/{sessionID}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("sessionID") String sessionID, @PathParam("token") String token){
    	return dbItemManaer.setUser(sessionID, token);
    }
    
    @POST
   	@Path("/update/user/{sessionID}/{username}/{token}")
   	@Produces(MediaType.TEXT_PLAIN)
    public String updateUsername(@PathParam("sessionID") String sessionID, @PathParam("username") String username, @PathParam("token") String token){
    	return dbItemManaer.updateUsername(sessionID, username, token);
    }
    
    @POST
    @Path("/update/rating/{sessionID}/{itemID}/{rating}/{token}")
   	@Produces(MediaType.TEXT_PLAIN)
    public String updateRaing(@PathParam("sessionID") String sessionID, @PathParam("itemID") int itemID, @PathParam("rating") int rating, @PathParam("token") String token){
    	return dbItemManaer.updateRating(sessionID, itemID, rating, token);
    }
}