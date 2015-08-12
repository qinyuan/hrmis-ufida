package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

public class RecommendTable extends AbstractRecommendTable {

	private int postId = 0;
	private int demandId;

	public RecommendTable setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public RecommendTable setDemandId(int demandId) {
		this.demandId = demandId;
		return this;
	}

	@Override
	protected String getTableHead() {
		String ths = "<tr>";
		ths += "<th>候选人</th>";
		ths += "<th>创建者</th>";
		ths += "<th>推送岗位</th>";
		ths += "<th colspan='2'>推荐过程</th>";
		ths += "<th>客户反馈</th>";
		ths += "</tr>";
		return ths;
	}

	@Override
	protected Recommend[] getRecommends() throws SQLException {
		if (demandId > 0) {
			return Recommend.getInstancesByDemandId(demandId);
		} else {
			return Recommend.getInstancesByPostId(postId);
		}
	}

	@Override
	protected int getTdsCount() {
		return 6;
	}

	@Override
	protected AbstractRecommendTableRows getRecommendTableRows(
			Recommend recommend) throws SQLException {
		return new RecommendTableRows(recommend);
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(new RecommendTable().setPostId(14));
	}
}