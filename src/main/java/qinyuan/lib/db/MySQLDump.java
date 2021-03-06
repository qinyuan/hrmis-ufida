package qinyuan.lib.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.date.MyTimeRecord;

public class MySQLDump {

	private MySQLDataSource ds;
	private String dbName;

	public MySQLDump(String dbName, MySQLDataSource ds) {
		this.dbName = dbName;
		this.ds = ds;
	}

	public void export(String fileName) {
		File file = new File(fileName);
		validateFile(file);

		try (FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				BufferedWriter bw = new BufferedWriter(osw)) {
			MySQLConn cnn = ds.getConn();
			cnn.use(dbName);

			bw.write(getHeader());
			bw.write(getCreateDatabaseCommand(dbName));

			String[] tableNames = getTableNames(cnn);
			for (String tableName : tableNames) {
				bw.write(getCreateTableCommand(cnn, tableName));
				insertValues(bw, cnn, tableName);
			}

			cnn.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getHeader() {
		StringBuilder s = new StringBuilder();
		s.append("\n");
		s.append("SET SQL_MODE=\"NO_AUTO_VALUE_ON_ZERO\";\n");
		s.append("SET time_zone = \"+00:00\";\n");
		s.append("SET GLOBAL max_allowed_packet=1024*1024*64;");
		s.append("\n");
		return s.toString();
	}

	private static void validateFile(File file) {
		String fileName = file.getAbsolutePath();
		if (!fileName.endsWith(".sql")) {
			throw new RuntimeException("incorrect sql file format " + fileName);
		}
		if (!file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("can not create file "
						+ file.getAbsolutePath());
			}
		}
	}

	private static void insertValues(BufferedWriter bw, MySQLConn cnn,
			String tableName) throws SQLException, IOException {
		bw.write("\n\n/* insert values into " + tableName + "*/\n");

		MySQLFields mf = new MySQLFields(cnn, tableName);
		cnn.execute("SELECT * FROM " + tableName);
		if (!cnn.next()) {
			return;
		}
		cnn.beforeFirst();

		bw.write(mf.getInsertClause());
		int[] types = mf.getTypes();
		boolean firstRow = true;
		while (cnn.next()) {
			if (firstRow) {
				firstRow = false;
			} else {
				bw.write(",");
			}
			bw.write("\n" + getValues(cnn, types));
		}
		bw.write(";\n\n");
	}

	private static String getValues(MySQLConn cnn, int[] types)
			throws SQLException {
		int colCount = cnn.getColCount();
		StringBuilder s = new StringBuilder("(");
		for (int i = 1; i <= colCount; i++) {
			String value = cnn.getString(i);
			if (value == null) {
				value = "NULL";
			} else {
				value = value.replace("'", "''").replace("\\", "\\\\")
						.replace("\n", "\\n");
				if (types[i - 1] != MySQLFields.INT) {
					value = "'" + value + "'";
				}
			}
			s.append(value);
			s.append(",");
		}
		if (s.charAt(s.length() - 1) == ',') {
			s.deleteCharAt(s.length() - 1);
		}
		s.append(")");
		return s.toString();
	}

	private static String getCreateDatabaseCommand(String dbName) {
		StringBuilder s = new StringBuilder("\n\n");

		s.append("/* create database */\n");
		s.append("DROP DATABASE IF EXISTS `" + dbName + "`;\n");
		s.append("CREATE DATABASE `" + dbName + "`;\n");
		s.append("USE `" + dbName + "`;\n\n");

		return s.toString();
	}

	private static String getCreateTableCommand(MySQLConn cnn, String tableName)
			throws Exception {
		// create note
		String createTableSQL = "\n\n/* create table " + tableName + " */\n";

		// create table statement
		cnn.execute("SHOW CREATE TABLE " + tableName);
		cnn.next();
		createTableSQL += cnn.getString(2) + ";\n\n";

		return createTableSQL;
	}

	private static String[] getTableNames(MySQLConn cnn) throws SQLException {
		String query = "SHOW TABLES";
		cnn.execute(query);

		String[] tables;
		tables = new String[cnn.getRowCount()];
		int i = 0;
		while (cnn.next()) {
			tables[i++] = cnn.getString(1);
		}
		return tables;
	}

	public static void main(String[] args) throws SQLException {
		MyConn cnn = new MyConn();
		cnn.close();

		MyTimeRecord r = new MyTimeRecord();
		String dbName = "hrmis";
		MySQLDump d = new MySQLDump(dbName, new MyDataSource());
		d.export("d:/test.sql");
		r.print("end");
	}

	private static class MySQLFields {
		final static int INT = 0;
		private String[] names;
		private int[] types;
		private String tableName;

		MySQLFields(MySQLConn cnn, String tableName) throws SQLException {
			this.tableName = tableName;
			cnn.execute("DESC " + tableName);
			names = new String[cnn.getRowCount()];
			types = new int[cnn.getRowCount()];
			for (int i = 0; cnn.next(); i++) {
				names[i] = cnn.getString(1);
				String type = cnn.getString(2).toLowerCase();
				types[i] = type.indexOf("int") >= 0 ? 0 : 1;
			}
		}

		private String getFields() {
			StringBuilder s = new StringBuilder("(");
			for (int i = 0; i < names.length; i++) {
				if (i > 0) {
					s.append(", ");
				}
				s.append("`" + names[i] + "`");
			}
			s.append(")");
			return s.toString();
		}

		String getInsertClause() {
			StringBuilder s = new StringBuilder("INSERT INTO `" + tableName
					+ "` ");
			s.append(getFields());
			s.append(" VALUES");
			return s.toString();
		}

		int[] getTypes() {
			return types;
		}
	}
}
