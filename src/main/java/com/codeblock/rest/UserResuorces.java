package com.codeblock.rest;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeblock.dao.SecurityService;
import com.codeblock.dao.UserService;
import com.codeblock.entity.Message;
import com.codeblock.entity.User;
import com.codeblock.exce.BlogException;
import com.codeblock.util.UserValidator;

@RestController
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)

public class UserResuorces {

	public static final String USER_LOGIN = "user";

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(path = "codeBlock/registration", method = RequestMethod.POST)
	public Response registration(@RequestBody(required = true) User userDetail, BindingResult bindingResult,
			HttpSession session) throws BlogException {
		User user = new User(userDetail.getUsername(), userDetail.getPassword(), userDetail.getPasswordConfirm());
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			List<String> errors = UserValidator.getError();
			return Response.status(200).entity(errors.toArray(new String[0])).build();
		}
		userService.createUser(user);
		securityService.autologin(user.getUsername(), user.getPasswordConfirm());
		session.setAttribute(USER_LOGIN, user);
		return Response.status(200).entity(new Message("you successfully logged in!")).build();
	}

	@RequestMapping(path = "codeBlock/login", method = RequestMethod.POST)
	public Response login(@RequestBody User userDetail, HttpSession session) {
		if (securityService.autologin(userDetail.getUsername(), userDetail.getPassword())) {
			User user = userService.findByUsername(userDetail.getUsername());
			session.setAttribute(USER_LOGIN, user);
			return Response.status(200).entity(new Message("you successfully logged in!")).build();
		}
		return Response.status(200).entity(new Message("*Username or password are incorrect.")).build();

	}

}
