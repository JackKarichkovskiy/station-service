package edu.kpi.fiot.stationservice.resource;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.kpi.fiot.stationservice.service.dao.dto.Ticket;
import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.TicketService;

public class TicketResource {
	private TicketService ts = TicketService.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getAllTickets(@PathParam("stationId") String stationId) {
		return ts.getAllTicketsFromStation(stationId);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Ticket addTicket(@PathParam("stationId") String stationId, Ticket ticket) {
		return ts.addTicketToStation(stationId, ticket);
	}
}
