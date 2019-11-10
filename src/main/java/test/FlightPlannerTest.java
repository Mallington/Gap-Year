package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.techsails.Control.FlightPlanning.FlightPlanner;
import de.techsails.Control.FlightPlanning.GeoCode;
import de.techsails.Entites.Flight;
import de.techsails.Entites.FlightQuote;
import de.techsails.Entites.User;

public class FlightPlannerTest {

	FlightPlanner planner;
	ArrayList<String> countries;
	User user;
	
	@Before
	public void setUp() throws Exception {
		planner = new FlightPlanner();
		countries = new ArrayList<String>();
		countries.add("France"); countries.add("Italy"); countries.add("Tunisia"); countries.add("Egypt");
		//user = new User("test@email.com", "Anis Mekacher", "12456789", "+461775499980", "Germany", new Date(925871313000L) );
	}

	@Test
	public void testGetFlightPlan() {
		System.out.println("**************** getFlightPlan ****************");
		List<FlightQuote> flights = null;
		try {
			flights = planner.getFlightPlan(countries, null, "2020-01-01", 3);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(FlightQuote flight : flights) {
			System.out.println(flight);
		}
	}

	@Ignore
	@Test
	public void testGetBestFlight() {
		System.out.println("************** getBestFlight *****************");
		FlightQuote flight = planner.getBestFlight("Germany", countries, new Date(1577847946000L));
		System.out.println(flight);
	}

	@Ignore
	@Test
	public void testGetGeoCodesFromCountryList() {
		List<GeoCode> geoCodes = planner.getGeoCodesFromCountryList(countries);
		
		System.out.println("******** getGeoCodesFromCountryList **********");
		for(GeoCode country : geoCodes)
			System.out.println(country);
	}

	@Ignore
	@Test
	public void testGetGeoCode() {
		System.out.println("***************** getGeoCode *****************");
		System.out.println(planner.getGeoCode("Germany"));
	}

}
