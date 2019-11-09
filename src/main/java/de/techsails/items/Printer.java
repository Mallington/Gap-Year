package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Printer extends Item{

	private String property;
	private String connection;
	private String printingTech;
	
	public Printer() {
		super();
	}

	public Printer(Item item, String property, String connection, String printingTech) {
		super(item);
		this.property=property;
		this.connection=connection;
		this.printingTech=printingTech;
	}
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getConnection() {
		return connection;
	}
	
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	public String getPrintingTech() {
		return printingTech;
	}
	
	public void setPrintingTech(String printingTech) {
		this.printingTech = printingTech;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonO = super.getJSON();
		jsonO.put("property", property);
		jsonO.put("connection", connection);
		jsonO.put("printingTech", printingTech);

		return jsonO;
	}
}
