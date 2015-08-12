package qinyuan.hrmis.domain.table;

import java.sql.SQLException;
import qinyuan.hrmis.dao.CustomerDao;
import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;

public class DemandTable extends BasePaginatedTable {

	private int postId;

	public DemandTable() {
		super(new MyDataSource());
	}

	public DemandTable setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	@Override
	protected String getQuery() {
		String resumeTable = "(SELECT demand_id,COUNT(*) AS resume_count "
				+ "FROM rec_recommend GROUP BY demand_id)";
		String query = "SELECT * FROM rec_demand as d "
				+ "INNER JOIN rec_post AS p ON d.post_id=p.post_id "
				+ "INNER JOIN rec_customer AS c ON d.customer_id=c.customer_id "
				+ "LEFT JOIN " + resumeTable
				+ " AS r ON d.demand_id=r.demand_id";
		if (postId > 0) {
			query += " WHERE d.post_id=" + postId;
		}
		query += " ORDER BY d.post_id,d.customer_id";
		return query;
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		int demandId = cnn.getInt("d.demand_id");
		tr.setId("demand" + demandId);
		tr.add(cnn.getString("p.name"));
		tr.add(getNewLink("demand-remark.jsp?demandId=" + demandId,
				cnn.getString("d.name")));
		tr.add(CustomerDao.getFullCusName(cnn.getInt("customer_id")));

		int resumeCount = cnn.getInt("resume_count");
		if (resumeCount != 0) {
			tr.add(getNewLink("recommend-list.jsp?postId=-1&demandId="
					+ demandId, resumeCount));
		} else {
			tr.add("");
		}
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("职位类型", 100);
		th.add("需求名称", 100);
		th.add("客户", 200);
		th.add("推送量", 80);
	}

	@Override
	protected String getTitle() {
		return "需求汇总";
	}

	public static void main(String[] args) {
		System.out.println(new DemandTable());
	}
}