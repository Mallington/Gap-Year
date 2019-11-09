package de.techsails.Entites;

public class Login {
	
	private String email;
	private String pwd;

	public Login(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}
	
	
}
