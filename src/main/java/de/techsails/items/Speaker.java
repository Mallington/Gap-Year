package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Speaker extends Item{

	private String connection;
	
	public Speaker() {
		super();
	}

	public Speaker(Item item, String connection) {
		super(item);
		this.connection= connection;
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
		jsonObj.put("connection", connection);

		return jsonObj;
	}
}
