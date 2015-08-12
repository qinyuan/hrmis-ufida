package qinyuan.hrmis.domain.table;

import static qinyuan.lib.web.html.ImageUtil.getDelImage;
import static qinyuan.lib.web.html.ImageUtil.getMdfImage;

import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;

public class PostTable extends BasePaginatedTable {

	private boolean editable;

	public PostTable() {
		super(new MyDataSource());
	}

	public PostTable setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	@Override
	protected String getQuery() {
		String demandTable = "(SELECT post_id,COUNT(*) AS demand_count "
				+ "FROM rec_demand GROUP BY post_id)";
		String resumeTable = "(SELECT post_id,COUNT(*) AS resume_count "
				+ "FROM rec_resume GROUP BY post_id)";
		String recommendResumeTable = "(SELECT post_id,count(rm.recommend_id) AS recommend_count "
				+ "FROM rec_demand AS d INNER JOIN rec_recommend AS rm "
				+ "USING(demand_id) GROUP BY d.post_id)";
		String query = "SELECT * FROM rec_post AS p LEFT JOIN " + demandTable
				+ " AS d ON p.post_id=d.post_id LEFT JOIN " + resumeTable
				+ " AS r on p.post_id=r.post_id LEFT JOIN "
				+ recommendResumeTable + " AS rr on p.post_id=rr.post_id";
		return query;
	}

	private static String getNewLink(String page, int count, int postId) {
		return count == 0 ? "" : getNewLink(page + "?postId=" + postId
				+ "&recruiterId=-1", count);
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		int postId = cnn.getInt("p.post_id");
		tr.setId("post" + postId);
		tr.add(cnn.getString("p.name"));

		int demandCount = cnn.getInt("demand_count");
		tr.add(getNewLink("demand-sum.jsp", demandCount, postId));

		int resumeCount = cnn.getInt("resume_count");
		tr.add(getNewLink("resumeList.jsp", resumeCount, postId));

		int recommendCount = cnn.getInt("recommend_count");
		tr.add(getNewLink("recommend-list.jsp", recommendCount, postId));

		if (editable) {
			tr.add(getMdfImage(postId) + getDelImage(postId));
		}
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("职位类型", 100);
		th.add("需求总量", 100);
		th.add("录入简历量", 100);
		th.add("推送量", 100);
		if (editable) {
			th.add("", 50);
		}
	}

	@Override
	protected String getTitle() {
		return "岗位类型";
	}

	public static void main(String[] args) {
		System.out.println(new PostTable());
	}
}