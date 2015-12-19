package edu.kpi.fiot.stationservice.service.dao.jpa.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
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
	
	public Ticket addSeatToBus(String busId, Ticket ticket){
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
	
	public List<Ticket> getAllOrderedSeatsInBus(String busId){
		Session session = service.getSession();
		session.beginTransaction();
		
		Bus bus = session.get(Bus.class, busId);
		Hibernate.initialize(bus.getSeats());

		session.getTransaction().commit();
		session.close();
		
		return bus.getSeats();
	}
	
	public List<Integer> getAllFreeSeatsInBus(String busId){
		Session session = service.getSession();
		session.beginTransaction();
		
		Bus bus = session.get(Bus.class, busId);
		Hibernate.initialize(bus.getSeats());

		session.getTransaction().commit();
		session.close();
		
		List<Ticket> allOrderedSeats = (List<Ticket>) bus.getSeats();
		List<Integer> freeSeats = new ArrayList<>();
		
		for(int i = 0; i < bus.getCapacity(); i++){
			if(!allOrderedSeats.contains(i)){
				freeSeats.add(i);
			}
		}
		
		return freeSeats;
	}
}
