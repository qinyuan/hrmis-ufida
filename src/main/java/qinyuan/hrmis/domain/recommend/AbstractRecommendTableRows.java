package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyConn;

public abstract class AbstractRecommendTableRows {

	private Recommend recommend;
	protected int rowCount;
	private MyConn cnn;

	public AbstractRecommendTableRows(Recommend recommend) {
		this.recommend = recommend;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		try {
			cnn = new MyConn();
			String query = "SELECT rs.rec_step_id,deal_time,s.name,status_id "
					+ "FROM rec_rec_step AS rs LEFT JOIN rec_status AS s "
					+ "USING(status_id) WHERE recommend_id=? "
					+ "ORDER BY rs.status_id";
			int recommendId = recommend.getId();
			cnn.prepare(query).setInt(1, recommendId).setInt(1, recommendId)
					.execute();
			rowCount = cnn.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				cnn.next();
				if (i == 0) {
					s.append("<tr id='rm" + recommendId + "'>");
					s.append(getMultiRowTds(getLeftTds()));
				} else {
					s.append("<tr>");
				}

				if (i == rowCount - 1) {
					s.append(getSingleRowTds(getLastCenterTds()));
				} else {
					s.append(getSingleRowTds(getCenterTds()));
				}

				if (i == 0)
					s.append(getMultiRowTds(getRightTds()));
				s.append("</tr>");
			}
			return s.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			cnn.close();
		}
	}

	protected abstract String[] getCenterTds() throws SQLException;

	protected String getDealTime() throws SQLException {
		int recStepId = cnn.getInt("rec_step_id");
		String str = "<span class='recStepDealTime' id='recStepDealTime"
				+ recStepId + "'>"
				+ cnn.getString("deal_time").replace(".0", "") + "</span>";
		return str;
	}

	protected String getDemand() {
		return recommend.getDemand();
	}

	protected String getFeedback() {
		String feedback = recommend.getFeedback();
		if (feedback == null || feedback.trim().isEmpty()) {
			feedback = "<i>（无）</i>";
		}
		String s = "<span class='feedback' style='display:block;width:400px'>"
				+ feedback + "</span>";
		return s.replace("\n", "\n<br />");
	}

	protected String[] getLastCenterTds() throws SQLException {
		return getCenterTds();
	}

	protected abstract String[] getLeftTds() throws SQLException;

	protected String getNextAction() throws SQLException {
		String nextAction = Status.getNext(rowCount);
		if (nextAction == null) {
			return "";
		} else {
			return "<a href='#nextStep'>" + Status.getNext(rowCount) + "</a>";
		}
	}

	protected int getRecommendId() {
		return recommend.getId();
	}

	protected int getResumeId() {
		return recommend.getResumeId();
	}

	protected abstract String[] getRightTds() throws SQLException;

	protected String getStatusName() throws SQLException {
		return cnn.getString("s.name");
	}

	protected String getTableRowStart() {
		return "<tr id='rm" + getRecommendId() + "'>";
	}

	private String getMultiRowTds(String[] ss) {
		StringBuilder o = new StringBuilder();
		for (String s : ss) {
			o.append("<td rowspan='" + rowCount + "'>" + s + "</td>");
		}
		return o.toString();
	}

	private String getSingleRowTds(String[] ss) {
		StringBuilder o = new StringBuilder();
		for (String s : ss) {
			o.append("<td>" + s + "</td>");
		}
		return o.toString();
	}
}
