package com.codearchive.exce;



/**
 * the class that created the CustomerManagerExeception to be used in this system.
 * every exception that may arise within the system is converted to a 
 * CustomerManagerExeception and it's message was edited to fit the users needs.
 */

public class BlogException extends Exception {
	private static final long serialVersionUID = 1L;

	public BlogException(String msg) {
		super(msg) ; 
		
	}	
	
	

	

}
