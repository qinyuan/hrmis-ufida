package qinyuan.hrmis.action.user;

import java.sql.SQLException;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.web.SimpleAction;

public class DebugSqlAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		String debugSql = getString("debugSql");
		if (debugSql == null) {
			return;
		}
		if (!(debugSql.startsWith("SELECT") || debugSql.startsWith("SHOW"))) {
			setResult("SQL must starts with SELECT or SHOW");
			return;
		}
		try (MyConn cnn = new MyConn()) {
			cnn.execute(debugSql);
			while (cnn.next()) {
				setResult(cnn.getValues() + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
