package qinyuan.hrmis.domain.table;

import java.sql.SQLException;
import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;
import static qinyuan.lib.web.html.ImageUtil.*;

public class SimpleUserTable extends BasePaginatedTable {

	public SimpleUserTable() {
		super(new MyDataSource());
	}

	@Override
	protected String getQuery() {
		return "SELECT * FROM u_user";
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		int userId = cnn.getInt("user_id");
		tr.setId("user" + userId);
		tr.add(cnn.getString("name"));
		tr.add(cnn.getString("password"));
		tr.add(getMdfImage(userId) + getDelImage(userId) + getPriImage(userId));
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("用户名", 80);
		th.add("密码", 80);
		th.add("", 60);
	}

	@Override
	protected String getTitle() {
		return "用户列表";
	}
}