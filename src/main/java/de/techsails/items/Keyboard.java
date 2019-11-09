package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Keyboard extends Item{

	private String backlit;
	private String connection;
	
	public Keyboard() {
		super();
	}

	public Keyboard(Item item, String backlit, String connection) {
		super(item);
		this.backlit=backlit;
		this.connection=connection;
	}

	public String getBacklit() {
		return backlit;
	}

	public void setBacklit(String backlit) {
		this.backlit = backlit;
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
		jsonObj.put("backlit", backlit);
		jsonObj.put("connection", connection);

		return jsonObj;
	}
}
