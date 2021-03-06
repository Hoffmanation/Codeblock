package com.codeblock.service.impl;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeblock.service.SecurityService;




/**
 * A Service-Implementation class  for the {@link AuthenticationManager} DAO-Layer service
 * @author Hoffman
 *
 */
@Transactional
@Component
public class SecurityServiceImpl implements SecurityService {
	private static final Logger logger = Logger.getLogger(SecurityServiceImpl.class.getSimpleName());
	
	/*
	 * Spring Dependency Injection
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	

/**
 * Find And retrieve the already logged in user from Spring-Context
 */
	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}

	/**
	 * Main method for authenticating users
	 * 
	 * @param username
	 * @param password
	 * @return {@link Boolean}
	 */
	@Override
	public boolean autologin(String username, String password) {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, password, userDetails.getAuthorities());

			authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			if (usernamePasswordAuthenticationToken.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				logger.info("A new successful Login to Codeblock App, Credentials that was given : User Name: "+ username + ", Password: " + password);
				return true;
			}
			logger.error("Access to Codeblock App was denied, Credentials that was given : User Name: "+ username + ", Password: " + password);
			return false;
		} catch (BadCredentialsException bc) {
			logger.error("Access to Codeblock App was denied due to Bad Credentials, Credentials that was given : User Name: "+ username + ", Password: " + password);
		} catch (UsernameNotFoundException unf) {
			logger.error("Access to Codeblock App was denied due to a non existed user, Credentials that was given : User Name: "+ username + ", Password: " + password);
		} catch (Exception e) {
			logger.error("Access to Codeblock App was denied, Credentials that was given : User Name: "+ username + ", Password: " + password);
		}
		return false;
	}
	

}
