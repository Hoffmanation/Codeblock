package com.codeblock.dao;

import java.util.List;
import java.util.UUID;

import com.codeblock.entity.User;
import com.codeblock.exce.BlogException;

public interface UserService {

	public boolean createUser(String name , String password, String password2) throws BlogException;
	
	public boolean createUser(User user) throws BlogException;

	public List<User> getUserById(UUID id) throws BlogException;


	public boolean logout() throws BlogException;
	
	 public User findByUsername(String username);
	

}
