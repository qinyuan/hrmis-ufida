package qinyuan.lib.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public abstract class DataSource {

	private static ComboPooledDataSource ds;

	protected abstract String getUser();

	protected abstract String getUrl();

	protected abstract String getDriver();

	protected abstract String getPassword();

	public DBConn getConn() throws SQLException {
		return new DBConn() {
			@Override
			protected DataSource getDataSource() {
				return DataSource.this;
			}
		};
	}

	protected Connection getConnection() throws SQLException {
		if (ds == null) {
			try {
				ds = new ComboPooledDataSource();
				ds.setUser(getUser());
				ds.setPassword(getPassword());
				ds.setJdbcUrl(getUrl());
				ds.setDriverClass(getDriver());
				ds.setInitialPoolSize(2);
				ds.setMinPoolSize(1);
				ds.setMaxPoolSize(10);
				ds.setMaxStatements(50);
				ds.setMaxIdleTime(60);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return ds.getConnection();
	}
}