package de.techsails.Entites;

import java.util.Date;
import java.util.List;

public class FlightPlan {

	private List<String> countries;
	private String country;
	public String departureDate;
	private int numOfDaysInbetween;

	public FlightPlan() {
	}

	public FlightPlan(List<String> countries, String country, String date, int numOfDaysInbetween) {
		this.countries = countries;
		this.country = country;
		this.departureDate = date;
		this.numOfDaysInbetween = numOfDaysInbetween;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public int getNumOfDaysInbetween() {
		return numOfDaysInbetween;
	}

	public void setNumOfDaysInbetween(int numOfDaysInbetween) {
		this.numOfDaysInbetween = numOfDaysInbetween;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

}
