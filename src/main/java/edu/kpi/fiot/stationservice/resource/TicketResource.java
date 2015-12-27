package edu.kpi.fiot.stationservice.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.kpi.fiot.stationservice.service.dao.DatabaseService;
import edu.kpi.fiot.stationservice.service.dao.dto.Ticket;
import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.HibernateService;

@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {
	private DatabaseService ds = HibernateService.getInstance();
	
	@GET
	public List<Ticket> getAllTickets() {
		return ds.getAllEntities(Ticket.class);
	}
	
	@GET
	@Path("/{ticketId}")
	public Ticket getTicket(@PathParam("ticketId") String ticketId) {
		return ds.read(ticketId, Ticket.class);
	}

	@PUT
	@Path("/{ticketId}")
	public Response updateTicket(@PathParam("ticketId") String ticketId, Ticket updatedTicket) {
		updatedTicket.setId(ticketId);
		ds.update(updatedTicket);
		return Response.ok().entity(updatedTicket).build();
	}

	@DELETE
	@Path("/{ticketId}")
	public Response deleteBus(@PathParam("ticketId") String ticketId) {
		Ticket ticket = new Ticket();
		ticket.setId(ticketId);
		ds.delete(ticket);
		return Response.ok().entity(ticket).build();
	}
}
