package org.kisspbx.model.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.kisspbx.model.CustomDialplan;

@Entity
@DiscriminatorValue("CUSTOM")
public class CustomApplication extends Application {
	
	@ManyToOne
	private CustomDialplan dialplan;
	
	public CustomDialplan getDialplan() {
		return dialplan;
	}

	public void setDialplan(CustomDialplan dialplan) {
		this.dialplan = dialplan;
	}
	
	@Override
	public String getApplication() {
		return (dialplan != null)?dialplan.getName():"";
	}
	
	@Override
	public List<String> getAppVariables() {
		return new ArrayList<>();
	}
}
