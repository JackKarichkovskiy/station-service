package edu.kpi.fiot.stationservice.service.dao.jpa.hibernate;

import java.util.Collection;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import edu.kpi.fiot.stationservice.service.dao.dto.Bus;
import edu.kpi.fiot.stationservice.service.dao.dto.Station;
import edu.kpi.fiot.stationservice.service.dao.dto.Ticket;

public class TicketService {
	private HibernateService service = HibernateService.getInstance();

	private static TicketService instance;

	/**
	 * Constructor of Singleton instance
	 * 
	 * @param dummyStr
	 *            - to prevent reflection initialization added non-valuable
	 *            String argument
	 * @throws HibernateException
	 *             - if config file is wrong
	 */
	private TicketService(String dummyStr) {
	}

	public static TicketService getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new TicketService("");
	}

	public Collection<Ticket> getAllTicketsFromStation(String stationId) {
		Session session = service.getSession();
		session.beginTransaction();
		
		Station station = session.get(Station.class, stationId);
		Hibernate.initialize(station.getTickets());

		session.getTransaction().commit();
		session.close();

		return station.getTickets();
	}

	public Ticket addTicketToStation(String stationId, Ticket ticket) {
		Session session = service.getSession();
		session.beginTransaction();
		
		Station station = session.get(Station.class, stationId);
		station.getTickets().add(ticket);
		ticket.setStation(station);
		session.save(ticket);
		Bus bus = ticket.getBus();
		if(bus != null){
			bus.getSeats().add(ticket);
			session.update(bus);
		}
		session.update(station);
		
		session.getTransaction().commit();
		session.close();
		
		return ticket;
	}
}
