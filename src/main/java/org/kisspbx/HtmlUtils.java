package org.kisspbx;

public class HtmlUtils {

	public static String encodeAsHTML(String s) {
		if (s != null) {
			return s.replaceAll("\r\n", "<br/>");
		} else {
			return "";
		}
	}
}
