package qinyuan.hrmis.domain.resume.statistics;

import java.sql.SQLException;
import qinyuan.hrmis.domain.recommend.Status;
import qinyuan.hrmis.domain.resume.ResumeCount;
import qinyuan.lib.web.html.Anchor;
import qinyuan.lib.web.html.TableRow;

public class ResumeStatisticsRow {

	private int userId;
	private int postId;
	private String startDate;
	private String endDate;

	public ResumeStatisticsRow setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public ResumeStatisticsRow setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public ResumeStatisticsRow setEndDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public ResumeStatisticsRow setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public TableRow toTableRow() throws SQLException {
		return toTableRow(QueryButtons.NONE);
	}

	public TableRow toTableRow(QueryButtons buttonsType) throws SQLException {
		try {
			TableRow tr = new TableRow();

			ResumeCount rc = new ResumeCount();
			rc.setStatusId(0);
			rc.setUserId(userId);
			rc.setStartDate(startDate);
			rc.setPostId(postId);
			rc.setEndDate(endDate);

			tr.add(rc.getCountAnchor());
			for (int statusId = 1; statusId <= Status.size(); statusId++) {
				rc.setStatusId(statusId);
				tr.add(rc.getCountAnchor());
			}

			switch (buttonsType) {
			case EMPTY:
				tr.add("");
				break;
			case WEEK:
				tr.add(getButtons("week", startDate, endDate));
				break;
			case DAY:
				tr.add(getButtons("day", startDate, endDate));
				break;
			default:
				break;
			}

			return tr;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static String getButtons(String type, String startDate,
			String endDate) {
		StringBuilder s = new StringBuilder();
		Anchor a = new Anchor();
		a.setHref("#").setAttr(
				"data-options",
				"style:\"" + type + "\",startDate:\"" + startDate
						+ "\",endDate:\"" + endDate + "\"").setText("统计图");
		s.append(a.toString());
		s.append(" ");
		a.setAttr("data-options", "style:\"" + type + "Table\",startDate:\""
				+ startDate + "\",endDate:\"" + endDate + "\"").setText("统计表");
		s.append(a.toString());
		return s.toString();
	}
}