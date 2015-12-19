package edu.kpi.fiot.stationservice.service.dao.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.mapping.Bag;

@Entity
@Table(name="buses")
@XmlRootElement
public class Bus {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@OneToOne
	@JoinColumn(name="driver_id")
	private Driver driver;

	@OneToMany(mappedBy = "bus", fetch=FetchType.LAZY)
	private List<Ticket> seats = new ArrayList<>();
	
	@Column(name = "capacity")
	private int capacity;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@XmlTransient
	public List<Ticket> getSeats() {
		return seats;
	}

	public void setSeats(List<Ticket> seats) {
		this.seats = seats;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
