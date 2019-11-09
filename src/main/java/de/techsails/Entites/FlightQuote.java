package de.techsails.Entites;

public class FlightQuote extends Flight{
	private String departureTime;
	private int minCost;
	public FlightQuote(String departureLocation, String arrivalLocation, String departureTime, int cost) {
		super(departureLocation, arrivalLocation);
		this.departureTime = departureTime;
		minCost = cost;
	}
	
	public String getDepartureTime() {
		return this.departureTime;
	}
	public int getMinCost() {
		return minCost;
	}
	
	@Override
	public String toString() {
		return String.format("Depart: %s\nArrive:%s\nCost:%s", super.getDeparture(), super.getDestination(), minCost);
	}
}
