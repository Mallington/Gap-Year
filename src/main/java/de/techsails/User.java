package de.techsails;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class User {

	private int id;
	private String sessionID;
	private String username;
	
	public User() {
		
	}
	
	public User(int id, String sessionID, String username) {
		this.id = id;
		this.sessionID = sessionID;
		setUsername(username);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null) {
			username = "none";
		}
		this.username = username;
	}
	
	@Override
	public String toString(){
		return id+" "+sessionID+" "+username;
	}
	
}
