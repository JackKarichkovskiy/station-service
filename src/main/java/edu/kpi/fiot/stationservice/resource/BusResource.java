package edu.kpi.fiot.stationservice.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.kpi.fiot.stationservice.resource.exception.DataNotFoundException;
import edu.kpi.fiot.stationservice.resource.exception.ErrorMessages;
import edu.kpi.fiot.stationservice.service.dao.DatabaseService;
import edu.kpi.fiot.stationservice.service.dao.dto.Bus;
import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.HibernateService;

@Path("/buses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BusResource {

	private DatabaseService ds = HibernateService.getInstance();

	private final Class<Bus> resourceClass = Bus.class;

	@GET
	public List<Bus> getAllBuses() {
		List<Bus> allBuses = ds.getAllEntities(resourceClass);
		return allBuses;
	}

	@GET
	@Path("/{busId}")
	public Bus getBus(@PathParam("busId") String busId) {
		Bus bus = ds.read(busId, resourceClass);
		if (bus == null) {
			String errMessage = String.format(ErrorMessages.DATA_NOT_FOUND, resourceClass.getName());
			throw new DataNotFoundException(errMessage);
		}
		return bus;
	}

	@POST
	public Response addBus(Bus newBus, @Context UriInfo uriInfo) {
		Serializable newId = ds.insert(newBus);

		URI uri = uriInfo.getAbsolutePathBuilder().path(newId.toString()).build();
		return Response.created(uri).entity(newBus).build();
	}

	@PUT
	@Path("/{busId}")
	public Response updateBus(@PathParam("busId") String busId, Bus updatedBus) {
		updatedBus.setId(busId);
		ds.update(updatedBus);
		return Response.ok().entity(updatedBus).build();
	}

	@DELETE
	@Path("/{busId}")
	public Response deleteBus(@PathParam("busId") String busId) {
		Bus bus = new Bus();
		bus.setId(busId);
		ds.delete(bus);
		return Response.ok().entity(bus).build();
	}

	@Path("/{busId}/seats")
	public BusSeatsResource getBusSeatsResourceResource() {
		return new BusSeatsResource();
	}
}
