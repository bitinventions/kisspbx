package org.kisspbx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class ManagerApiUser {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(nullable=false, unique=true)
	private String name;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String readPermission;
	
	@Column(nullable=false)
	private String writePermission;
	
	private String deny;
	
	private String permit;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReadPermission() {
		return readPermission;
	}

	public void setReadPermission(String read) {
		this.readPermission = read;
	}

	public String getWritePermission() {
		return writePermission;
	}

	public void setWritePermission(String write) {
		this.writePermission = write;
	}

	public String getDeny() {
		return deny;
	}

	public void setDeny(String deny) {
		this.deny = deny;
	}

	public String getPermit() {
		return permit;
	}

	public void setPermit(String permit) {
		this.permit = permit;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

}
