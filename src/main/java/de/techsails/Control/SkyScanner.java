package de.techsails.Control;

import java.util.List;

import de.techsails.Entites.Flight;
import de.techsails.JavaJSON.*;

public class SkyScanner {
	
	enum FlightPreference{
		CHEAPEST
	}

	static String BASE_URL = "http://partners.api.skyscanner.net/apiservices";
	static String API_VERSION = "v2";
    private String apiKey;
    private String secret = null;
    
    
    
    public SkyScanner(String key){
    	apiKey = key;
    }
    
    public boolean connect() {
    	try {
			secret = RequestUtils.sendGet(String.format("%s/token/%s/gettoken?apiKey=%s", BASE_URL, API_VERSION, apiKey));
			return (secret ==null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		}
    }
    
    public List<Flight> getFlights(String departure, String arrival, SkyScanner.FlightPreference preference) {
    	
    	
    	return null;
    }
    
    public boolean isAuthenticated() {
    	return (secret ==null);
    }
    
    public String getSecret() {
    	return secret;
    }


    public void getFlightPath(){
    		
    }
}
