/**
 *Item represents an item in the database
 *
 *@author yousuf.amanuel
 *@version 22.November.2018
 *
 */
package de.techsails;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

@XmlRootElement
public class Item {
	public static final double PRICE_MIN = -1;
	public static final double PRICE_MAX = 100000;

	private int id;
	private String imgSrc;
	private String name;
	private String manufacturer;
	private double price;
	private String type;
	private String[] category;
	private int rating;
	// private String description;

	// empty constructor to produce (MediaType.APPLICATION_JSON)
	public Item() {

	}

	public Item(Item item) {
		this.id = item.id;
		this.imgSrc = item.imgSrc;
		this.name = item.name;
		this.manufacturer = item.manufacturer;
		this.price = item.price;
		this.type = item.type;
		this.category = item.category;
		this.rating = item.rating;
	}

	public Item(int id, String imgSrc, String name, String manufacturer, double price, String type, String[] category, int rating) {
		this.id = id;
		this.imgSrc = imgSrc;
		this.name = name;
		this.manufacturer = manufacturer;
		this.price = price;
		this.type = type;
		this.category = category;
		this.rating = rating;
	}
	
	public Item(int id, String imgSrc, String name, String manufacturer, double price, String type, String[] category) {
		this.id = id;
		this.imgSrc = imgSrc;
		this.name = name;
		this.manufacturer = manufacturer;
		this.price = price;
		this.type = type;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manfacturer) {
		this.manufacturer = manfacturer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return id + " " + imgSrc + " " + name + " " + manufacturer + " " + price + " " + type + " " + Arrays.toString(category) + " " + rating;
	}

	public String getInfo(String info) {
		if (info.equalsIgnoreCase("name"))
			return name;
		else if (info.equalsIgnoreCase("type"))
			return type;
		else if (info.equalsIgnoreCase("manufacturer"))
			return manufacturer;
		else if (info.equalsIgnoreCase("category"))
			return Arrays.toString(category);
		return null;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("ID", id);
		jsonObj.put("imgSrc", imgSrc);
		jsonObj.put("name", name);
		jsonObj.put("manufacturer", manufacturer);
		jsonObj.put("price", price);
		jsonObj.put("type", type);
		jsonObj.put("category", Arrays.toString(category));
		jsonObj.put("rating", rating);

		return jsonObj;
	}

}
