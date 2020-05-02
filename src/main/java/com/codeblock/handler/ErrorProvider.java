package com.codeblock.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.MediaType;

/**
 * A {@link @Provider} class will provides a global handling for all exceptions throughout this application. 
 * Provide a message to be shown to the user in case an
 * exception is thrown by one of the restful resource methods
 * @author Hoffman
 *
 */
@Provider
public class ErrorProvider implements ExceptionMapper<BlogException> {



	   @Override
	    public Response toResponse(final BlogException exception) {
	        return Response.status(getBlogErrorStatus(exception))
	                       .entity(getBlogError(exception))
	                       .type(MediaType.APPLICATION_JSON_VALUE)
	                       .build();
	    }

	   private String getBlogError(BlogException exception) {
	        return exception.getBlogError() ;
	    }
	   
	   private Response.Status getBlogErrorStatus(BlogException exception) {
		   return exception.getBlogErrorStatus() ;
	   }

}
