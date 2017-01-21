package org.kisspbx.config;

public class Line {
	private String var;
	private String value;
	
	protected Line() { }
	
	public Line(String var, String value) {
		this.var = var;
		this.value = value;
	}
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
