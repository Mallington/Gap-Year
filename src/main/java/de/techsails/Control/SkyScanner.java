package de.techsails.Control;

import java.util.List;

import de.techsails.Entites.Flight;
import de.techsails.JavaJSON.*;

public class SkyScanner {
	
	enum FlightPreference{
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
    
    public List<Flight> getFlights(String departure, String arrival, SkyScanner.FlightPreference preference) throws Exception {
    	
    	
    	String request = String.format("%s/v1/flights/browse/browsequotes/v1.0/%s/%s/%s/%s/%s/%s/%s"
    			,BASE_URL, marketCountry, targetCurrency, locale, departure, arrival, departureDate, arrivalDate);
    	
    	JSONObject jsResponse = new JSONObject(RequestUtils.sendGet(request, apiKey));
    	
    	
    	
    	JSONArray quotes = jsResponse.getJSONArray("Quotes");
    	
    	System.out.println(quotes.toString());
    	
    	
    	return null;
    }
    
    public String getPlaces(String queryLocation) throws Exception {
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
