package com.cloudbees.walmartqa1.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements
		ExceptionMapper<NotFoundException> {

	public Response toResponse(NotFoundException nfe) {
		return Response.status(Response.Status.NOT_FOUND)
				.entity(nfe.getMessage())
				.type(MediaType.APPLICATION_JSON).build();
	}
}
