package de.techsails.Control;

import java.util.Date;

public class Flight {
	Date departure;
	Date arrival;
	float cost;
	
	
	public Flight() {
		departure = arrival = null;
		cost = 0;
	}
	
	public Flight(Date dep, Date ar, float cost) {
		departure = dep;
		arrival = ar;
		this.cost = cost;
	}
}
