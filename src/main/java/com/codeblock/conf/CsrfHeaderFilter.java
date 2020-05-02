package com.codeblock.conf;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * One of the Application HTTP Filter.
 *1.Provides the HTTP request validation and/or creation of the access token - XSRF-TOKEN and X-CSRF-TOKEN.
 *2.Provides the HTTP request validation of an already logged in user
 * @author Hoffman
 *
 */
public class CsrfHeaderFilter extends OncePerRequestFilter {
	
	/**
	 * Main Filter method 
	 */
  @Override
  protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	  //Do a session validation and that the User object present in it, if is a valid request redirect to main application page
	  HttpSession session = request.getSession(false) ;
	  if (null!=session && null!=session.getAttribute("user")) {
		String url =   request.getRequestURL().toString();
		if (url.endsWith("/login.html") ||  url.endsWith("/registration.html") ) {
			response.sendRedirect("/index.html");  			
		}
	}
	  
		//Validate that the Csrf-Token and the XSRF-TOKEN cookie are present in 
		//the request and that they are the same value  
    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
        .getName());
    if (csrf != null) {
     Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
      String token = csrf.getToken();
      if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
        cookie = new Cookie("XSRF-TOKEN", token);
        cookie.setPath("/");
        response.addCookie(cookie);
      }
    }
    filterChain.doFilter(request, response);
  }
}