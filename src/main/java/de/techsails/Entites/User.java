package de.techsails.Entites;

import java.sql.Date;

public class User {
	
	private String email;
	private String name;
	private String pwd;
	private String phoneNo;
	private String country;
	private Date birthDate;
	
	public User() {
		
	}
	
	public User(String email, String name, String pwd, String phoneNo, String country, Date birthDate) {
		this.email = email;
		this.name = name;
		this.pwd = pwd;
		this.phoneNo = phoneNo;
		this.country = country;
		this.birthDate = birthDate;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
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
