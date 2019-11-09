package de.techsails;
import org.json.simple.JSONObject;

import de.techsails.Item;

public class SpecificItem extends Item{

	private JSONObject properties;
	
	public SpecificItem() {
		super();
	}

	public SpecificItem(Item item, JSONObject properties) {
		super(item);
		this.properties=properties;
	}

	public JSONObject getProperties() {
		return properties;
	}

	public void setProperties(JSONObject properties) {
		this.properties = properties;
	}
	
}
