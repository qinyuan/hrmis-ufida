package qinyuan.hrmis.lib.db;

import java.util.Map;

import qinyuan.lib.db.MySQLDataSource;
import qinyuan.lib.file.PFile;

public class MyDataSource extends MySQLDataSource {

	private static Map<String, String> map = PFile.parse("data_source");

	@Override
	protected String getDataBaseName() {
		return map.get("database");
	}

	@Override
	protected String getPassword() {
		return map.get("password");
	}

	@Override
	protected String getUser() {
		return map.get("user");
	}

	@Override
	protected String getUrl() {
		String url = map.get("url");
		if (url == null) {
			return "jdbc:mysql://" + getHost() + ":3306/" + getDataBaseName();
		} else {
			return url;
		}
	}
}
