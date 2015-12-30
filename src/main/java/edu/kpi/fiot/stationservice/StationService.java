package edu.kpi.fiot.stationservice;

import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.HibernateService;

@ApplicationPath("webapi")
public class StationService extends Application{
	
	@PreDestroy
	public void preDestroy(){
		HibernateService.getInstance().destroy();
	}
}
