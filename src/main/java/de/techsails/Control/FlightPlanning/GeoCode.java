package de.techsails.Control.FlightPlanning;

public class GeoCode{
	public double longitude;
	public double latitude;
	
	public GeoCode(double lat, double lon) {
		longitude = lon;
		latitude = lat;
	}
	
	public Double getDistance(GeoCode code) {
		return Math.sqrt(Math.pow(longitude-code.longitude,2) + Math.pow(latitude-code.latitude,2));
	}
	
	@Override
	public String toString() {
		return "(" + latitude + "," + longitude + ")";
	}
}
