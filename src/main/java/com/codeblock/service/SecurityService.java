package com.codeblock.service;

import org.springframework.security.authentication.AuthenticationManager;

import com.codeblock.handler.BlogException;

/**
 * An Interface  for the {@link AuthenticationManager} DAO-Layer service
 * @author Hoffman
 *
 */
public interface SecurityService {

	public String findLoggedInUsername();

	public boolean autologin(String username, String password) throws BlogException;
}
