package org.kisspbx.config;

import java.util.ArrayList;
import java.util.List;

public class FileConfig {
	
	private String filename;
	private List<Category> categories = new ArrayList<>();
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public Category getOrCreate(String name) {
		Category cat = null;
		for (Category c : categories) {
			if (c.getName().equals(name)) {
				cat = c;
				break;
			}
		}
		if (cat == null) {
			cat = new Category();
			cat.setName(name);
			categories.add(cat);
		}
		return cat;
	}
}
