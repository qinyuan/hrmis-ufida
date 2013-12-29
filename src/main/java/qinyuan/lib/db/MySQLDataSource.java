package qinyuan.lib.db;

import java.sql.SQLException;

public abstract class MySQLDataSource extends DataSource {

	@Override
	protected String getUser() {
		return HibernateUtil.getUsername();
	}

	@Override
	protected String getPassword() {
		return HibernateUtil.getPassword();
	}

	@Override
	protected String getUrl() {
		return HibernateUtil.getUrl();
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