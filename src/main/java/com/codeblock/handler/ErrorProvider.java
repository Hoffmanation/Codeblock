package com.codeblock.handler;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.codeblock.pojo.Message;


// This class will provide a message to be shown to the user in case exception is thrown by one of the restful resource methods
@Provider
public class ErrorProvider implements ExceptionMapper<Exception>{

public static String error ; 

	@Override
	public Response toResponse(Exception e) {
		return 
				Response.serverError() // create response builder with Error code: 500
				.entity(new Message(printError(error))) // create a JSON within the reponse
				.build(); // create the response using the builder
	}
	
	public String printError(String error) {
		return error ; 
	}
	

}
