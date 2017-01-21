package org.kisspbx.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FormArea extends SimpleTagSupport {

	private String label;
	private String field;
	private String value;
	private String rows;
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
	
	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer component = new StringBuffer();
		
		component.append("<div class=\"form-group\">")
			.append(String.format("<label class=\"col-md-%d control-label\" for=\"%s\">%s</label>", cols, field, label))
			.append(String.format("<div class=\"col-md-%d\">", 12 - cols))
			.append(String.format("<textarea rows=\"%s\" class=\"form-control\" name=\"%s\" id=\"%s\">%s</textarea>", rows, field, field, value))
			.append("</div></div>");
		getJspContext().getOut().println(component);
	}
}
