package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Tablet extends Item{

	private double size;
	private int hardDrive;
	private String operatingSystem;
	
	public Tablet() {
		super();
	}

	public Tablet(Item item, double size, int hardDrive, String operatingSystem) {
		super(item);
		this.size=size;
		this.hardDrive=hardDrive;
		this.operatingSystem=operatingSystem;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getHardDrive() {
		return hardDrive;
	}

	public void setHardDrive(int hardDrive) {
		this.hardDrive = hardDrive;
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
		jsonObj.put("hardDrive", hardDrive);
		jsonObj.put("operatingSystem", operatingSystem);

		return jsonObj;
	}
}
