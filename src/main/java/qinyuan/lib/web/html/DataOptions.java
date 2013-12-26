package qinyuan.lib.web.html;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DataOptions {

	private Map<String, String> map = new HashMap<String, String>();

	public DataOptions add(String key, String value) {
		map.put(key, value);
		return this;
	}

	public DataOptions add(String key, Object value) {
		return add(key, value.toString());
	}

	@Override
	public String toString() {
		return "data-options='" + getBody() + "'";
	}

	public String getBody() {
		StringBuilder s = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			s.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
		}
		if (s.charAt(s.length() - 1) == ',')
			s.deleteCharAt(s.length() - 1);
		return s.toString();
	}

	public static void main(String[] args) {
		DataOptions dp = new DataOptions();
		dp.add("key1", "value1");
		dp.add("key2", "value2");
		System.out.println(dp);
	}
}
