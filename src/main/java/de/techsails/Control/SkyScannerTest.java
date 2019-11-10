package de.techsails.Control;

import java.io.IOException;
import java.net.MalformedURLException;

import de.techsails.Control.SkyScanner.FlightPreference;
import de.techsails.JavaJSON.*;

public class SkyScannerTest {
    public static String SKY_SCANNER_KEY = "jacobs-2019";
    public static void main(String[] args) throws MalformedURLException, JSONException, IOException{

        SkyScanner travel = new SkyScanner(SKY_SCANNER_KEY);
        try {
        	
        	 
			System.out.println(travel.getAirportToAirport("MANC-sky", "LOND-sky", FlightPreference.CHEAPEST));
			
			
			travel.getPlaceToPlace("London", "Manchester", FlightPreference.CHEAPEST);
			 
		} catch (Exception e) {
			System.out.println("flight crashed");
			e.printStackTrace();
		}
        
       
       
    }
}
