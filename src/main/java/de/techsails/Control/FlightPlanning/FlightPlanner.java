package de.techsails.Control.FlightPlanning;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import de.techsails.Control.DateUtils;
import de.techsails.Control.SkyScanner;
import de.techsails.Entites.Flight;
import de.techsails.Entites.FlightQuote;
import de.techsails.Entites.User;
import de.techsails.JavaJSON.JSONArray;
import de.techsails.JavaJSON.JSONTokener;

public class FlightPlanner {
	
	InputStream countriesGeoCodesFile;
	JSONArray countriesGeoCodes;
	
	HashMap<String, CountryData> countryData = new HashMap<>();
	
	public FlightPlanner() {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			countriesGeoCodesFile = classloader.getResourceAsStream("country-geocodes.json");
			countriesGeoCodes = new JSONArray(new JSONTokener(countriesGeoCodesFile));
			for (int i = 0; i < countriesGeoCodes.length(); i++) {
				JSONArray tmp = countriesGeoCodes.getJSONArray(i);
				CountryData data = new CountryData(
						tmp.getString(0),
						Double.parseDouble(tmp.getString(1)),
						Double.parseDouble(tmp.getString(2)),
						tmp.getString(3));
				countryData.put(data.name, data);
			}
	}
	
	@SuppressWarnings("deprecation")
	public List<FlightQuote> getFlightPlan(List<String> countries,User user,String date, int numOfDaysInbetween) throws ParseException {
		
		ArrayList<FlightQuote> flightsPlan = new ArrayList<>();
		String lastCountry = (user == null) ? "Germany" :  user.getCountry();
		Date lastArrivalDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date departureDate = format.parse(date);
		
		while(countries.size() > 0) {
			
			Date departure = flightsPlan.isEmpty() ? departureDate : DateUtils.addToDate(lastArrivalDate, numOfDaysInbetween);
			System.out.println("Checking a flight from "+lastCountry+ " to one of these countries "+Arrays.toString(countries.toArray()));
			FlightQuote bestFlight = getBestFlight(lastCountry,countries,departure);
			
			System.out.println(bestFlight);
			System.out.println("    ||");
			System.out.println("    ||");
			System.out.println("    \\/");
			
			lastCountry = bestFlight.destCountry;
			countries.remove(lastCountry);
			flightsPlan.add(bestFlight);
			try {
				lastArrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(bestFlight.getDepartureTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return flightsPlan;
	}
	
	public FlightQuote getBestFlight(String country, List<String> countries, Date departure) {
		HashMap<String, Double> distances = new HashMap<String,Double>();
		ArrayList<GeoCode> countriesGeoCodes = getGeoCodesFromCountryList(countries);
		GeoCode curCountryGeo = getGeoCode(country);
		
		for(int i=0; i<countries.size(); i++) 
			distances.put(countries.get(i),curCountryGeo.getDistance(countriesGeoCodes.get(i)) );
		
		Optional< Entry<String, Double>> relationship = distances.entrySet().stream().max(new Comparator< Entry<String,Double> >() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return o1.getValue() > o2.getValue() ? -1 : 1;
			}
		});
		
		SkyScanner skyScanner = new SkyScanner("jacobs-2019");
		skyScanner.setDepartureDate(departure);
		List<FlightQuote> flights = null ;
		try {
			flights = skyScanner.getPlaceToPlace(country, relationship.get().getKey(), SkyScanner.FlightPreference.CHEAPEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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