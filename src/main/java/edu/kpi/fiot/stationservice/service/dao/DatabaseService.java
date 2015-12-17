package edu.kpi.fiot.stationservice.service.dao;

import java.io.Serializable;
import java.util.List;

public interface DatabaseService {
	Serializable insert(Object obj);
	
	<T extends Serializable, V> V read(T id, Class<V> objClass);
	
	void update(Object obj);
	
	void delete(Object obj);
	
	<T>List<T> getAllEntities(Class<T> objClass);
}
