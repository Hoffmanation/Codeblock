package com.codeblock.manager;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codeblock.entity.User;
import com.codeblock.handler.BlogException;
import com.codeblock.handler.ErrorProvider;
import com.codeblock.pojo.LoginDetails;
import com.codeblock.pojo.LoginError;
import com.codeblock.service.SecurityService;
import com.codeblock.service.UserService;
import com.codeblock.util.Constants;
import com.codeblock.util.UserValidator;

/**
 * A service manager class that will handle the Business-Logic for the
 * {@link User} DB entity This class serves us as the layer that connect between
 * the REST-API's Layer and the DAO layer of the USER entities
 * 
 * @author Hoffman
 *
 */
@Service
public class UserManager {

	
	/*
	 * Spring Dependency Injection
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserValidator userValidator;

	
	
	/**
	 * Registration B-L method
	 * 
	 * @param userDetail
	 * @param bindingResult
	 * @param session
	 * @return {@link Response}
	 */
	public Response registration(LoginDetails userDetail, BindingResult bindingResult, HttpSession session) {
		try {
			User user = new User(userDetail.getUsername(), userDetail.getPassword());
			userValidator.validate(userDetail, bindingResult);
			if (bindingResult.hasErrors()) {
				LoginError errors = userValidator.getError(bindingResult);
				return Response.status(401).entity(errors).build();
			}
			userService.createUser(new User(userDetail.getUsername(), userDetail.getPassword()));
			session.setAttribute(Constants.USER_LOGIN, user);
			login(userDetail, session);
		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
		return Response.status(200).entity(Status.OK.getReasonPhrase()).build();
	}

	/**
	 * Login B-L method
	 * 
	 * @param userDetail
	 * @param session
	 * @return {@link Response}
	 */
	public Response login(LoginDetails userDetail, HttpSession session) {
		try {
			if (securityService.autologin(userDetail.getUsername(), userDetail.getPassword())) {
				User user = userService.findByUsername(userDetail.getUsername());
				session.setAttribute(Constants.USER_LOGIN, user);
				return Response.status(200).entity(Status.OK.getReasonPhrase()).build();
			}
			return Response.status(401).entity("*Username or password are incorrect.").build();

		} catch (BlogException e) {
			return new ErrorProvider().toResponse(e);
		}
	}
	
	
	
	/**
	 * Get and display the user info B-L method
	 * 
	 * @param session
	 * @return {@link Response}
	 */
	public Response getUserInfo(HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER_LOGIN);
		return Response.status(200).entity(user).build();
	}

}
