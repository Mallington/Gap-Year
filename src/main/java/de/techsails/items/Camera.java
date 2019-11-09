package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Camera extends Item{

	private double megapixel;
	private String focalLength;
	
	public Camera() {
		super();
	}

	public Camera(Item item, double megapixel, String focalLength) {
		super(item);
		this.megapixel=megapixel;
		this.focalLength=focalLength;
	}

	public double getMegapixel() {
		return megapixel;
	}

	public void setMegapixel(double megapixel) {
		this.megapixel = megapixel;
	}

	public String getFocalLength() {
		return focalLength;
	}

	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("megapixel", megapixel);
		jsonObj.put("focalLength", focalLength);

		return jsonObj;
	}
}
