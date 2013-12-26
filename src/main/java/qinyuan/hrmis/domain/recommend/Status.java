package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.lib.db.MyConn;

public class Status {

	private static Map<String, Integer> strToIntMap = new HashMap<String, Integer>();
	private static Map<Integer, String> intToStrMap = new HashMap<Integer, String>();
	private static Map<Integer, String> nextActionMap = new HashMap<Integer, String>();
	private static boolean isInited = false;

	public static String getNext(String name) throws SQLException {
		return getNext(get(name));
	}

	public static int size() throws SQLException {
		init();
		return strToIntMap.size();
	}

	public static String getNext(int id) throws SQLException {
		init();
		return nextActionMap.get(id);
	}

	public static String get(int id) throws SQLException {
		init();
		return intToStrMap.get(id);
	}

	public static int get(String name) throws SQLException {
		init();
		return strToIntMap.get(name);
	}

	private static void init() throws SQLException {
		if (isInited)
			return;
		try (MyConn cnn = new MyConn()) {
			cnn.execute("SELECT status_id,name,next_action FROM rec_status");
			while (cnn.next()) {
				Integer id = cnn.getInt(1);
				String name = cnn.getString(2);
				strToIntMap.put(name, id);
				intToStrMap.put(id, name);
				nextActionMap.put(id, cnn.getString(3));
			}
			isInited = true;
		} catch (SQLException e) {
			throw e;
		}
	}
}
