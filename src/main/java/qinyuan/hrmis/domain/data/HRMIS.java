package qinyuan.hrmis.domain.data;

import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyConn;

public class HRMIS {

	public static void execute(String query) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
		} catch (Exception e) {
			throw e;
		}
	}

	public static boolean exists(String query) {
		if (!query.substring(0, 6).toUpperCase().equals("SELECT")) {
			query = "SELECT * " + query;
		}
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			return cnn.getRowCount() > 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) {
		System.out.println(exists("from hrmis"));
	}

	public static boolean empty(String query) {
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			return cnn.getRowCount() == 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int getInt(String table, String keyField, int keyValue,
			String resultField) throws SQLException {
		return getInt("SELECT " + resultField + " FROM " + table + " WHERE "
				+ keyField + "=" + keyValue);
	}

	public static String getString(String table, String keyField, int keyValue,
			String resultField) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.execute("SELECT " + resultField + " FROM " + table + " WHERE "
					+ keyField + "=" + keyValue);
			if (cnn.next()) {
				return cnn.getString(resultField);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static int getInt(String query) throws SQLException {
		return getInts(query)[0];
	}

	public static int[] getInts(String query) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			int[] ints = new int[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				ints[i++] = cnn.getInt(1);
			}
			return ints;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static int getRowCount(String query) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			return cnn.getRowCount();
		} catch (SQLException e) {
			throw e;
		}
	}
}
