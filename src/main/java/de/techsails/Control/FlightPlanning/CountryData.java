package de.techsails.Control.FlightPlanning;

public class CountryData {
	public String country;
	public GeoCode geoCode;
	public String name;
	
	public CountryData() {}
	
	public CountryData(String country, double lat, double lon, String name) {
		this.country = country;
		geoCode = new GeoCode(lat, lon);
		this.name = name;
	}
}