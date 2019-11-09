package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Dvd_Br extends Item{

	private String playerType;
	private String resolution;
	
	public Dvd_Br() {
		super();
	}

	public Dvd_Br(Item item, String playerType, String resolution) {
		super(item);
		this.playerType=playerType;
		this.resolution=resolution;
	}

	public String getPlayerType() {
		return playerType;
	}

	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("playerType", playerType);
		jsonObj.put("resolution", resolution);

		return jsonObj;
	}
}
