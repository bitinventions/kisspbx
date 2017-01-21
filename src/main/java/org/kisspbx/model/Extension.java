package org.kisspbx.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Extension implements Destination {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String extension;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private ExtensionType type;
	
	@NotNull
	@Column(nullable=false)
	private String username;
	
	@NotNull
	@Column(nullable=false)
	private String password;
	
	@ManyToOne
	private Template template;
	
	private String callerid;
	
	private boolean voicemail;
	
	@Size(max=6)
	@Column(nullable=true, length=6)
	private String vmpin = "0000";
	
	private String vmemail;

    @Column(length=2048)
    private String parameters;
    
    @Size(max=255)
    @Column(length=255)
    private String callgroups;
    
    @Size(max=255)
    @Column(length=255)
    private String pickupgroups;

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
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String exten) {
		this.extension = exten;
	}

	public ExtensionType getType() {
		return type;
	}

	public void setType(ExtensionType type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getCallerid() {
		return callerid;
	}

	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}

	public boolean isVoicemail() {
		return voicemail;
	}

	public void setVoicemail(boolean voicemail) {
		this.voicemail = voicemail;
	}

	public String getVmpin() {
		return vmpin;
	}

	public void setVmpin(String vmpin) {
		this.vmpin = vmpin;
	}

	public String getVmemail() {
		return vmemail;
	}

	public void setVmemail(String vmemail) {
		this.vmemail = vmemail;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}   
	
	@Override
	public String toString() {
		return String.format("%s <%s>", callerid, extension);
	}

	@Override
	public String getDestination() {
		return extension;
	}
	
	@Override
	public String getDestinationName() {
		return String.format("%s - %s", extension, callerid);
	}

	public String getCallgroups() {
		return callgroups;
	}

	public void setCallgroups(String callgroups) {
		this.callgroups = callgroups;
	}

	public String getPickupgroups() {
		return pickupgroups;
	}

	public void setPickupgroups(String pickupgroups) {
		this.pickupgroups = pickupgroups;
	}
	
	public String getDialString() {
		return String.format("%s/%s", type.toString(), username);
	}
}
