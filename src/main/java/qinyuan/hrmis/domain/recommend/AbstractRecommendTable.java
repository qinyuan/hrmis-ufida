package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

public abstract class AbstractRecommendTable {

	protected abstract String getTableHead();

	protected abstract Recommend[] getRecommends() throws SQLException;

	protected abstract int getTdsCount();

	protected abstract AbstractRecommendTableRows getRecommendTableRows(
			Recommend recommend) throws SQLException;

	@Override
	public String toString() {
		try {
			StringBuilder s = new StringBuilder();
			s.append("<table><thead>");
			s.append(getTableHead());
			s.append("</thead><tbody>");

			Recommend[] recommends = getRecommends();
			if (recommends.length == 0) {
				s.append("<tr><td colspan='" + getTdsCount()
						+ "'><i><b>无推荐记录...</b></i></td></tr>");
			} else {
				for (Recommend recommend : getRecommends()) {
					s.append(getRecommendTableRows(recommend));
				}
			}
			s.append("</tbody></table>");
			return s.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}