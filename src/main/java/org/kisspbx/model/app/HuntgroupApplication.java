package org.kisspbx.model.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.kisspbx.model.Extension;
import org.kisspbx.model.HuntGroup;

@Entity
@DiscriminatorValue("HUNTGROUP")
public class HuntgroupApplication extends Application {
	
	@ManyToOne
	private HuntGroup huntgroup;
	
	public HuntgroupApplication() {
		extension = "s";
		priority = "1";
	}

	public HuntGroup getHuntgroup() {
		return huntgroup;
	}

	public void setHuntgroup(HuntGroup huntgroup) {
		this.huntgroup = huntgroup;
	}

	@Override
	public String getApplication() {
		return "huntgroup-feature";
	}
	
	@Override
	public List<String> getAppVariables() {
		List<String> vars = new ArrayList<>();
		if (huntgroup.getPriorityExtension() != null)
			vars.add("PRIOTITY_EXTEN=" + huntgroup.getPriorityExtension().getDialString());
		
		if (huntgroup.getVoicemailExtension() != null) 
			vars.add("VOICEMAIL=" + huntgroup.getVoicemailExtension().getDialString());
		
		StringBuffer sb = new StringBuffer();
		for (Extension e : huntgroup.getExtensionGroup()) {
			if (sb.length() > 0) sb.append("&");
			sb.append(e.getType().toString()).append("/").append(e.getUsername());
		}
		if (sb.length() > 0) vars.add("GROUP=" + sb.toString());
		vars.add("STRATEGY="+huntgroup.getStrategy());
		vars.add("DIALTIME="+huntgroup.getDialtime());
		vars.add("TIMEOUT="+huntgroup.getTimeout());
		vars.add("TIMES="+huntgroup.getTimes());
		return vars;
	}
}
