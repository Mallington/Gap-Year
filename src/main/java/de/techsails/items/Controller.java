package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Controller extends Item{

	private String consoleType;
	
	public Controller() {
		super();
	}

	public Controller(Item item, String consoleType) {
		super(item);
		this.consoleType= consoleType;
	}

	public String getConsoleType() {
		return consoleType;
	}

	public void setConsoleType(String consoleType) {
		this.consoleType = consoleType;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("consoleType", consoleType);

		return jsonObj;
	}
}
