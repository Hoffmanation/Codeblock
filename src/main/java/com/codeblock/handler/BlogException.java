package com.codeblock.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Custom Exception class will serve as an error message return to the client
 * in case a CRUD operation failed.
 * 
 * @author Hoffman
 *
 */
public class BlogException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private Status errorStatus;

	public BlogException(String msg, Response.Status status) {
		super(msg);
		this.errorMessage = msg;
		this.errorStatus = status;

	}

	//Method will provide a specific error Message for the REST API error response
	public String getBlogError() {
		return this.errorMessage;
	}

	//Method will provide a specific error status code for the REST API error response
	public Response.Status getBlogErrorStatus() {
		return this.errorStatus;
	}

}
