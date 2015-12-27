package edu.kpi.fiot.stationservice.service.dao.jpa.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.kpi.fiot.stationservice.service.dao.dto.Bus;
import edu.kpi.fiot.stationservice.service.dao.dto.Ticket;

public class BusSeatService {
	private HibernateService service = HibernateService.getInstance();

	private static BusSeatService instance;

	/**
	 * Constructor of Singleton instance
	 * 
	 * @param dummyStr
	 *            - to prevent reflection initialization added non-valuable
	 *            String argument
	 * @throws HibernateException
	 *             - if config file is wrong
	 */
	private BusSeatService(String dummyStr) {
	}

	public static BusSeatService getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new BusSeatService("");
	}

	public Ticket addSeatToBus(String busId, Ticket ticket) {
		Session session = service.getSession();
		session.beginTransaction();

		Bus bus = session.get(Bus.class, busId);
		bus.getSeats().add(ticket);
		session.save(ticket);
		session.update(bus);

		session.getTransaction().commit();
		session.close();

		return ticket;
	}

	public Ticket getTicketBySeatNum(String busId, Integer seatNum){
		Session session = service.getSession();
		session.beginTransaction();

		Query query = session.createQuery(HQLQueries.GET_TICKET_BY_SEAT);
		query.setString("busId", busId);
		query.setInteger("seatNum", seatNum);
		List<Ticket> tickets = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		if(tickets == null || tickets.isEmpty()){
			return null;
		}else{
			return tickets.get(0);
		}
	}

	public List<Ticket> getAllOrderedSeatsInBus(String busId) {
		Session session = service.getSession();
		session.beginTransaction();

		Bus bus = session.get(Bus.class, busId);
		Hibernate.initialize(bus.getSeats());

		session.getTransaction().commit();
		session.close();

		return bus.getSeats();
	}

	public List<Ticket> getAllFreeSeatsInBus(String busId) {
		Session session = service.getSession();
		session.beginTransaction();

		Bus bus = session.get(Bus.class, busId);
		Hibernate.initialize(bus.getSeats());

		session.getTransaction().commit();
		session.close();

		List<Ticket> allOrderedSeats = (List<Ticket>) bus.getSeats();
		List<Ticket> freeSeats = new ArrayList<>();

		outer: for (int i = 0; i < bus.getCapacity(); i++) {
			for (int j = 0; j < allOrderedSeats.size(); j++) {
				if (allOrderedSeats.get(j).getSeatNum().equals(i)) {
					continue outer;
				}
			}

			Ticket ticket = new Ticket();
			ticket.setSeatNum(i);
			freeSeats.add(ticket);
		}

		return freeSeats;
	}

	public Ticket[] getAllSeatsInBus(String busId) {
		Session session = service.getSession();
		session.beginTransaction();

		Bus bus = session.get(Bus.class, busId);
		Hibernate.initialize(bus.getSeats());

		session.getTransaction().commit();
		session.close();
		
		if(bus == null) return new Ticket[0];
		
		Ticket[] allSeats = new Ticket[bus.getCapacity()];
		
		for(Ticket ticket : bus.getSeats()){
			allSeats[ticket.getSeatNum()] = ticket;
		}
		
		for (int i = 0; i < allSeats.length; i++) {
			if(allSeats[i] == null){
				Ticket ticket = new Ticket();
				ticket.setSeatNum(i);
				allSeats[i] = ticket;
			}
		}
		
		return allSeats;
	}

	private interface HQLQueries {
		String BUS_ID_TAG = ":busId";
		String SEAT_NUM_TAG = ":seatNum";
		String GET_TICKET_BY_SEAT = "from Ticket as t where t.bus.id = " + BUS_ID_TAG + " and t.seatNum = "
				+ SEAT_NUM_TAG;
	}
}
