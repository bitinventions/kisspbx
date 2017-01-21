package org.kisspbx.model.provider;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@DiscriminatorColumn(name="type")
public abstract class Provider {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(unique=true, nullable=false)
	protected String name;
	
	@Column(length=2048)
	private String parameters;

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

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public abstract String getDevice();

}
