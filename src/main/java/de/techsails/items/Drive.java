package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Drive extends Item{

	private int storageSize;
	private String driveType;
	
	public Drive() {
		super();
	}

	public Drive(Item item, int storageSize, String driveType) {
		super(item);
		this.storageSize=storageSize;
		this.driveType=driveType;
	}

	public int getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(int storageSize) {
		this.storageSize = storageSize;
	}

	public String getDriveType() {
		return driveType;
	}

	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("storageSize", storageSize);
		jsonObj.put("driveType", driveType);

		return jsonObj;
	}
}
