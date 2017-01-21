package org.kisspbx.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class HuntGroup {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@NotNull
	@Size(max=255)
	@Column(nullable=false, unique=true, length=255)
	private String name;
	
	@Size(max=255)
	@Column(length=255)
	private String description;
	
	@NotNull
	@Size(max=32)
	@Column(nullable=false, length=32)
	private String strategy;
	
	@OneToOne
	private Extension priorityExtension;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<Extension> extensionGroup = new HashSet<>();
	
	private int dialtime;
	
	private int timeout;
	
	private int times;
	
	@OneToOne
	private Extension voicemailExtension;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public Extension getPriorityExtension() {
		return priorityExtension;
	}

	public void setPriorityExtension(Extension priorityExtension) {
		this.priorityExtension = priorityExtension;
	}

	public Set<Extension> getExtensionGroup() {
		return extensionGroup;
	}

	public void setExtensionGroup(Set<Extension> extensionGroup) {
		this.extensionGroup = extensionGroup;
	}

	public int getDialtime() {
		return dialtime;
	}

	public void setDialtime(int dialtime) {
		this.dialtime = dialtime;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Extension getVoicemailExtension() {
		return voicemailExtension;
	}

	public void setVoicemailExtension(Extension voicemailExtension) {
		this.voicemailExtension = voicemailExtension;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
