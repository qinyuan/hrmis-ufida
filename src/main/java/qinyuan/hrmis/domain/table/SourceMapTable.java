package qinyuan.hrmis.domain.table;

import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.ImageUtil;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.TransparentTextArea;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;

public class SourceMapTable extends BasePaginatedTable {

	public SourceMapTable() {
		super(new MyDataSource());

	}

	private int sourceId;

	public SourceMapTable setSourceId(int sourceId) {
		this.sourceId = sourceId;
		return this;
	}

	@Override
	protected String getQuery() {
		String query = "SELECT * FROM rec_source AS s "
				+ "INNER JOIN rec_source_map AS m USING(source_id)";
		if (sourceId > 0) {
			query += " WHERE source_id=" + sourceId;
		}
		query += " ORDER BY s.source_id,m.source_map_id";
		return query;
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		int sourceMapId = cnn.getInt("m.source_map_id");
		tr.add("<span id='source" + cnn.getInt("s.source_id") + "'>"
				+ cnn.getString("s.name") + "</span>");

		TransparentTextArea ta = new TransparentTextArea();
		ta.setRows(3);
		ta.setCols(55);
		ta.setText(cnn.getString("source_selector"));

		tr.add(ta);
		tr.add(cnn.getString("target_selector"));
		tr.add(cnn.getString("m.remark"));
		tr.add(ImageUtil.getMdfImage(sourceMapId)
				+ ImageUtil.getDelImage(sourceMapId));
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("简历来源", 100);
		th.add("来源选择器", 450);
		th.add("目标选择器", 80);
		th.add("备注", 80);
		th.add("", 50);
	}

	@Override
	protected String getTitle() {
		return "简历来源映射";
	}

	public static void main(String[] args) {
		System.out.println(new SourceMapTable());
	}
}
