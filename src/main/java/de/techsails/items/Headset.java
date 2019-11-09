package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Headset extends Item{

	private String gaming;
	private String connection;
	
	public Headset() {
		super();
	}

	public Headset(Item item, String gaming, String connection) {
		super(item);
		this.gaming=gaming;
		this.connection=connection;
	}

	public String getGaming() {
		return gaming;
	}

	public void setGaming(String gaming) {
		this.gaming = gaming;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("gaming", gaming);
		jsonObj.put("connection", connection);

		return jsonObj;
	}
}
