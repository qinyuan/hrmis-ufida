package qinyuan.lib.web;

import java.util.Map;
import java.util.Map.Entry;

public class JsUtil {

	public static String toJsObject(Map<String, String> map) {
		StringBuilder s = new StringBuilder();

		s.append("{");
		for (Entry<String, String> entry : map.entrySet()) {
			s.append('"' + entry.getKey() + "\":");
			if (entry.getValue() == null) {
				s.append("null,");
			} else {
				s.append("\"" + entry.getValue() + "\",");
			}
		}
		if (s.charAt(s.length() - 1) == ',') {
			s.deleteCharAt(s.length() - 1);
		}
		s.append("}");

		return s.toString();
	}
}
