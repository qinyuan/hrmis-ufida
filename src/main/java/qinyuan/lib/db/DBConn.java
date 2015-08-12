package qinyuan.lib.db;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import qinyuan.lib.file.FileUtil;

public abstract class DBConn implements AutoCloseable {

	private static long LAST_CONNECT_TIME;

	private Connection cnn;
	private ResultSet rs;
	private PreparedStatement ps;
	private String charset;

	public DBConn() throws SQLException {
		cnn = getDataSource().getConnection();
		testConnection();
	}

	private void testConnection() {
		long connectTime = System.currentTimeMillis();
		long timeDiff = connectTime - LAST_CONNECT_TIME;
		if (timeDiff >= Config.getWaitTimeOut()) {
			LAST_CONNECT_TIME = connectTime;
			while (true) {
				try {
					cnn.createStatement().executeQuery("select 1");
					break;
				} catch (Exception e) {
					try {
						cnn = getDataSource().getConnection();
					} catch (Exception e1) {
					}
				}
			}
		}
		if (!Config.isUpdated()) {
			try {
				ResultSet localResultSet = cnn.createStatement().executeQuery(
						"show global variables like 'wait_timeout'");
				localResultSet.next();
				int waitTimeOut = localResultSet.getInt(2);
				Config.setWaitTimeOut(waitTimeOut);
			} catch (Exception e) {
			}
		}
	}

	protected abstract DataSource getDataSource();

	public void beforeFirst() throws SQLException {
		rs.beforeFirst();
	}

	@Override
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			cnn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() throws SQLException {
		cnn.commit();
	}

	public int execute() throws SQLException {
		rs = null;
		if (ps.execute()) {
			rs = ps.getResultSet();
		}
		return ps.getUpdateCount();
	}

	public int execute(String query) throws SQLException {
		return prepare(query).execute();
	}

	public int execute(StringBuilder query) throws SQLException {
		return execute(query.toString());
	}

	public int[] executeSource(String sourceFile) throws Exception {
		String str = FileUtil.readAll(sourceFile);
		String[] sqls = str.split(";");
		int[] ints = new int[(str.endsWith(";") ? sqls.length : sqls.length - 1)];
		for (int i = 0; i < ints.length; i++) {
			ints[i] = execute(sqls[i]);
		}
		return ints;
	}

	public boolean getBoolean(int index) throws SQLException {
		return rs.getBoolean(index);
	}

	public boolean getBoolean(String col) throws SQLException {
		return rs.getBoolean(col);
	}

	public int getColCount() throws SQLException {
		return rs.getMetaData().getColumnCount();
	}

	public double getDouble(int index) throws SQLException {
		return rs.getDouble(index);
	}

	public double getDouble(String col) throws SQLException {
		return rs.getDouble(col);
	}

	public int getInt(int index) throws SQLException {
		return rs.getInt(index);
	}

	public int getInt(String col) throws SQLException {
		return rs.getInt(col);
	}

	public int getRowCount() throws SQLException {
		rs.last();
		int rowCount = rs.getRow();
		rs.beforeFirst();
		return rowCount;
	}

	public String getString(int index) throws SQLException {
		if (charset == null) {
			return rs.getString(index);
		} else {
			return convertStr(rs.getBytes(index), charset);
		}
	}

	public String getString(String col) throws SQLException {
		if (charset == null) {
			return rs.getString(col);
		} else {
			return convertStr(rs.getBytes(col), charset);
		}
	}

	/**
	 * return String such as [value1,value2,value3]
	 * 
	 * @return
	 * @throws java.sql.SQLException
	 */
	public String getValues() throws SQLException {
		StringBuilder o = new StringBuilder("(");
		for (int i = 1; i <= getColCount(); i++) {
			o.append("'" + getString(i) + "',");
		}
		if (o.length() > 0) {
			o.deleteCharAt(o.length() - 1);
		}
		o.append(")");
		return o.toString();
	}

	public boolean next() throws SQLException {
		return rs.next();
	}

	public DBConn prepare(String query) throws SQLException {
		rs = null;
		ps = cnn.prepareStatement(query);
		return this;
	}

	public DBConn setAutoCommit(boolean bool) throws SQLException {
		cnn.setAutoCommit(bool);
		return this;
	}

	public DBConn setBoolean(int index, boolean arg) throws SQLException {
		ps.setBoolean(index, arg);
		return this;
	}

	public DBConn setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public DBConn setDouble(int index, double arg) throws SQLException {
		ps.setDouble(index, arg);
		return this;
	}

	public DBConn setInt(int index, int arg) throws SQLException {
		ps.setInt(index, arg);
		return this;
	}

	public DBConn setString(int index, String arg) throws SQLException {
		ps.setString(index, arg);
		return this;
	}

	private static String convertStr(byte[] bytes, String charset) {
		try {
			String str = new String(bytes, charset);
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
