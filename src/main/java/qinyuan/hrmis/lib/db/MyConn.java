package qinyuan.hrmis.lib.db;

import java.sql.SQLException;

import qinyuan.lib.db.DataSource;
import qinyuan.lib.db.MySQLConn;

public class MyConn extends MySQLConn {

	public MyConn() throws SQLException {
		super();
	}

	@Override
	protected DataSource getDataSource() {
		return new MyDataSource();
	}
}