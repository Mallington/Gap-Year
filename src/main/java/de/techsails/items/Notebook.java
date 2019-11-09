package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Notebook extends Item{

	private double size;
	private String processor;
	private int ram;
	private int storageSize;
	private String operatingSystem;
	
	public Notebook() {
		super();
	}

	public Notebook(Item item, double size, String processor, int ram, int storageSize, String operatingSystem) {
		super(item);
		this.size=size;
		this.processor=processor;
		this.ram=ram;
		this.storageSize=storageSize;
		this.operatingSystem=operatingSystem;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(int storageSize) {
		this.storageSize = storageSize;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("size", size);
		jsonObj.put("processor", processor);
		jsonObj.put("ram", ram);
		jsonObj.put("storageSize", storageSize);
		jsonObj.put("operatingSystem", operatingSystem);

		return jsonObj;
	}
	
}
