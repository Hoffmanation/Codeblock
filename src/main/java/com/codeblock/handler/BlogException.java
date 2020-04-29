package com.codeblock.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * the class that created the CustomerManagerExeception to be used in this
 * system. every exception that may arise within the system is converted to a
 * CustomerManagerExeception and it's message was edited to fit the users needs.
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

	public String getBlogError() {
		return this.errorMessage;
	}

	public Response.Status getBlogErrorStatus() {
		return this.errorStatus;
	}

}
