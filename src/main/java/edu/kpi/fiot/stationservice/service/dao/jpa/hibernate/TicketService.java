package edu.kpi.fiot.stationservice.service.dao.jpa.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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

	public List<Ticket> getAllTicketsFromStation(String stationId) {
		List<Ticket> resultList = new ArrayList<>();

		Session session = service.getSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Ticket.class);
		resultList = criteria.add(Restrictions.eq("station", stationId)).list();
		session.getTransaction().commit();
		session.close();

		return resultList;
	}

	public Ticket addTicketToStation(String stationId, Ticket ticket) {
		Session session = service.getSession();
		session.beginTransaction();
		
		Station station = service.read(stationId, Station.class);
		station.getTickets().add(ticket);
		//ticket.setStation(station);
		session.update(station);
		session.save(ticket);
		
		session.getTransaction().commit();
		session.close();
		
		return ticket;
	}
}
