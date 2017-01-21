package org.kisspbx.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FormInput extends SimpleTagSupport {

	private String label;
	private String field;
	private String value;
	private String type;
	private String placeholder;
	private int cols = 3;
	
	public void setLabel(String label) {
		this.label = label;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer component = new StringBuffer();
		
		if (type == null) type = "text";
		
		component.append("<div class=\"form-group\">")
			.append(String.format("<label class=\"col-md-%d control-label\" for=\"%s\">%s</label>", cols, field, label))
			.append(String.format("<div class=\"col-md-%d\">", 12 - cols))
			.append(String.format("<input type=\"%s\" class=\"form-control\" name=\"%s\" value=\"%s\" id=\"%s\"", type, field, value, field));
			if (placeholder != null) component.append(String.format(" placeholder=\"%s\" />", placeholder));
			else component.append("/>");
			component.append("</div></div>");
		getJspContext().getOut().println(component);
	}
}
