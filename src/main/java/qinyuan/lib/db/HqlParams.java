package qinyuan.lib.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;

public class HqlParams {

	private Map<Integer, String> strMap = new HashMap<Integer, String>();
	private Map<String, String> strStrMap = new HashMap<String, String>();
	private Map<Integer, Integer> intMap = new HashMap<Integer, Integer>();
	private Map<String, Integer> strIntMap = new HashMap<String, Integer>();

	public void setString(int index, String str) {
		strMap.put(index, str);
	}

	public void setString(String key, String value) {
		strStrMap.put(key, value);
	}

	public void setInteger(String key, Integer value) {
		strIntMap.put(key, value);
	}

	public void setInteger(int index, Integer integer) {
		intMap.put(index, integer);
	}

	public void process(Query query) {
		for (Entry<Integer, String> entry : strMap.entrySet()) {
			query.setString(entry.getKey(), entry.getValue());
		}
		for (Entry<Integer, Integer> entry : intMap.entrySet()) {
			query.setInteger(entry.getKey(), entry.getValue());
		}
		for (Entry<String, Integer> entry : strIntMap.entrySet()) {
			query.setInteger(entry.getKey(), entry.getValue());
		}
		for (Entry<String, String> entry : strStrMap.entrySet()) {
			query.setString(entry.getKey(), entry.getValue());
		}
	}
}
