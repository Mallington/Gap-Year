package de.techsails.Entites;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Flight {

	private int id;
	private String departure;
	private String destination;
	
	public Flight() {
		
	}
	
	public Flight(int id, String departure, String destination) {
		this.id = id;
		this.departure = departure;
		this.destination = destination;
	}
	
	public Flight(String departure, String destination) {
		this.id = 0;
		this.departure = departure;
		this.destination = destination;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setDestination(String destination) {
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
