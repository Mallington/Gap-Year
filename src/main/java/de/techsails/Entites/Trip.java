package de.techsails.Entites;

public class Trip {

	private int id;
	private String name;
	
	public Trip(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Trip(String name) {
		this.id = 0;
		this.name = name;
	}

	
	public Trip() {
	}

	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
