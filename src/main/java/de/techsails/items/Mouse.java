package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Mouse extends Item{

	private int dpi;
	private String connection;
	
	public Mouse() {
		super();
	}

	public Mouse(Item item, int dpi, String connection) {
		super(item);
		this.dpi=dpi;
		this.connection=connection;
	}

	public int getDpi() {
		return dpi;
	}

	public void setDpi(int dpi) {
		this.dpi = dpi;
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
		jsonObj.put("dpi", dpi);
		jsonObj.put("connection", connection);

		return jsonObj;
	}
}
