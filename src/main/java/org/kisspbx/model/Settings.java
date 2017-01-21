package org.kisspbx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Settings {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private SettingsType type;
	
	@Column(length=255)
	private String name;
	
	@Column(length=255)
	private String value;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SettingsType getType() {
		return type;
	}

	public void setType(SettingsType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
