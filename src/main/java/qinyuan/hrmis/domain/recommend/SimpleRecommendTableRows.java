package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

class SimpleRecommendTableRows extends AbstractRecommendTableRows {

	public SimpleRecommendTableRows(Recommend recommend) {
		super(recommend);
	}

	@Override
	protected String[] getLeftTds() {
		return new String[] { getDemand() };
	}

	@Override
	protected String[] getRightTds() {
		return new String[] { getFeedback() };
	}

	@Override
	protected String[] getCenterTds() throws SQLException {
		return new String[] { getDealTime(), getStatusName() };
	}
}