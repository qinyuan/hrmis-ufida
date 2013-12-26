package qinyuan.lib.db;

public abstract class MDBDataSource extends DataSource {

	@Override
	protected String getUser() {
		return "";
	}

	protected abstract String getFilePath();

	@Override
	protected String getUrl() {
		return "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="
				+ getFilePath();
	}

	@Override
	protected String getDriver() {
		return "sun.jdbc.odbc.JdbcOdbcDriver";
	}

	@Override
	protected String getPassword() {
		return "";
	}
}