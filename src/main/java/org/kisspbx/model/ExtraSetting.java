package org.kisspbx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class ExtraSetting {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private SettingsType type;
	
	@Size(max=4096)
	@Column(length=4096)
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
