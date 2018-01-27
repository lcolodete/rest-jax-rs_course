package org.lcolodete.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.lcolodete.javabrains.messenger.model.ErrorMessage;

//Anotacao desabilitada devido ao video 28 : Using WebApplicationException
//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable t) {
		ErrorMessage errorMessage = new ErrorMessage(t.getMessage(), 500, "http://javabrains.koushik.org");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(errorMessage)
						.build();
	}

}
