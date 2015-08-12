package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

class RecommendEditTableRows extends AbstractRecommendTableRows {

	public RecommendEditTableRows(Recommend recommend) {
		super(recommend);
	}

	@Override
	protected String[] getLeftTds() {
		return new String[] { getDemand() };
	}

	@Override
	protected String[] getRightTds() throws SQLException {
		return new String[] { getFeedback(), getNextAction() };
	}

	@Override
	protected String[] getCenterTds() throws SQLException {
		return new String[] { getDealTime(), getStatusName(), "" };
	}

	@Override
	protected String[] getLastCenterTds() throws SQLException {
		return new String[] { getDealTime(), getStatusName(),
				"<a href='#prevStep'>撤消</a>" };
	}

}