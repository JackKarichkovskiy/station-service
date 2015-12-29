package edu.kpi.fiot.stationservice.resource.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException ex) {
		Status errorStatus = Status.NOT_FOUND;
		ErrorMessage message = new ErrorMessage(ex.getMessage(), errorStatus.getStatusCode());
		return Response.status(errorStatus)
				.entity(message)
				.build();
	}

}
