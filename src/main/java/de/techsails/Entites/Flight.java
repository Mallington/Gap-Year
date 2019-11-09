package de.techsails.Entites;

public class Flight {

	private int id;
	private String departure;
	private String destination;
	
	public Flight(int id, String departure, String destination) {
		this.id = id;
		this.departure = departure;
		this.destination = destination;
	}
	
	public int getId() {
		return id;
	}
	public String getDeparture() {
		return departure;
	}
	public String getDestination() {
		return destination;
	}
	
	
}
