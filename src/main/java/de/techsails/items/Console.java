package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Console extends Item{

	private String consoleType;
	private int storageSize;
	
	public Console() {
		super();
	}

	public Console(Item item, String consoleType, int storageSize) {
		super(item);
		this.consoleType=consoleType;
		this.storageSize=storageSize;
	}

	public String getConsoleType() {
		return consoleType;
	}

	public void setConsoleType(String consoleType) {
		this.consoleType = consoleType;
	}

	public int getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(int storageSize) {
		this.storageSize = storageSize;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("consoleType", consoleType);
		jsonObj.put("storageSize", storageSize);

		return jsonObj;
	}
}
