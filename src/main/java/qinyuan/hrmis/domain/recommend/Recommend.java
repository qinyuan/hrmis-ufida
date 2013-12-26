package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

import qinyuan.hrmis.dao.SimpleDemandDao;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.date.MyDateTime;

public class Recommend {

	private int id;
	private int resumeId;
	private int demandId;
	private String demand;
	private String feedback;

	public int getId() {
		return id;
	}

	public int getResumeId() {
		return resumeId;
	}

	public int getDemandId() {
		return demandId;
	}

	public String getDemand() {
		return demand;
	}

	public String getFeedback() {
		return feedback;
	}

	public static Recommend getInstance(int recommendId) throws SQLException {
		Recommend[] recommends = getInstancesByWhereClause("recommend_id="
				+ recommendId);
		return recommends.length > 0 ? recommends[0] : null;
	}

	public static Recommend[] getInstancesByPostId(int postId)
			throws SQLException {
		String whereClause;
		if (postId > 0) {
			whereClause = "demand_id IN (SELECT demand_id FROM rec_demand WHERE post_id="
					+ postId + ")";
		} else {
			whereClause = "";
		}
		return getInstancesByWhereClause(whereClause);
	}

	public static Recommend[] getInstancesByDemandId(int demandId)
			throws SQLException {
		return getInstancesByWhereClause("demand_id=" + demandId);
	}

	public static Recommend[] getInstancesByResumeId(int resumeId)
			throws SQLException {
		return getInstancesByWhereClause("resume_id=" + resumeId);
	}

	public static void mdfFeedback(int recommendId, String feedback)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare(
					"UPDATE rec_recommend SET feedback=? WHERE recommend_id=?")
					.setString(1, feedback).setInt(2, recommendId).execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void nextStep(int recommendId) throws SQLException {
		int maxStatusId = getMaxStatusId(recommendId);
		String query = "INSERT INTO rec_rec_step"
				+ "(recommend_id,status_id,deal_time) VALUES(" + recommendId
				+ "," + (maxStatusId + 1) + ",'" + new MyDateTime() + "')";
		HRMIS.execute(query);
	}

	public static void prevStep(int recommendId) throws SQLException {
		int maxStatusId = getMaxStatusId(recommendId);
		String query = "DELETE FROM rec_rec_step WHERE recommend_id="
				+ recommendId + " AND status_id=" + maxStatusId;
		HRMIS.execute(query);

		if (maxStatusId == 1) {
			HRMIS.execute("DELETE FROM rec_recommend WHERE recommend_id="
					+ recommendId);
		}
	}

	private static Recommend getInstanceByConn(MyConn cnn) throws SQLException {
		Recommend r = new Recommend();
		r.id = cnn.getInt("recommend_id");
		r.resumeId = cnn.getInt("resume_id");
		r.demandId = cnn.getInt("demand_id");
		r.demand = SimpleDemandDao.getFullDemandName(r.demandId);
		r.feedback = cnn.getString("feedback");
		return r;
	}

	private static Recommend[] getInstancesByWhereClause(String whereClause)
			throws SQLException {
		String query = "SELECT recommend_id,resume_id,demand_id,feedback "
				+ "FROM rec_recommend";
		if (!whereClause.trim().isEmpty()) {
			query += " WHERE " + whereClause;
		}
		query += " ORDER BY recommend_id DESC";
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			Recommend[] recommends = new Recommend[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				recommends[i++] = getInstanceByConn(cnn);
			}
			return recommends;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static int getMaxStatusId(int recommendId) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare(
					"SELECT MAX(status_id) FROM rec_rec_step WHERE recommend_id=?")
					.setInt(1, recommendId).execute();
			cnn.next();
			return cnn.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
