package com.codearchive.dao;

public interface SecurityService {

	public String findLoggedInUsername();

	public boolean autologin(String username, String password);
}
