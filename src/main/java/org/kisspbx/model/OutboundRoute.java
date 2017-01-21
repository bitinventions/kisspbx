package org.kisspbx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.kisspbx.model.provider.Provider;

@Entity
public class OutboundRoute {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(nullable=false, unique=true)
	private String name;
	
	@Column(nullable=false)
	private String dialpattern;
	
	private int prefix;
	
	private String prepend;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private Provider provider;

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

	public String getDialpattern() {
		return dialpattern;
	}

	public void setDialpattern(String dialpattern) {
		this.dialpattern = dialpattern;
	}

	public int getPrefix() {
		return prefix;
	}

	public void setPrefix(int prefix) {
		this.prefix = prefix;
	}

	public String getPrepend() {
		return prepend;
	}

	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}
