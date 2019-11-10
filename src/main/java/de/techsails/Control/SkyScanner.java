package de.techsails.Control;

import java.util.ArrayList;
import java.util.List;

import de.techsails.Control.SkyScanner.FlightPreference;
import de.techsails.Entites.*;
import de.techsails.JavaJSON.*;

public class SkyScanner {
	
	public enum FlightPreference{
		CHEAPEST
	}

	static String BASE_URL = "https://www.skyscanner.net/g/chiron/api";
	static String API_VERSION = "v1";
    private String apiKey;
    private String secret = null;
    private String marketCountry = "GB";
    private String targetCurrency = "GBP";
    private String locale = "en-GG";
    private String departureDate = "anytime";
    private String arrivalDate = "anytime";
    
    
    
    public SkyScanner(String key){
    	apiKey = key;
    }
    
   /* public boolean connect() {
    	
    	try {
			secret = RequestUtils.sendGet(String.format("%s/token/%s/gettoken?apiKey=%s", BASE_URL, API_VERSION, apiKey));
			return (secret ==null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		}
    } */
    
    
    public List<FlightQuote> getPlaceToPlace(String departureLocation, String arrivalLocation, SkyScanner.FlightPreference preference) throws Exception {
    	List<FlightQuote> flightQuotes = new ArrayList<FlightQuote>();
    	
    	List<String> depatureAirports = getAirports(departureLocation);
    	List<String> arrivalAirports = getAirports(arrivalLocation);
    	
    	for(String depart : depatureAirports) {
    		for(String arrival : arrivalAirports) {
        		System.out.println(String.format("Checking: %s, %s", depart, arrival));
        		FlightQuote cheapest = getAirportToAirport(depart, arrival, preference);
        		System.out.println("Cheap: "+cheapest);
        	}
    	}
    	
    	return flightQuotes;
    }
    
    public List<String> getAirports(String locationName) throws Exception{
    	List<String> ids = new ArrayList<String>();
    	String placesJSON = getPlacesString(locationName);
    	
    	JSONArray array = new JSONObject(placesJSON).getJSONArray("Places");
    	
    	for(int i =0; i< array.length(); i++) {
    		ids.add(array.getJSONObject(i).getString("PlaceId"));
    		
    	}
    	return ids;
    }
    public FlightQuote getAirportToAirport(String departure, String arrival, SkyScanner.FlightPreference preference) throws Exception {
    	String request = String.format("%s/v1/flights/browse/browsequotes/v1.0/%s/%s/%s/%s/%s/%s/%s"
    			,BASE_URL, marketCountry, targetCurrency, locale, departure, arrival, departureDate, arrivalDate);
    	
    	JSONObject jsResponse = new JSONObject(RequestUtils.sendGet(request, apiKey));
    	
    	
    	
    	JSONArray quotes = jsResponse.getJSONArray("Quotes");
    	
    	
    	
    	switch(preference) {
    	case CHEAPEST:
    		int cheapest = Integer.MAX_VALUE;
    		JSONObject cheapestQuote = null;
    		
    		for(int i =0; i< quotes.length(); i++) {
    				JSONObject quote = quotes.getJSONObject(i);
    				if(quote.getInt("MinPrice")<cheapest) {
    					cheapest = quote.getInt("MinPrice");
    					cheapestQuote = quote;
    				}
    		}
    		
    		if(cheapestQuote!=null) return new FlightQuote(departure, arrival,cheapestQuote.getJSONObject("InboundLeg").getString("DepartureDate"), cheapestQuote.getInt("MinPrice"));
    		else return null;
    	}
    	
    	
    	
    	
    	
    	
    	return null;
    }
    
    public String getPlacesString(String queryLocation) throws Exception {
    	String request = String.format("%s/v1/places/autosuggest/v1.0/%s/%s/%s?query={%s}", BASE_URL,marketCountry, targetCurrency, locale, queryLocation);
    	return RequestUtils.sendGet(request, apiKey);
    }
    
    /*public boolean isAuthenticated() {
    	return (secret ==null);
    } */
    
   /* public String getSecret() {
    	return secret;
    }*/


    public void getFlightPath(){
    		
    }
}
