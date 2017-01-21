package org.kisspbx.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Category {
	
	private static final Pattern directivePattern = Pattern.compile("^#(include|tryinclude|exec)\\s+(.*)$");
	private static final Pattern codePattern = Pattern.compile("^\\s*([^=>\\s#]+)\\s*=>?(.*)$");
	
	private String name;
	private boolean isTemplate;
	private String inherits;
	private String comment;
	private List<Line> lines = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isTemplate() {
		return isTemplate;
	}
	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
	public String getInherits() {
		return inherits;
	}
	public void setInherits(String inherits) {
		this.inherits = inherits;
	}
	public List<Line> getLines() {
		return lines;
	}
	public void addRaw(String raw) {
		if (raw == null || raw.trim().equals("")) return;
		
		String [] rawLines = raw.split("\n");
		for (String s : rawLines) {
			if (s.trim().equals("")) {
				lines.add(new NewLine());
				
			} else if (s.trim().startsWith(";")) {
				int i = 1;
				while (s.charAt(i) == ';') i++;
				lines.add(new Comment(s.substring(i).trim()));
				
			} else {
				Matcher m = codePattern.matcher(s.trim());
				if (m.matches()) {
					String word = m.group(1);
					String value = m.group(2).trim();
					if (word.equals("exten") || word.equals("same") || word.equals("include")) {
						if (word.equals("same")) word = " same"; // pretier code
						lines.add(new CodeLine(word, value));
						
					} else {
						lines.add(new Line(word, value));
					}
					
				} else {
					m = directivePattern.matcher(s.trim());
					if (m.matches()) {
						lines.add(new Directive(m.group(1), m.group(2)));
					}
				}
			}
		}
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
