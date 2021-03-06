package com.codeblock.conf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * A Logount listener to be use by the user every time a user logs out of the
 * application
 * 
 * @author Hoffman
 *
 */
public class CodeblockLogoutHandler implements LogoutHandler {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CodeblockLogoutHandler.class);
	/**
	 * Main logout listener method
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		//If {@Authentication} is present in the servlet context and the user is injected inside of it log the logout event
		//And redirect the user to the login page right after
		if (authentication != null) {
			logger.info("User seccessfully logged out, user info:" + authentication.getPrincipal());
		}
		// perform other required operation
		String URL = request.getContextPath() + "/login.html";
		response.setStatus(org.springframework.http.HttpStatus.OK.value());
		try {
			response.sendRedirect(URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}