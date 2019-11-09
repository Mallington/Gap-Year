package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Lens extends Item{

	private String focalLength;

	public Lens() {
		super();
	}

	public Lens(Item item, String focalLength) {
		super(item);
		this.focalLength=focalLength;
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
		jsonObj.put("focalLength", focalLength);

		return jsonObj;
	}
}
