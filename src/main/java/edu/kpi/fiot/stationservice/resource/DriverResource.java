package edu.kpi.fiot.stationservice.resource;

import java.io.Serializable;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.kpi.fiot.stationservice.exception.DataNotFoundException;
import edu.kpi.fiot.stationservice.exception.ErrorMessages;
import edu.kpi.fiot.stationservice.service.dao.DatabaseService;
import edu.kpi.fiot.stationservice.service.dao.dto.Driver;
import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.HibernateService;

@Path("/drivers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DriverResource {
private DatabaseService ds = HibernateService.getInstance();

private final Class<Driver> resourceClass = Driver.class;
	
	@GET
	@Path("/{driverId}")
    public Driver getDriver(@PathParam("driverId")String driverId) {
		Driver driver = ds.read(driverId, resourceClass);
		if(driver == null){
			String errMessage = String.format(ErrorMessages.DATA_NOT_FOUND, resourceClass.getName());
			throw new DataNotFoundException(errMessage);
		}
        return driver;
    }
	
	@POST
    public Response addDriver(Driver newDriver, @Context UriInfo uriInfo) {
		Serializable newId = ds.insert(newDriver);
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId.toString()).build();
		return Response.created(uri)
				.entity(newDriver)
				.build();
    }
}
