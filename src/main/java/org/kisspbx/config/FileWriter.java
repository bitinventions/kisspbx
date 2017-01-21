package org.kisspbx.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileWriter {
	
	private String path;
	private FileConfig file;
	private String timestamp;
	
	public FileWriter(String p, FileConfig f, String ts) {
		path = p;
		file = f;
		timestamp = ts;
	}
	
	public void write() throws IOException {
		String origFile = String.format("%s/%s", path, file.getFilename());
		String backupFile = String.format("%s/backup/%s.%s", path, file.getFilename(), timestamp);
		
		// backup file
		Files.createDirectories(Paths.get(path + "/backup/"));
		if (Files.exists(Paths.get(origFile))) {
			Files.copy(Paths.get(origFile), Paths.get(backupFile), StandardCopyOption.REPLACE_EXISTING);
			Files.delete(Paths.get(origFile));
		}
		
		// write file
		FileOutputStream fs = new FileOutputStream(new File(origFile));
		try {
			for (Category c : file.getCategories()) {
				// category label
				if (c.getComment() != null) {
					fs.write(String.format(";; %s\n", c.getComment()).getBytes());
				}
				if (c.isTemplate()) {
					fs.write(String.format("[%s](!)\n", c.getName()).getBytes());
					
				} else if (c.getInherits() != null) {
					fs.write(String.format("[%s](%s)\n", c.getName(), c.getInherits()).getBytes());
						
				} else {
					fs.write(String.format("[%s]\n", c.getName()).getBytes());
				}
				// lines
				for (Line l : c.getLines()) {
					if (l instanceof Comment) {
						fs.write(String.format(";; %s\n", l.getValue()).getBytes());
						
					} else if (l instanceof Directive) {
						fs.write(String.format("#%s %s\n", l.getVar(), l.getValue()).getBytes());
						
					} else if (l instanceof NewLine) {
						fs.write("\n".getBytes());
						
					} else if (l instanceof CodeLine) {
						fs.write(String.format("%s => %s\n", l.getVar(), l.getValue()).getBytes());
						
					} else {
						if (l.getValue() != null && !l.getValue().trim().equals(""))
							fs.write(String.format("%s=%s\n", l.getVar(), l.getValue()).getBytes());						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fs.flush();
			fs.close();
		}
	}
}
