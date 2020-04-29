package com.codeblock.rest;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeblock.manager.CodeblockAppManager;
import com.codeblock.pojo.LoginDetails;

@RestController
public class UserController {

	
	@Autowired
	private CodeblockAppManager appManager ;

	@RequestMapping(path = "codeblock/registration", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response registration(@RequestBody(required = true) LoginDetails userDetail, BindingResult bindingResult,HttpSession session)  {
		return appManager.registration(userDetail, bindingResult, session) ;
	}

	@RequestMapping(path = "codeblock/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response login(@RequestBody LoginDetails userDetail, HttpSession session) {
		return appManager.login(userDetail, session);
	}

}
