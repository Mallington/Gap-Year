package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Charger extends Item{

	private String compatibleBrand;
	
	public Charger() {
		super();
	}

	public Charger(Item item, String compatibleBrand) {
		super(item);
		this.compatibleBrand=compatibleBrand;
	}

	public String getCompatibleBrand() {
		return compatibleBrand;
	}

	public void setCompatibleBrand(String compatibleBrand) {
		this.compatibleBrand = compatibleBrand;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("compatibleBrand", compatibleBrand);

		return jsonObj;
	}
}
