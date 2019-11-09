package de.techsails.items;

import org.json.simple.JSONObject;

import de.techsails.Item;

public class Desktop extends Item{

	private String processor;
	private int ram;
	private int storageSize;
	
	public Desktop() {
		super();
	}

	public Desktop(Item item, String processor, int ram, int storageSize) {
		super(item);
		this.processor=processor;
		this.ram=ram;
		this.storageSize=storageSize;
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
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		JSONObject jsonObj = super.getJSON();
		jsonObj.put("processor", processor);
		jsonObj.put("ram", ram);
		jsonObj.put("storageSize", storageSize);

		return jsonObj;
	}
}
