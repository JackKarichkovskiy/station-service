package edu.kpi.fiot.stationservice.resource;

import java.net.URI;
import java.util.Collection;
import java.util.List;

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

import edu.kpi.fiot.stationservice.resource.exception.DataNotFoundException;
import edu.kpi.fiot.stationservice.resource.exception.ErrorMessages;
import edu.kpi.fiot.stationservice.service.dao.dto.Ticket;
import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.BusSeatService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BusSeatsResource {
	private BusSeatService busService = BusSeatService.getInstance();
	
	@POST
	public Response addSeat(@PathParam("busId") String busId,  Ticket ticket, @Context UriInfo uriInfo) {
		Ticket newSeat = busService.addSeatToBus(busId, ticket);
		URI uri = uriInfo.getAbsolutePathBuilder().path(newSeat.getId()).build();
		return Response.created(uri).entity(newSeat).build();
	}
	
	@GET
	@Path("/{seatNumber}")
	public Ticket getTicketBySeatNum(@PathParam("busId") String busId,
			@PathParam("seatNumber") Integer seatNumber){
		Ticket ticket = busService.getTicketBySeatNum(busId, seatNumber);
		if(ticket == null){
			String errMessage = String.format(ErrorMessages.SEAT_NOT_FOUND, seatNumber);
			throw new DataNotFoundException(errMessage);
		}
		return ticket;
	}
	
	@GET
	@Path("/orderedSeats")
	public Collection<Ticket> getAllOrderedSeats(@PathParam("busId") String busId) {
	    Collection<Ticket> allOrderedSeatsInBus = busService.getAllOrderedSeatsInBus(busId);
		return allOrderedSeatsInBus;
	}
	
	@GET
	@Path("/freeSeats")
	public List<Ticket> getAllFreeSeats(@PathParam("busId") String busId){
		return busService.getAllFreeSeatsInBus(busId);
	}
	
	@GET
	public Ticket[] getAllSeats(@PathParam("busId") String busId){
		return busService.getAllSeatsInBus(busId);
	}
}
