package qinyuan.lib.db;

import java.sql.SQLException;

public abstract class MySQLDataSource extends DataSource {

	@Override
	protected String getUser() {
		return "root";
	}

	protected abstract String getDataBaseName();

	protected String getHost() {
		return "localhost";
	}

	@Override
	protected String getUrl() {
		return "jdbc:mysql://" + getHost() + ":3306/" + getDataBaseName();
	}

	@Override
	final protected String getDriver() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public MySQLConn getConn() throws SQLException {
		return new MySQLConn() {
			@Override
			protected DataSource getDataSource() {
				return MySQLDataSource.this;
			}
		};
	}
}
