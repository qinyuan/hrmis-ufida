package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

import qinyuan.hrmis.lib.db.MyConn;

class RecommendTableRows extends AbstractRecommendTableRows {

	public RecommendTableRows(Recommend recommend) {
		super(recommend);
	}

	@Override
	protected String[] getLeftTds() throws SQLException {
		try (MyConn cnn = new MyConn()) {
			String[] strs = new String[3];
			String query = "SELECT applicant,u.name FROM rec_resume AS r "
					+ "INNER JOIN u_user AS u ON r.creator_id=u.user_id AND r.resume_id="
					+ getResumeId();
			cnn.execute(query);
			cnn.next();
			strs[0] = cnn.getString("applicant");
			strs[1] = cnn.getString("u.name");
			strs[2] = getDemand();
			return strs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	protected String[] getRightTds() {
		return new String[] { getFeedback() };
	}

	@Override
	protected String[] getCenterTds() throws SQLException {
		return new String[] { getDealTime(), getStatusName() };
	}

	public int getTdsCount() throws SQLException {
		return getLeftTds().length + getRightTds().length
				+ getCenterTds().length;
	}
}