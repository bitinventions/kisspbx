package org.kisspbx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class CustomDialplan implements Destination {

	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(nullable=false, unique=true)
	private String name;
	
	@Column(nullable=false, length=2048)
	private String dialplan;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDialplan() {
		return dialplan;
	}

	public void setDialplan(String dialplan) {
		this.dialplan = dialplan;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getDestination() {
		return name;
	}

	@Override
	public String getDestinationName() {
		return "Custom:"+name;
	}
}
