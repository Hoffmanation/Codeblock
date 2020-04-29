package com.codeblock.conf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CodeblockLogoutHandler implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		if (authentication != null) {
			System.out.println(authentication.getName());
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