package org.kisspbx.model.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.kisspbx.model.Destination;
import org.kisspbx.model.DestinationType;

@Entity
@DiscriminatorColumn(name="type")
public abstract class Application implements Destination {
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@NotNull
	@Size(max=255)
	@Column(nullable=false, length=255)
	private String description;
	
	@NotNull
	@Size(max=64)
	@Column(nullable=false, unique=true, length=64)
	protected String pattern;
	
	@Size(max=64)
	@Column(nullable=true, length=64)
	protected String extension;
	
	@Size(max=64)
	@Column(nullable=true, length=64)
	protected String priority;
	
	@Size(max=2048)
	@Column(nullable=true, length=2048)
	protected String variables;
	
	protected boolean internalOnly = true;
	
	@Enumerated(EnumType.STRING)
	private DestinationType finalDestinationType;
	
	private String finalDestination;
	
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	public abstract String getApplication();
	
	public abstract List<String> getAppVariables();
	
	public boolean isInternalOnly() {
		return internalOnly;
	}

	public void setInternalOnly(boolean internalOnly) {
		this.internalOnly = internalOnly;
	}
	
	@Override
	public String getDestination() {
		return pattern;
	}
	
	@Override
	public String getDestinationName() {
		return String.format("%s - %s", pattern, description);
	}

	public DestinationType getFinalDestinationType() {
		return finalDestinationType;
	}

	public void setFinalDestinationType(DestinationType finalDestinationType) {
		this.finalDestinationType = finalDestinationType;
	}

	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}
	
	public List<String> getVariableList() {
		List<String> vars = new ArrayList<>();
		if (variables != null && !variables.equals("")) {
			String [] varray = variables.split("\n");
			for (String s : varray) {
				if (!s.trim().equals("")) vars.add(s);
			}
		}
		return vars;
	}
}
