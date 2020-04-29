package com.codeblock.pojo;

public class LoginDetails {

	private String username ;
	private String password ;
	private String passwordConfirm;
	
	public LoginDetails() {
		// TODO Auto-generated constructor stub
	}

	public LoginDetails(String username, String password,String passwordConfirm) {
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public String toString() {
		return "LoginDetails [username=" + username + ", password=" + password + ", passwordConfirm=" + passwordConfirm
				+ "]";
	}
	
	
	
}
