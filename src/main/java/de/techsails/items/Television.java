package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Television extends Item{

	private double size;
	
	public Television() {
		super();
	}
	
	public Television(Item item, double size) {
		super(item);
		this.size=size;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("size", size);

		return jsonObj;
	}
}
