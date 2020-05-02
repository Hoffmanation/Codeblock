package com.codeblock.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeblock.manager.UserManager;
import com.codeblock.pojo.LoginDetails;

/**
 * A Collection of {@link RestController} class that will accept HTTP request to interact with the JS client
 * A Collection of REST-API's for validating and authenticating user's login, register, update password and logout 
 * @author Hoffman
 *
 */
@RestController
public class UserController {

	
	@Autowired
	private UserManager userManager ;

	@RequestMapping(path = "codeblock/registration", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response registration(@RequestBody(required = true) LoginDetails userDetail, BindingResult bindingResult,HttpSession session)  {
		return userManager.registration(userDetail, bindingResult, session) ;
	}

	@RequestMapping(path = "codeblock/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response login(@RequestBody LoginDetails userDetail, HttpSession session) {
		return userManager.login(userDetail, session);
	}
	
	@RequestMapping(path = "codeblock/getUserInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getUserInfo(HttpSession session)  {
		return userManager.getUserInfo(session);
	}
	
	@RequestMapping(path = "codeblock/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean logout(@Context HttpServletRequest request,HttpSession session)  {
	    session = request.getSession(false);
		session.invalidate();
		if (session != null) {
			session = null;
		}
		return true;
	}

}
