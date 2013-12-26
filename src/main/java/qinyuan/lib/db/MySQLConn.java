package qinyuan.lib.db;

import java.sql.SQLException;

public abstract class MySQLConn extends DBConn {

	public MySQLConn() throws SQLException {
		super();
	}

	public MySQLConn use(String database) throws SQLException {
		execute("USE " + database);
		return this;
	}

	public int getLastInsertId() throws SQLException {
		this.execute("SELECT LAST_INSERT_ID()");
		this.next();
		return getInt(1);
	}
}
