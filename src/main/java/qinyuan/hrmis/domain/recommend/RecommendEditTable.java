package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

public class RecommendEditTable extends AbstractRecommendTable {

	private int resumeId;

	public RecommendEditTable(int resumeId) {
		this.resumeId = resumeId;
	}

	@Override
	protected String getTableHead() {
		String ths = "<tr>";
		ths += "<th>推送岗位</th>";
		ths += "<th colspan='3'>推荐过程</th>";
		ths += "<th>客户反馈（双击可修改）</th>";
		ths += "<th>下一步</th>";
		ths += "</tr>";
		return ths;
	}

	@Override
	protected Recommend[] getRecommends() throws SQLException {
		return Recommend.getInstancesByResumeId(resumeId);
	}

	@Override
	protected int getTdsCount() {
		return 6;
	}

	@Override
	protected AbstractRecommendTableRows getRecommendTableRows(
			Recommend recommend) throws SQLException {
		return new RecommendEditTableRows(recommend);
	}
}