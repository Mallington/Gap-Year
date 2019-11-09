package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Monitor extends Item{

	private double size;
	private int width;
	private int length;
	
	public Monitor() {
		super();
	}

	public Monitor(Item item, double size, int width, int length) {
		super(item);
		this.size=size;
		this.width=width;
		this.length=length;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("size", size);
		jsonObj.put("width", width);
		jsonObj.put("length", length);

		return jsonObj;
	}
}
