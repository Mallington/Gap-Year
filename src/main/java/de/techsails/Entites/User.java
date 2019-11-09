package de.techsails.Entites;

import java.sql.Date;

public class User {
	
	private int id;
	private String name;
	private String phoneNo;
	private String country;
	private Date birthDate;
	
	public User() {
		
	}
	
	public User(int id, String name, String phoneNo, String country, Date birthDate) {
		this.id = id;
		this.name = name;
		this.phoneNo = country;
		this.birthDate = birthDate;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public String getCountry() {
		return country;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	
	
}
