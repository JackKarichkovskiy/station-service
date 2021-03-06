package edu.kpi.fiot.stationservice.service.dao.jpa.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.kpi.fiot.stationservice.service.dao.jpa.JPAService;

import static edu.kpi.fiot.stationservice.utils.ServiceUtils.checkNotNull;

public class HibernateService extends JPAService {

	private static HibernateService instance;

	private SessionFactory factory;

	/**
	 * Constructor of Singleton instance
	 * 
	 * @param dummyStr
	 *            - to prevent reflection initialization added non-valuable
	 *            String argument
	 * @throws HibernateException
	 *             - if config file is wrong
	 */
	private HibernateService(String dummyStr) {
		factory = new Configuration().configure().buildSessionFactory();
	}

	public static HibernateService getInstance() {
		if (instance != null)
			return instance;
		else
			return instance = new HibernateService("");
	}

	@Override
	public Serializable insert(Object obj) {
		checkNotNull(obj);

		Session session = factory.openSession();
		session.beginTransaction();
		Serializable newId = session.save(obj);
		session.getTransaction().commit();
		session.close();
		return newId;
	}

	@Override
	public void update(Object obj) {
		checkNotNull(obj);

		Session session = factory.openSession();
		session.beginTransaction();
		session.update(obj);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Object obj) {
		checkNotNull(obj);

		Session session = factory.openSession();
		session.beginTransaction();
		session.delete(obj);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public <T extends Serializable, V> V read(T id, Class<V> objClass) {
		checkNotNull(id);
		checkNotNull(objClass);

		Session session = factory.openSession();
		session.beginTransaction();
		V result = session.get(objClass, id);
		session.getTransaction().commit();
		session.close();
		
		return result;
	}

	@Override
	public <T> List<T> getAllEntities(Class<T> objClass) {
		checkNotNull(objClass);
		
		List<T> resultList = new ArrayList<>();

		Session session = factory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(objClass);
		resultList = criteria.list();
		session.getTransaction().commit();
		session.close();

		return resultList;
	}

	public SessionFactory getSessionFactory() {
		return factory;
	}

	public Session getSession() {
		return factory.openSession();
	}

	public void destroy() {
		if (factory != null) {
			factory.close();
		}
	}
}
