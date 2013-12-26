package qinyuan.hrmis.domain.table;

import java.sql.SQLException;
import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;
import static qinyuan.lib.web.html.ImageUtil.*;

public class SourceTable extends BasePaginatedTable {

	public SourceTable() {
		super(new MyDataSource());
	}

	@Override
	protected String getQuery() {
		return "SELECT * FROM rec_source";
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		boolean deletable = cnn.getBoolean("deletable");

		int id = cnn.getInt("source_id");
		tr.add(cnn.getString("name"));
		tr.add(cnn.getString("id_pattern"));

		tr.add(getCompressedStrTd(cnn.getString("href_prefix")));
		tr.add(getCompressedStrTd(cnn.getString("href_suffix")));
		tr.add(cnn.getString("sign"));
		tr.add(cnn.getBoolean("downloaded") ? "是" : "否");
		tr.add(deletable ? "自定义" : "系统");
		tr.add(getMdfImage(id) + (deletable ? getDelImage(id) : ""));
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("来源", 70);
		th.add("简历ID模式", 130);
		th.add("链接前缀", 130);
		th.add("链接后缀", 80);
		th.add("唯一标记", 100);
		th.add("默认已下载");
		th.add("系统/自定义", 80);
		th.add();
	}

	@Override
	protected String getTitle() {
		return "简历来源管理";
	}

	public static void main(String[] args) {
		System.out.println(new SourceTable());
	}
}