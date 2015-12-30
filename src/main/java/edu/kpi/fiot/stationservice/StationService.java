package edu.kpi.fiot.stationservice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import edu.kpi.fiot.stationservice.service.dao.jpa.hibernate.HibernateService;

@ApplicationPath("webapi")
public class StationService extends Application{
	
	@PostConstruct
	public void init(){
		HibernateService.getInstance();
	}
	
	@PreDestroy
	public void destroy(){
		HibernateService.getInstance().destroy();
	}
}
