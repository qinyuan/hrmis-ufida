package qinyuan.hrmis.domain.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.file.PFile;

public class Privilege {

	private final static String PROJ = PFile.parse("page").get("proj");

	public final static Map<Integer, Privilege> priMap = new HashMap<Integer, Privilege>();
	static {
		for (Entry<String, String> entry : PFile.parse("privilege").entrySet()) {
			Integer key = Integer.valueOf(entry.getKey());
			Privilege value = new Privilege(entry.getValue());
			priMap.put(key, value);
		}
	}

	private String title;
	private String base;
	private Map<String, String> map;
	private List<String> keyList = new ArrayList<String>();

	Privilege(String filePath) {
		map = PFile.parse(filePath, keyList);
		title = map.get("title");
		base = map.get("base");
	}

	public String getTitle() {
		return title;
	}

	public String getPattern() {
		String pattern = "/" + PROJ + "/" + base + "/.*";
		return pattern.replace("/{2,}", "/");
	}

	public String[] getAnchors() {
		List<String> list = new ArrayList<String>();
		for (String key : keyList) {
			if (!key.equals("title") && !key.endsWith("base")) {
				String link = map.get(key);
				if (base != null) {
					link = "/" + PROJ + "/" + base + "/" + link;
				}
				link = link.replaceAll("/{2,}", "/");
				list.add(createAnchor(key, link));
			}
		}

		String[] strs = new String[list.size()];
		list.toArray(strs);
		return strs;
	}

	public static int[] getPrisByUserId(int userId) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			String query = "SELECT pri_id FROM u_privilege WHERE user_id=? "
					+ "ORDER BY pri_id";
			cnn.prepare(query).setInt(1, userId).execute();
			int[] pris = new int[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				pris[i++] = cnn.getInt(1);
			}
			return pris;
		} catch (SQLException e) {
			throw e;
		}
	}

	private static String createAnchor(String text, String link) {
		return "<a href='" + link + "'>" + text + "</a>";
	}
}
