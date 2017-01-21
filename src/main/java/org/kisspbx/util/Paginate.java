package org.kisspbx.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Paginate extends SimpleTagSupport {

	private int total;
	private int size;
	private int page;
	private String clazz;

	public void setTotal(final int total) {
		this.total = total;
	}
	
	public void setSize(final int size) {
		this.size = size;
	}
	
	public void setPage(final int page) {
		this.page = page;
	}
	
	public void setClazz(final String classValue) {
		this.clazz = classValue;
	}

	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer component = new StringBuffer();
		int pages = (int)Math.ceil((double)total/size);
		if (pages <= 1) {
			
		} else if (pages <= 10) {
			component.append("<ul class=\"").append(clazz).append("\">");
			int p = 1;
			while (p <= pages) {
				component.append("<li").append((p == page)?" class=\"active\"":"")
							.append("><a href=\"?max=").append(size)
							.append("&page=").append(p).append("\">")
							.append(p).append("</a></li>");
				p++;
			}
			component.append("</ul>");
		} else {
			// TODO
		}
		getJspContext().getOut().println(component);
	}
}
