package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Mobile extends Item{

	private double size;
	private String operatingSystem;
	
	public Mobile() {
		super();
	}

	public Mobile(Item item, double size, String operatingSystem) {
		super(item);
		this.size=size;
		this.operatingSystem=operatingSystem;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("size", size);
		jsonObj.put("operatingSystem", operatingSystem);

		return jsonObj;
	}
}
