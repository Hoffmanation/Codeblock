package com.codeblock.service;

import java.util.List;
import java.util.UUID;

import com.codeblock.entity.User;
import com.codeblock.handler.BlogException;
/**
 * An Interface  for the {@link User} DAO-Layer service
 * @author Hoffman
 *
 */
public interface UserService {

	public boolean createUser(String name , String password) throws BlogException;
	
	public boolean createUser(User user) throws BlogException;

	public List<User> getUserById(UUID id) throws BlogException;


	public boolean logout() throws BlogException;
	
	 public User findByUsername(String username) ;
	

}
