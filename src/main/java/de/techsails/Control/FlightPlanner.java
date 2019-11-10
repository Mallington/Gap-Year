package de.techsails.Control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import de.techsails.Entites.Flight;
import de.techsails.Entites.FlightQuote;
import de.techsails.Entites.User;
import de.techsails.JavaJSON.JSONArray;
import de.techsails.JavaJSON.JSONTokener;

public class FlightPlanner {
	
	FileInputStream countriesGeoCodesFile;
	JSONArray countriesGeoCodes;
	
	HashMap<String, CountryData> countryData;
	
	public FlightPlanner() {
		try {
			countriesGeoCodesFile = new FileInputStream(getClass().getResource("country-geocodes.json").getPath());
			countriesGeoCodes = new JSONArray(new JSONTokener(countriesGeoCodesFile));
			for (int i = 0; i < countriesGeoCodes.length(); i++) {
				JSONArray tmp = countriesGeoCodes.getJSONArray(i);
				CountryData data = new CountryData(tmp.getString(0),tmp.getDouble(1),tmp.getDouble(2),tmp.getString(3));
				countryData.put(data.name, data);
			}
		} catch(FileNotFoundException e) {
		}
	}
	
	public List<Flight> getFlightPlan(List<String> countries,User user,Date departureDate, int numOfDaysInbetween) {
		ArrayList<Flight> flightsPlan = new ArrayList<>();
		String lastCountry = user.getCountry();
		Date lastArrivalDate = null;
		while(countries.size() > 0) {
			Date departure = flightsPlan.isEmpty() ? departureDate : DateUtils.addToDate(lastArrivalDate, numOfDaysInbetween);
			Flight bestFlight = getBestFlight(lastCountry,countries,departure);
			countries.remove(lastCountry);
			lastCountry = bestFlight.getDestination();
			flightsPlan.add(bestFlight);
		}
		
		return flightsPlan;
	}
	
	public Flight getBestFlight(String country, List<String> countries, Date departure) {
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
		
		SkyScanner skyScanner = new SkyScanner("jackobs2019"); //TODO add api key
		List<FlightQuote> flights = null ;
		try {
			flights = skyScanner.getPlaceToPlace(country, relationship.get().getKey(), SkyScanner.FlightPreference.CHEAPEST);
		} catch (Exception e) {}
		
		flights.sort(new Comparator<FlightQuote>() {
			@Override
			public int compare(FlightQuote o1, FlightQuote o2) {
				return o1.getMinCost() < o2.getMinCost() ? -1 : 1;
			}
		});
		
		return flights.get(0);
	}
	
	public ArrayList<GeoCode> getGeoCodesFromCountryList(List<String> countries) {
		ArrayList<GeoCode> countriesGeoCodes = new ArrayList<>();
		for(String country : countries)
			countriesGeoCodes.add(getGeoCode(country));
		
		return countriesGeoCodes;
	}
	
	public GeoCode getGeoCode(String country) {
		return countryData.get(country).geoCode;
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

class CountryData {
	public String country;
	public GeoCode geoCode;
	public String name;
	
	public CountryData() {}
	
	public CountryData(String country, double lat, double lon, String name) {
		this.country = country;
		geoCode = new GeoCode(lon, lat);
		this.name = name;
	}
}