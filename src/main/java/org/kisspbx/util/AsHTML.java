package org.kisspbx.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AsHTML extends SimpleTagSupport {

	private String value;

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		String formatted = "";
		if (value != null) {
			formatted = value.replaceAll("\r", "");
			formatted = value.replaceAll("\n", "<br/>");
		}
		getJspContext().getOut().println(formatted);
	}
}
