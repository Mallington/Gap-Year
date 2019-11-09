/**
 *DBItemsManager manages the items in the database
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.simple.JSONObject;

import de.techsails.Item;
import de.techsails.SpecificItem;
import de.techsails.Token;
import de.techsails.User;
import de.techsails.ValueComparator;
import de.techsails.ValueComparatorInteger;
import de.techsails.items.Cable;
import de.techsails.items.Camera;
import de.techsails.items.Charger;
import de.techsails.items.Console;
import de.techsails.items.Controller;
import de.techsails.items.Desktop;
import de.techsails.items.Drive;
import de.techsails.items.Dvd_Br;
import de.techsails.items.Game;
import de.techsails.items.Headset;
import de.techsails.items.Keyboard;
import de.techsails.items.Lens;
import de.techsails.items.Mobile;
import de.techsails.items.Monitor;
import de.techsails.items.Mouse;
import de.techsails.items.Notebook;
import de.techsails.items.NotebookCase;
import de.techsails.items.Printer;
import de.techsails.items.Speaker;
import de.techsails.items.Tablet;
import de.techsails.items.Television;

public class DBManager {
	private DBConnection dbconnection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private final static String DB_QUERY = "SELECT * FROM sql7264905.items";
	private final static String USER_QUERY = "select * from sql7264905.users where sessionID like CONCAT('%', ? ,'%')";
	private final static String RATING_QUERY = "SELECT items.*, rate.avgRating FROM items join (select itemid, avg(rating) as avgRating from ratings group by itemid) rate on items.id = rate.itemid";
	private final static String TOKEN = "oJ9Cl2ks7SWGOMmXSJ6bt3tIH4DsdLkt5LObtrPm";
	private final static double SAME_T_M = 0.2;
	private final static double DIFF_T_M = 0.4;
	private final static double SAME_T_DIFF_M = 0.1;
	private final static double DIFF_T_SAME_M = 0.7;
	private static final double NUMBER_OF_COMPARATIVE_CRITERIA = 5;
	private static final double SIMILARITY_THRESHOLD = 0.7;

	public DBManager() throws SQLException {
		// dbconnection = new DBConnection();
	}

	public Item getItem(int id) {
		Item tempItem = null;
		try {
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(
					"SELECT items.*, rate.avgRating FROM items join (select itemid, avg(rating) as avgRating from ratings where itemid = ? group by itemid) rate on items.id = rate.itemid");
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
						getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(8)));
			}
			close();
			if (tempItem == null) {
				return new Item();
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return tempItem;
	}

	public int getItemRating(int id) {
		double rating = 0;
		try {
			reconnect();
			pstmt = dbconnection.getConnection()
					.prepareStatement("select avg(rating) from ratings where itemid = ? group by itemid");
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				rating = resultSet.getDouble(1);
			}
			close();
		} catch (SQLException e) {
			e.getMessage();
		}
		return (int) (rating + 0.5);
	}

	public List<Item> getAllItems() {
		try {
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(
					"SELECT items.*, rate.avgRating FROM items join (select itemid, avg(rating) as avgRating from ratings group by itemid) rate on items.id = rate.itemid");
		} catch (SQLException e) {
			e.getMessage();
		}
		return getQueryResults();
	}

	public List<Item> getItems(int itemFromId, int itemToId) {
		try {
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(
					"SELECT items.*, rate.avgRating FROM items join (select itemid, avg(rating) as avgRating from ratings where itemid >= ? AND itemid <= ? group by itemid) rate on items.id = rate.itemid");
			pstmt.setInt(1, itemFromId);
			pstmt.setInt(2, itemToId);
		} catch (SQLException e) {
			e.getMessage();
		}
		return getQueryResults();
	}

	public List<Item> getItemsBy(String getItemsBy, String params) {
		return getItemsBy(getItemsBy, params, 0, 0, "");
	}

	public List<Item> getItemsBy(String getItemsBy, String params, double priceMin, double priceMax, String sort) {
		try {
			reconnect();
			String[] typesArray = params.split("/");
			int length = typesArray.length;
			int counter = 0;
			String query = RATING_QUERY + " where ( items." + getItemsBy + " LIKE  CONCAT('%', ? ,'%')";
			String tempQuery = "";
			if (length > 1) {
				for (int i = 1; i < typesArray.length; i++) {
					tempQuery = tempQuery + " OR " + getItemsBy + " LIKE  CONCAT('%', ? ,'%')";
				}
			}
			query = query + tempQuery + " )";
			if (priceMin > 0) {
				query = query + " AND Price >= ?";
			}
			if (priceMax > 0) {
				query = query + " AND Price <= ?";
			}
			if (sort.equalsIgnoreCase("ASC")) {
				query = query + " ORDER BY Price ASC";
			} else if (sort.equalsIgnoreCase("DESC")) {
				query = query + " ORDER BY Price DESC";
			}
			pstmt = dbconnection.getConnection().prepareStatement(query);
			if (length > 1) {
				for (int i = 0; i < length; i++) {
					pstmt.setString(++counter, typesArray[i]);
				}
			} else {
				pstmt.setString(++counter, params);
			}
			if (priceMin > 0) {
				pstmt.setDouble(++counter, priceMin);
			}
			if (priceMax > 0) {
				pstmt.setDouble(++counter, priceMax);
			}
			// System.out.println(query);
		} catch (SQLException e) {
			e.getMessage();
		}
		return getQueryResults();
	}

	public List<Item> getItemsByRating(String sort, String getItemsBy, String params) {
		try {
			reconnect();
			String[] typesArray = params.split("/");
			int length = typesArray.length;
			int counter = 0;
			String query = RATING_QUERY;
			if (getItemsBy.length() > 0) {
				String tempQuery = " where ( items." + getItemsBy + " LIKE  CONCAT('%', ? ,'%')";

				if (length > 1) {
					for (int i = 1; i < typesArray.length; i++) {
						tempQuery = tempQuery + " OR " + getItemsBy + " LIKE  CONCAT('%', ? ,'%')";
					}
				}
				query = query + tempQuery + " )";
			}
			query = query + " ORDER BY  rate.avgRating ";
			if (sort.equalsIgnoreCase("DESC")) {
				query = query + sort;
			}
			pstmt = dbconnection.getConnection().prepareStatement(query);
			if (getItemsBy.length() > 0) {
				if (length > 1) {
					for (int i = 0; i < length; i++) {
						pstmt.setString(++counter, typesArray[i]);
					}
				} else {
					pstmt.setString(++counter, params);
				}
			}
			// System.out.println(query);
		} catch (SQLException e) {
			e.getMessage();
		}
		return getQueryResults();
	}

	public List<Item> getItemsByPrice(double priceMin, double priceMax) {
		try {
			reconnect();
			pstmt = dbconnection.getConnection()
					.prepareStatement(RATING_QUERY + " where items.Price >= ? AND items.Price <= ?");
			pstmt.setDouble(1, priceMin);
			pstmt.setDouble(2, priceMax);
		} catch (SQLException e) {
			e.getMessage();
		}
		return getQueryResults();
	}

	private String[] getCategories(String s) {
		return s.split("\\|");
	}

	public List<Item> getRecommendation(int itemId, int size, String recommendationType, String recommendationLevel) {
		try {
			String rec_Type = "";
			if (recommendationType.equalsIgnoreCase("sim")) {
				rec_Type = " and it.item_type like it2.item_type";
			} else if (recommendationType.equalsIgnoreCase("comp")) {
				rec_Type = " and it.item_type not like it2.item_type";
				String[] categories = getItem(itemId).getCategory();
				String categoriesQuerty = " and (";
				for (int i = 0; i < categories.length; i++) {
					categoriesQuerty += " it.category like CONCAT('%','" + categories[i] + "','%') ";
					if (i < categories.length - 1) {
						categoriesQuerty += "or ";
					}
				}
				categoriesQuerty += ") ";
				if (categories.length > 0) {
					rec_Type += categoriesQuerty;
				}
			}
			String rec_Level = "";
			if (recommendationLevel.equalsIgnoreCase("low")) {
				rec_Level = " and it.manufacturer not like it2.manufacturer";
			} else if (recommendationLevel.equalsIgnoreCase("high")) {
				rec_Level = " and it.manufacturer like it2.manufacturer";
			}
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(
					"select it.* from sql7264905.dependencies dep join sql7264905.items it on dep.id2 = it.id "
							+ "join sql7264905.items it2 on dep.id1 = it2.id where dep.id1 = ?" + rec_Type + rec_Level
							+ " order by dep.rating desc limit ?");
			pstmt.setInt(1, itemId);
			pstmt.setInt(2, size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getRecQueryResults();
	}

	public List<Item> getRandomRecommendation(int itemId, int size, String recommendationType,
			String recommendationLevel) {
		List<Item> tempItems = getRecommendation(itemId, 5000, recommendationType, recommendationLevel);
		Collections.shuffle(tempItems);

		if (size > tempItems.size()) {
			return tempItems;
		}

		return tempItems.subList(0, size);
	}

	private List<Item> getQueryResults() {
		List<Item> tempItems = new ArrayList<>();
		try {
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				tempItems.add(new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
						getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(8))));
			}
			close();
			if (tempItems.size() < 1)
				tempItems.add(new Item());
		} catch (SQLException e) {
			e.getMessage();
		}
		return tempItems;
	}

	private List<Item> getRecQueryResults() {
		List<Item> tempItems = new ArrayList<>();
		try {
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				tempItems.add(new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
						getCategories(resultSet.getString(7))));
			}
			close();
			if (tempItems.size() < 1)
				tempItems.add(new Item());
		} catch (SQLException e) {
			// System.out.println("getRecQueryResults error");
			e.getMessage();
		}
		return tempItems;
	}

	public String updateDependencies(int itemFromId, int itemToId, String token) {
		if (token.equals(TOKEN)) {
			Item item1 = getItem(itemFromId);
			Item item2 = getItem(itemToId);
			double value = 0.0;
			if (item1.getManufacturer().equals(item2.getManufacturer())) {
				if (item1.getType().equals(item2.getType())) {
					value = SAME_T_M;
				} else {
					value = DIFF_T_SAME_M;
				}
			} else {
				if (item1.getType().equals(item2.getType())) {
					value = SAME_T_DIFF_M;
				} else {
					value = DIFF_T_M;
				}
			}
			reconnect();
			String query = "UPDATE sql7264905.dependencies SET rating = rating+" + value
					+ " WHERE (ID1 = ? AND ID2 = ?) OR (ID2 = ? AND ID1 = ?)";
			try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
				stmt.setInt(1, itemFromId);
				stmt.setInt(2, itemToId);
				stmt.setInt(3, itemFromId);
				stmt.setInt(4, itemToId);
				stmt.executeUpdate();
				dbconnection.close();
				return "update successful";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "update failed";
	}

	@SuppressWarnings("unused")
	public String getInfo(String info) {
		String tempItemTypes = "";
		if (info.equals("manufacturer") || info.equals("item_type") || info.equals("category")) {
			try {
				reconnect();
				String query = "select distinct " + info + " from items";
				pstmt = dbconnection.getConnection().prepareStatement(query);
				// pstmt.setString(1, infoName);
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) {
					String resultString = resultSet.getString(1);
					if (info.equals("category")) {
						String[] resultArray = resultString.split("\\|");
						for (int i = 0; i < resultArray.length; i++) {
							if (!tempItemTypes.contains(resultArray[i])) {
								tempItemTypes += resultArray[i] + "\n";
							}
						}
					} else {
						tempItemTypes += resultString + "\n";
					}
				}
				close();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
		return tempItemTypes;
	}

	public String updateDependencies(int itemFromId, int itemToId, Token token) {
		if (token.getTokenString().equals(TOKEN)) {
			Item item1 = getItem(itemFromId);
			Item item2 = getItem(itemToId);
			double value = 0.0;
			if (item1.getManufacturer().equals(item2.getManufacturer())) {
				if (item1.getType().equals(item2.getType())) {
					value = SAME_T_M;
				} else {
					value = DIFF_T_SAME_M;
				}
			} else {
				if (item1.getType().equals(item2.getType())) {
					value = SAME_T_DIFF_M;
				} else {
					value = DIFF_T_M;
				}
			}
			reconnect();
			String query = "UPDATE sql7264905.dependencies SET rating = rating+" + value
					+ " WHERE (ID1 = ? AND ID2 = ?) OR (ID2 = ? AND ID1 = ?)";
			try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
				stmt.setInt(1, itemFromId);
				stmt.setInt(2, itemToId);
				stmt.setInt(3, itemFromId);
				stmt.setInt(4, itemToId);
				stmt.executeUpdate();
				dbconnection.close();
				return "update successful";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "update failed";
	}

	public User setUser(String sessionID, String token) {
		if (token.equals(TOKEN)) {
			reconnect();
			try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(USER_QUERY)) {
				stmt.setString(1, sessionID);
				resultSet = stmt.executeQuery();
				// user doesn't exist
				if (!resultSet.next()) {
					// System.out.println(sessionID);
					String insertQuery = "insert into sql7264905.users (sessionID) values (?)";
					try (PreparedStatement stmt2 = dbconnection.getConnection().prepareStatement(insertQuery)) {
						stmt2.setString(1, sessionID);
						stmt2.executeUpdate();
					}
				}
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return getUser(sessionID);
	}

	public String updateUsername(String sessionID, String username, String token) {
		if (token.equals(TOKEN)) {
			reconnect();
			String query = "UPDATE sql7264905.users SET username = ? WHERE sessionID = ?";
			try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
				stmt.setString(1, username);
				stmt.setString(2, sessionID);
				stmt.executeUpdate();
				close();
				return "update successful";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "update failed";
	}

	public String updateRating(String sessionID, int itemID, int rating, String token) {
		if (token.equals(TOKEN)) {
			if (rating < 1 || rating > 5) {
				return "rating not allowed";
			}
			Item tempItem = getItem(itemID);
			if (tempItem.getId() < 1) {
				return "item doesn't exist";
			}
			User tempUser = getUser(sessionID);
			if (tempUser.getId() < 1) {
				return "user doesn't exist";
			}
			if (isRated(tempUser.getId(), itemID)) {
				return "item was already rated by this user";
			}
			reconnect();
			String query = "insert into sql7264905.ratings (userID, itemID, rating) values (?, ?, ?)";
			try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
				stmt.setInt(1, tempUser.getId());
				stmt.setInt(2, itemID);
				stmt.setInt(3, rating);
				stmt.executeUpdate();
				close();
				return "update successful";
			} catch (SQLException e) {
				e.printStackTrace();
				return "update failed";
			}
		}
		return "wrong token";
	}

	private boolean isRated(int userID, int itemID) {
		reconnect();
		boolean result = false;
		String query = "select * from ratings where userid = ? and itemid = ?";
		try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
			stmt.setInt(1, userID);
			stmt.setInt(2, itemID);
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private User getUser(String sessionID) {
		User tempUser = null;
		reconnect();
		try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(USER_QUERY)) {
			stmt.setString(1, sessionID);
			resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				tempUser = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
			} else {
				tempUser = new User();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempUser;
	}

	public List<Item> contentBasedRecommendation(int itemID, int size) {
		List<Item> tempItemsList = getAllItems();
		Item tempItem = getItem(itemID);
		if (tempItem.getId() < 1) {
			// return an empty list if item doesn't exists
			tempItemsList.add(new Item());
			return tempItemsList;
		}
		
		Map<Integer, Double> similarityMap = new HashMap<>();
		for (Item t : tempItemsList) {
			similarityMap.put(t.getId(), similarity(tempItem, t));
		}

		// for(int i : similarityMap.keySet()) {System.out.println(i+"
		// "+similarityMap.get(i));}
		Set<Integer> sortedIDs = sortByValue(similarityMap, false).keySet();
		tempItemsList = new ArrayList<>();
		int counter = 0;
		for (int i : sortedIDs) {
			if (i != itemID) {
				tempItemsList.add(getItem(i));
				counter++;
				if (counter >= size) {
					break;
				}
			}
		}
		// delete the first element
		// tempItemsList.remove(0);
		//for (Item t : tempItemsList) {System.out.println(t);}
		return tempItemsList;
	}

	private Map<Integer, Integer> sortByIntegerValue(Map<Integer, Integer> unsortedMap, boolean asc) {
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(new ValueComparatorInteger(unsortedMap, asc));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	
	private Map<Integer, Double> sortByValue(Map<Integer, Double> unsortedMap, boolean asc) {
		Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(new ValueComparator(unsortedMap, asc));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}

	private double similarity(Item firstItem, Item otherItem) {
		int typeSimilarity = getStringSimilarity(firstItem.getType(), otherItem.getType());
		int manufacturerSimilarity = getStringSimilarity(firstItem.getManufacturer(), otherItem.getManufacturer());
		int categorySimilarity = getCategorySimilarity(firstItem.getCategory(), otherItem.getCategory());
		double priceSimilarity = getNumbersSimilarity(firstItem.getPrice(), otherItem.getPrice());
		double ratingSimilarity = getNumbersSimilarity(firstItem.getRating(), otherItem.getRating());

		return (typeSimilarity + manufacturerSimilarity + categorySimilarity + priceSimilarity + ratingSimilarity)
				/ NUMBER_OF_COMPARATIVE_CRITERIA;
	}

	private double getNumbersSimilarity(double firstNumber, double otherNumber) {
		if (firstNumber > otherNumber) {
			return otherNumber / firstNumber;
		}
		return firstNumber / otherNumber;
	}

	private int getCategorySimilarity(String[] firstCategory, String[] otherCategory) {
		for (int i = 0; i < firstCategory.length; i++) {
			for (int j = 0; j < otherCategory.length; j++) {
				// similar category found
				if (firstCategory[i].equals(otherCategory[j])) {
					return 1;
				}
			}
		}
		return 0;
	}

	private int getStringSimilarity(String firstString, String otherString) {
		if (firstString.equals(otherString)) {
			return 1;
		}
		return 0;
	}

	public List<Item> userBasedCollaborativeFiltering(String sessionID, int itemID, int size) {
		List<Item> tempItemsList = new ArrayList<>();
		User tempUser = getUser(sessionID);
		int tempUserID = tempUser.getId();
		if (tempUserID < 1) {
			// return an empty list if user doesn't exists
			tempItemsList.add(new Item());
			return tempItemsList;
		}
		List<Integer> usersIDs = getUsersRatedItem(itemID);
		List<Map<Integer, Integer>> userNeighbourhoodRatings = new ArrayList<>();

		Map<Integer, Map<Integer, Integer>> usersRatings;

		// check if usersIDs is not empty
		if (usersIDs.size() > 0) {
			usersRatings = getUsersRatings(usersIDs);
			Map<Integer, Integer> userRatings = getUserRatings(tempUserID);
			
			for (int userID : usersRatings.keySet()) {
				if (userID != tempUserID) {
					double similarity = calculateUserSimilarity(userRatings, usersRatings.get(userID));
					if (similarity > SIMILARITY_THRESHOLD) {
						userNeighbourhoodRatings.add(usersRatings.get(userID));
					}
				}
			}

			if (userNeighbourhoodRatings.size() < 1) {
				// return an empty list if user neighbourhood is empty
				tempItemsList.add(new Item());
				return tempItemsList;
			}

			Map<Integer, Integer> resultItemsRatings = new HashMap<>();
			for (Map<Integer, Integer> r : userNeighbourhoodRatings) {
				for (int id : r.keySet()) {
					int oldValue = 0;
					if (resultItemsRatings.containsKey(id)) {
						oldValue = resultItemsRatings.get(id);
					}
					resultItemsRatings.put(id, oldValue + r.get(id));
				}
			}
			//for (int y : resultItemsRatings.keySet()) {System.out.println("id " + y + " rating sum " + resultItemsRatings.get(y));}
			
			Set<Integer> sortedItemsIDs = sortByIntegerValue(resultItemsRatings, false).keySet();

			if (sortedItemsIDs.size() < size) {
				size = sortedItemsIDs.size();
			}

			int counter = 0;
			for (int i : sortedItemsIDs) {
				if (i != itemID) {
					tempItemsList.add(getItem(i));
					counter++;
					if (counter >= size) {
						break;
					}
				}
			}
			// delete the first element
			// tempItemsList.remove(0);
			//for (Item t : tempItemsList) {System.out.println(t);}
		}
		return tempItemsList;
	}

	private double calculateUserSimilarity(Map<Integer, Integer> userRatings, Map<Integer, Integer> otherUserRatings ) {
		int commonItems = 0;
		double differenceSum = 0;
		for (int firstUserRatedMovie : userRatings.keySet()) {
			for (int otherUserRating : otherUserRatings.keySet()) {
				// if movies similar so calculate the similarity
				if (firstUserRatedMovie == otherUserRating) {
					commonItems++;
					differenceSum += getNumbersSimilarity(userRatings.get(firstUserRatedMovie),
							otherUserRatings.get(otherUserRating));
				}
			}
		}
		// avoid division by zero
		if (commonItems == 0) {
			return 0;
		}
		return differenceSum / commonItems;
	}

	private Map<Integer, Integer> getUserRatings(int userID) {
		Map<Integer, Integer> ratingsMap = new HashMap<>();
		reconnect();
		String query = "select itemid, rating from ratings where userid = ?";
		try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
			stmt.setInt(1, userID);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				ratingsMap.put(resultSet.getInt(1), resultSet.getInt(2));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ratingsMap;
	}

	private Map<Integer, Map<Integer, Integer>> getUsersRatings(List<Integer> usersIDs) {
		Map<Integer, Map<Integer, Integer>> usersRatings = new HashMap<>();
		reconnect();
		String query = "select userid, itemid, rating from ratings where userid = ?";
		if(usersIDs.size() > 1) {
			for(int i = 1; i < usersIDs.size(); i++) {
				query = query + " or  userid = ?";
			}
		}
		query = query + " order by userid";
		//System.out.println(query);
		try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
			for(int i = 0; i < usersIDs.size(); i++) {
				stmt.setInt(i+1, usersIDs.get(i));
			}
			resultSet = stmt.executeQuery();
			int currentUserID = 0;
			Map<Integer, Integer> tempRatingsMap = new HashMap<>();
			while (resultSet.next()) {
				// user id changed
				int nextID = resultSet.getInt(1);
				if(currentUserID != nextID) {
					if (currentUserID > 0) {
						usersRatings.put(currentUserID, tempRatingsMap);
						tempRatingsMap = new HashMap<>();
					}
					currentUserID = nextID;
				}
				tempRatingsMap.put(resultSet.getInt(2), resultSet.getInt(3));
			}
			// add last user
			if(!usersRatings.containsKey(currentUserID)) {
				usersRatings.put(currentUserID, tempRatingsMap);
				//System.out.println("last user added");
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersRatings;
	}

	private List<Integer> getUsersRatedItem(int itemID) {
		List<Integer> integerList = new ArrayList<>();
		reconnect();
		String query = "select userid from ratings where itemid = ?";
		try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(query)) {
			stmt.setInt(1, itemID);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				integerList.add(resultSet.getInt(1));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return integerList;
	}

	private void reconnect() {
		if (dbconnection == null) {
			try {
				dbconnection = new DBConnection();
				// System.out.println("Connection created");
			} catch (SQLException e) {
				// System.out.println("Connection is null");
				e.printStackTrace();
			}
		} else {
			// System.out.println("Connection is not null");
			close();
			try {
				if (dbconnection.getConnection().isClosed()) {
					try {
						dbconnection = new DBConnection();
						// System.out.println("Reconnected");
					} catch (SQLException e) {
						// System.out.println("Reconnection failed!");
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// System.out.println("Connection failed!");
				e.printStackTrace();
			}
		}
	}

	private void close() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (pstmt != null)
				pstmt.close();
			dbconnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public SpecificItem getSpecificItemm(int id) {
		Item tempItem = getItem(id);
		JSONObject jsonObj = null;
		SpecificItem specItem = null;
		String itemType = tempItem.getType();
		String itemQuery = DB_QUERY + " join " + itemType + "_p on items.id = " + itemType
				+ "_p.id where items.id = ? ";
		// System.out.println("query " + itemQuery);

		try {
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(itemQuery);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();

			while (resultSet.next()) {
				jsonObj = new JSONObject();
				// System.out.println(rsmd.getColumnCount()+" "+jsonObj.toJSONString());

				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					// System.out.println(rsmd.getColumnName(i));
				}
				// System.out.println(rsmd.getColumnCount()+" "+jsonObj.toJSONString());
				specItem = new SpecificItem(tempItem, jsonObj);
			}
			close();
		} catch (SQLException e) {
			e.getMessage();
		}

		return specItem;
	}

	public String getSpecificItem(int id) {
		Item tempItem = getItem(id);
		JSONObject jsonObj = null;
		String itemType = tempItem.getType();
		String itemQuery = DB_QUERY + " join " + itemType + "_p on items.id = " + itemType
				+ "_p.id where items.id = ? ";
		// System.out.println("query " + itemQuery);

		try {
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(itemQuery);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				if (itemType.equalsIgnoreCase("cable")) {
					jsonObj = new Cable(tempItem, resultSet.getInt(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("camera")) {
					jsonObj = new Camera(tempItem, resultSet.getDouble(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("charger")) {
					jsonObj = new Charger(tempItem, resultSet.getString(9)).getJSON();

				} else if (itemType.equalsIgnoreCase("console")) {
					jsonObj = new Console(tempItem, resultSet.getString(9), resultSet.getInt(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("controller")) {
					jsonObj = new Controller(tempItem, resultSet.getString(9)).getJSON();

				} else if (itemType.equalsIgnoreCase("desktop")) {
					jsonObj = new Desktop(tempItem, resultSet.getString(9), resultSet.getInt(10), resultSet.getInt(11))
							.getJSON();

				} else if (itemType.equalsIgnoreCase("drive")) {
					jsonObj = new Drive(tempItem, resultSet.getInt(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("dvd_br")) {
					jsonObj = new Dvd_Br(tempItem, resultSet.getString(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("game")) {
					jsonObj = new Game(tempItem, resultSet.getString(9)).getJSON();

				} else if (itemType.equalsIgnoreCase("headset")) {
					jsonObj = new Headset(tempItem, resultSet.getString(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("keyboard")) {
					jsonObj = new Keyboard(tempItem, resultSet.getString(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("lens")) {
					jsonObj = new Lens(tempItem, resultSet.getString(9)).getJSON();

				} else if (itemType.equalsIgnoreCase("mobile")) {
					jsonObj = new Mobile(tempItem, resultSet.getDouble(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("monitor")) {
					jsonObj = new Monitor(tempItem, resultSet.getDouble(9), resultSet.getInt(10), resultSet.getInt(11))
							.getJSON();

				} else if (itemType.equalsIgnoreCase("mouse")) {
					jsonObj = new Mouse(tempItem, resultSet.getInt(9), resultSet.getString(10)).getJSON();

				} else if (itemType.equalsIgnoreCase("notebook_case")) {
					jsonObj = new NotebookCase(tempItem, resultSet.getDouble(9)).getJSON();

				} else if (itemType.equalsIgnoreCase("notebook")) {
					jsonObj = new Notebook(tempItem, resultSet.getDouble(9), resultSet.getString(10),
							resultSet.getInt(11), resultSet.getInt(12), resultSet.getString(13)).getJSON();
					// System.out.println(jsonObj.toJSONString());

				} else if (itemType.equalsIgnoreCase("printer")) {
					jsonObj = new Printer(tempItem, resultSet.getString(9), resultSet.getString(10),
							resultSet.getString(11)).getJSON();

				} else if (itemType.equalsIgnoreCase("speaker")) {
					jsonObj = new Speaker(tempItem, resultSet.getString(9)).getJSON();

				} else if (itemType.equalsIgnoreCase("tablet")) {
					jsonObj = new Tablet(tempItem, resultSet.getDouble(9), resultSet.getInt(10),
							resultSet.getString(11)).getJSON();

				} else if (itemType.equalsIgnoreCase("television")) {
					jsonObj = new Television(tempItem, resultSet.getDouble(9)).getJSON();
				}
			}
			close();
		} catch (SQLException e) {
			e.getMessage();
		}
		return jsonObj.toJSONString();
	}

	public String getSpecificItemByType(String itemtype) {
		List<String> items = new ArrayList<>();
		String itemQuery = "SELECT items.*, " + itemtype
				+ "_p.* ,rate.avgRating FROM items join (select itemid, avg(rating) as avgRating from ratings group by itemid) rate on items.id = rate.itemid join "
				+ itemtype + "_p on items.id = " + itemtype + "_p.id";
		try {
			reconnect();
			pstmt = dbconnection.getConnection().prepareStatement(itemQuery);
			resultSet = pstmt.executeQuery();
			int columnNr = resultSet.getMetaData().getColumnCount();

			if (itemtype.equalsIgnoreCase("cable")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(
							new Cable(tempItem, resultSet.getInt(9), resultSet.getString(10)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("camera")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Camera(tempItem, resultSet.getDouble(9), resultSet.getString(10)).getJSON()
							.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("charger")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Charger(tempItem, resultSet.getString(9)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("console")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Console(tempItem, resultSet.getString(9), resultSet.getInt(10)).getJSON()
							.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("controller")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Controller(tempItem, resultSet.getString(9)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("desktop")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Desktop(tempItem, resultSet.getString(9), resultSet.getInt(10), resultSet.getInt(11))
							.getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("drive")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(
							new Drive(tempItem, resultSet.getInt(9), resultSet.getString(10)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("dvd_br")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Dvd_Br(tempItem, resultSet.getString(9), resultSet.getString(10)).getJSON()
							.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("game")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Game(tempItem, resultSet.getString(9)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("headset")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Headset(tempItem, resultSet.getString(9), resultSet.getString(10)).getJSON()
							.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("keyboard")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Keyboard(tempItem, resultSet.getString(9), resultSet.getString(10)).getJSON()
							.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("lens")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Lens(tempItem, resultSet.getString(9)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("mobile")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Mobile(tempItem, resultSet.getDouble(9), resultSet.getString(10)).getJSON()
							.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("monitor")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Monitor(tempItem, resultSet.getDouble(9), resultSet.getInt(10), resultSet.getInt(11))
							.getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("mouse")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(
							new Mouse(tempItem, resultSet.getInt(9), resultSet.getString(10)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("notebook_case")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new NotebookCase(tempItem, resultSet.getDouble(9)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("notebook")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Notebook(tempItem, resultSet.getDouble(9), resultSet.getString(10),
							resultSet.getInt(11), resultSet.getInt(12), resultSet.getString(13)).getJSON()
									.toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("printer")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Printer(tempItem, resultSet.getString(9), resultSet.getString(10),
							resultSet.getString(11)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("speaker")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Speaker(tempItem, resultSet.getString(9)).getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("tablet")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(
							new Tablet(tempItem, resultSet.getDouble(9), resultSet.getInt(10), resultSet.getString(11))
									.getJSON().toJSONString());
				}

			} else if (itemtype.equalsIgnoreCase("television")) {
				while (resultSet.next()) {
					Item tempItem = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getDouble(5), resultSet.getString(6),
							getCategories(resultSet.getString(7)), (int) Math.round(resultSet.getDouble(columnNr)));
					items.add(new Television(tempItem, resultSet.getDouble(9)).getJSON().toJSONString());
				}
			}
			close();
		} catch (

		SQLException e) {
			e.getMessage();
		}
		return items.toString();
	}

}
