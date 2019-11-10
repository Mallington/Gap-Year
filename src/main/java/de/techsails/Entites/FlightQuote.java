package de.techsails.Entites;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FlightQuote extends Flight{
	private String departureTime;
	private int minCost;
	
	public FlightQuote() {
	}

	public FlightQuote(String departureLocation, String arrivalLocation, String departureTime, int cost) {
		super(departureLocation, arrivalLocation);
		this.departureTime = departureTime;
		minCost = cost;
	}
	
	public String getDepartureTime() {
		return this.departureTime;
	}
	public int getMinCost() {
		return this.minCost;
	}
	
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public void setMinCost(int minCost) {
		this.minCost = minCost;
	}

	@Override
	public String toString() {
		return String.format("Depart: %s\nArrive:%s\nCost:%s", super.getDeparture(), super.getDestination(), minCost);
	}
}
