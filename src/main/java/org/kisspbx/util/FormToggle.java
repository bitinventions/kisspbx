package org.kisspbx.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FormToggle extends SimpleTagSupport {

	private String label;
	private String field;
	private String active;
	private String [] values;
	private String [] options;
	private int cols = 3;
	
	public void setLabel(String label) {
		this.label = label;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setActive(String value) {
		this.active = value;
	}
	
	public void setValues(String values) {
		this.values = values.split(",");
	}
	
	public void setOptions(String options) {
		this.options = options.split(",");
	}
	
	public void setCols(int cols) {
		this.cols = cols;
	}


	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer component = new StringBuffer();
		
		component.append("<div class=\"form-group\">")
			.append(String.format("<label class=\"col-md-%d control-label\" for=\"%s\">%s</label>", cols, field, label))
			.append(String.format("<div class=\"col-sm-%d\">", 12 - cols))
			.append("<div class=\"btn-group pull-right\" data-toggle=\"buttons\">");
			for(int i=0; i<options.length; i++) {
				String option = options[i];
				String optionValue = (values.length > i)?values[i]:"";
				boolean optionSelected = false;
				if (active != null && active.equals(optionValue)) optionSelected = true;
				component.append("<label class=\"btn btn-default");
				if (optionSelected) component.append(" active\">");
				else component.append("\">");
				component.append(String.format("<input type=\"radio\" name=\"%s\" value=\"%s\" autocomplete=\"off\"", field, optionValue));
				if (optionSelected) component.append(" checked/>");
				else component.append("/>");
				component.append(option).append("</label>");
			}
		component.append("</div></div></div>");
		getJspContext().getOut().println(component);
	}
}
