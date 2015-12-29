package edu.kpi.fiot.stationservice.utils;

public class ServiceUtils {
	
	/**
	 * Method checks the object by null equality.
	 *
	 * @param <T>
	 *            - type of object
	 * @param obj
	 *            - object that needs to be checked
	 * @return the origin value
	 * @throws IllegalArgumentException
	 *             if (object == null)
	 */
	public static final <T> T checkNotNull(T obj) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}

		return obj;
	}
}
