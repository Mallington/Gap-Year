package de.techsails.Entites;

import java.util.List;

public class Countries {

	List<String> countries;

	public Countries() {
	}

	public Countries(List<String> countries) {
		this.countries = countries;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}
	
	
	public String countiresAsString() {
		String sb = "";
		for(String c: countries)
			sb += c + " ";
		return sb;
	}
}
