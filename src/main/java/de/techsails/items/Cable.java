package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Cable extends Item{

	private int length;
	private String cableType;
	
	public Cable() {
		super();
	}

	public Cable(Item item, int length, String cableType) {
		super(item);
		this.length=length;
		this.cableType=cableType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCableType() {
		return cableType;
	}

	public void setCableType(String cableType) {
		this.cableType = cableType;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("length", length);
		jsonObj.put("cableType", cableType);

		return jsonObj;
	}
}
