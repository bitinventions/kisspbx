package org.kisspbx.model.provider;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("SIP")
public class SIPProvider extends Provider {
	
	@Size(max=256)
	@Column(length=256)
	private String did;
	
	@Size(max=256)
	@Column(length=256)
	private String host;
	
	@Size(max=256)
	@Column(length=256)
	private String username;
	
	@Size(max=256)
	@Column(length=256)
	private String password;
	
	private boolean register;
	
	private boolean insecure;
	
	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getDevice() {
		return String.format("SIP/%s", name);
	}

	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

	public boolean isInsecure() {
		return insecure;
	}

	public void setInsecure(boolean insecure) {
		this.insecure = insecure;
	}
}
