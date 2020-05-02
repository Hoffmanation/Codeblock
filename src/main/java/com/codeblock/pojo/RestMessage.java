package com.codeblock.pojo;

/**
 * Message POJO will be used as a custom  response for any  REST API.
 * @author Hoffman
 *
 */
public class RestMessage {
	private String message;

	public RestMessage() {
	}

	public RestMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + "]";
	}
}