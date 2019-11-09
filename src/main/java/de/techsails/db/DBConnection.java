/**
 *DBConnection manages the database connection
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection connection;

	public DBConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7264905", "sql7264905", "nrASDVN3MV");
			//System.out.println("----------------Connected!!");

		} catch (Exception e) {
			//System.out.println("----------------Failed!!");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() {
		if(!connection.equals(null)) {
			try {
				connection.close();
				//System.out.println("Connection closed!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
