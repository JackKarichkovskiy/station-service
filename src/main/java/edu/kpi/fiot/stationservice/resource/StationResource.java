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

import edu.kpi.fiot.stationservice.service.dao.DatabaseService;
import edu.kpi.fiot.stationservice.service.dao.dto.Station;
import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.HibernateService;

@Path("/stations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StationResource {
	private DatabaseService ds = HibernateService.getInstance();

	@GET
	@Path("/{stationId}")
	public Station getStation(@PathParam("stationId") String stationId) {
		Station station = ds.read(stationId, Station.class);
		return station;
	}

	@POST
	public Response addStation(Station station, @Context UriInfo uriInfo) {
		Serializable newId = ds.insert(station);
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId.toString()).build();
		return Response.created(uri).entity(station).build();
	}
	
	@Path("/{stationId}/tickets")
	public TicketResource getTicketResource() {
		return new TicketResource();
	}
}
