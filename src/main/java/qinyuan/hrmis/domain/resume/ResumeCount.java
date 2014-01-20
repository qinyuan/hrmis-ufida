package qinyuan.hrmis.domain.resume;

import java.sql.SQLException;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.db.SQLCreator;

public class ResumeCount {

	private int statusId;
	private int userId;
	private int postId;
	private int targetPlaceId;
	private int demandId;
	private String startDate;
	private String endDate;

	public ResumeCount setTargetPlaceId(int targetPlaceId) {
		this.targetPlaceId = targetPlaceId;
		return this;
	}

	public ResumeCount setDemandId(int demandId) {
		this.demandId = demandId;
		return this;
	}

	public ResumeCount setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}

	public ResumeCount setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public ResumeCount setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public ResumeCount setStartDate(String startDate) {
		if (MyDate.isDate(startDate))
			this.startDate = startDate;
		else
			this.startDate = null;
		return this;
	}

	public ResumeCount setEndDate(String endDate) {
		if (MyDate.isDate(endDate))
			this.endDate = endDate;
		else
			this.endDate = null;
		return this;
	}

	public int getDownloadedCount() throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.execute(getDownloadedResumeCountQuery().toString());
			cnn.next();
			return cnn.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int getCount() throws SQLException {
		try (MyConn cnn = new MyConn()) {
			if (statusId > 0)
				cnn.execute(getRecStepCountQuery());
			else
				cnn.execute(getResumeCountQuery().toString());

			cnn.next();
			return cnn.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getCountAnchor() throws SQLException {
		int count = getCount();
		if (count == 0)
			return "";

		StringBuilder s = new StringBuilder();
		s.append("<a target='_blank' href='resumeList.jsp?useSessionParam=false&");
		if (startDate != null) {
			if (statusId == 0) {
				s.append("startAddDate=" + startDate + "&");
			} else {
				s.append("startHandleDate=" + startDate + "&");
			}
		}
		if (endDate != null) {
			if (statusId == 0) {
				s.append("endAddDate=" + endDate + "&");
			} else {
				s.append("endHandleDate=" + endDate + "&");
			}
		}
		if (userId > 0)
			s.append("recruiterId=" + userId + "&");
		if (postId > 0)
			s.append("postId=" + postId + "&");
		s.append("statusId=" + statusId + "'>" + count + "</a>");
		return s.toString();
	}

	public String[] getRecommendApplicant() throws SQLException {
		if (statusId <= 0) {
			return new String[0];
		}

		String query = "select r.applicant from rec_resume as r "
				+ "inner join rec_recommend as rm ON r.resume_id = rm.resume_id "
				+ "inner join rec_rec_step as rs ON rm.recommend_id = rs.recommend_id "
				+ "inner join rec_demand as d ON d.demand_id = rm.demand_id";
		query += " WHERE rs.status_id=" + statusId;

		if (userId > 0) {
			query += " AND r.tracer_id=" + userId;
		}
		if (postId > 0) {
			query += " AND d.post_id=" + postId;
		}
		if (targetPlaceId > 0) {
			query += " AND r.target_place_id=" + targetPlaceId;
		}
		if (demandId > 0) {
			query += " AND d.demand_id=" + demandId;
		}
		if (startDate != null) {
			query += " AND DATE(rs.deal_time)>='" + startDate + "'";
		}
		if (endDate != null) {
			query += " AND DATE(rs.deal_time)<='" + endDate + "'";
		}

		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			String[] strs = new String[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				strs[i++] = cnn.getString("r.applicant");
			}
			return strs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	private String getRecStepCountQuery() {
		SQLCreator sc = new SQLCreator("rec_resume");
		sc.addFields("resume_id");
		if (userId > 0)
			sc.addWhereClause("tracer_id=" + userId);
		if (postId > 0)
			sc.addWhereClause("post_id=" + postId);
		if (targetPlaceId > 0)
			sc.addWhereClause("target_place_id=" + targetPlaceId);

		String query = "SELECT COUNT(*) FROM rec_rec_step "
				+ "WHERE status_id=" + statusId;
		if (startDate != null)
			query += " AND DATE(deal_time)>='" + startDate + "'";
		if (endDate != null)
			query += " AND DATE(deal_time)<='" + endDate + "'";
		query += " AND recommend_id IN (SELECT recommend_id FROM rec_recommend "
				+ "WHERE resume_id IN (" + sc + ")";
		if (demandId > 0) {
			query += " AND demand_id=" + demandId;
		}
		query += ")";
		return query;
	}

	private SQLCreator getDownloadedResumeCountQuery() {
		SQLCreator sc = getResumeCountQuery();
		sc.addWhereClause("downloaded=TRUE");
		return sc;
	}

	private SQLCreator getResumeCountQuery() {
		SQLCreator sc = new SQLCreator("rec_resume");
		sc.addFields("COUNT(*)");
		if (startDate != null)
			sc.addWhereClause("DATE(add_time)>='" + startDate + "'");
		if (endDate != null)
			sc.addWhereClause("DATE(add_time)<='" + endDate + "'");
		if (userId > 0)
			sc.addWhereClause("tracer_id=" + userId);
		if (postId > 0)
			sc.addWhereClause("post_id=" + postId);
		if (targetPlaceId > 0)
			sc.addWhereClause("target_place_id=" + targetPlaceId);
		return sc;
	}
}