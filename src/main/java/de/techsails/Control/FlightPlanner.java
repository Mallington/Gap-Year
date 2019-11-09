package de.techsails.Control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

public class FlightPlanner {
	
	public List<Flight> getFlightPlan(List<String> countries) {
		return null;
	}
	
	public Flight getBestFlight(String country, List<String> countries) {
		HashMap<String, Double> distances = new HashMap<String,Double>();
		ArrayList<GeoCode> countriesGeoCodes = getGeoCodesFromCountryList(countries);
		GeoCode curCountryGeo = getGeoCode(country);
		
		for(int i=0; i<countries.size(); i++) 
			distances.put(countries.get(i),curCountryGeo.getDistance(countriesGeoCodes.get(i)) );
		
		Optional< Entry<String, Double>> relationship = distances.entrySet().stream().max(new Comparator< Entry<String,Double> >() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return o1.getValue() > o2.getValue() ? 1 : -1;
			}
		});
		
		
		
		return null;
	}
	
	public ArrayList<GeoCode> getGeoCodesFromCountryList(List<String> countries) {
		ArrayList<GeoCode> countriesGeoCodes = new ArrayList<>();
		for(String country : countries)
			countriesGeoCodes.add(getGeoCode(country));
		
		return countriesGeoCodes;
	}
	
	public GeoCode getGeoCode(String country) {
		return new GeoCode(0,0);
	}
}


class GeoCode{
	public double longitude;
	public double latitude;
	
	public GeoCode(double lon, double lat) {
		longitude = lon;
		latitude = lat;
	}
	
	public Double getDistance(GeoCode code) {
		return Math.sqrt(Math.pow(longitude-code.longitude,2) + Math.pow(latitude-code.latitude,2));
	}
}