package qinyuan.hrmis.domain.table;

import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.ImageUtil;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;

public class TargetPlaceTable extends BasePaginatedTable {

	public TargetPlaceTable() {
		super(new MyDataSource());
	}

	@Override
	protected String getQuery() {
		return "SELECT * FROM rec_target_place ORDER BY target_place_id";
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		tr.add(cnn.getString("name"));
		int id = cnn.getInt("target_place_id");
		tr.add(ImageUtil.getMdfImage(id) + ImageUtil.getDelImage(id));
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("工作地点");
		th.add();
	}

	@Override
	protected String getTitle() {
		return "工作地点";
	}
}
