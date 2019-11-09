/**
 *DBItemsManager manages the items in the database
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.techsails.Entites.User;

public class DBManager {
	private DBConnection dbconnection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	private final static String USER_QUERY = "select * from gapDB.user where email = ? and pwd = ?";

	public DBManager() throws SQLException {
		dbconnection = new DBConnection();
	}

	/*
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
	}*/

	public User getUser(String email, String pwd) {
		User tempUser = null;
		reconnect();
		try (PreparedStatement stmt = dbconnection.getConnection().prepareStatement(USER_QUERY)) {
			stmt.setString(1, email);
			stmt.setString(2, pwd);
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				tempUser = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempUser;
	}

	public String createUser(User user) {
		User tempUser = getUser(user.getEmail(), user.getPwd());
		String response = "";
		try{
			// user doesn't exist
			if (tempUser == null) {
				String insertQuery = "insert into gapDB.user values (?, ?, ?, ?, ?, ?)";
				try (PreparedStatement stmt2 = dbconnection.getConnection().prepareStatement(insertQuery)) {
					stmt2.setString(1, user.getEmail());
					stmt2.setString(2, user.getName());
					stmt2.setString(3, user.getPwd());
					stmt2.setString(4, user.getPhoneNo());
					stmt2.setString(5, user.getCountry());
					stmt2.setDate(6, user.getBirthDate());
					stmt2.executeUpdate();
				}
				response = "user created";
			}
			else {
				response = "user couldn't be created";
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
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

}
