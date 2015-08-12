package qinyuan.hrmis.domain.resume.statistics;

public class PeriodResumeStatistics extends ResumeStatistics {

	private String startDate;
	private String endDate;

	public PeriodResumeStatistics(String startDate, String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		try {
			StringBuilder s = new StringBuilder("<table>");
			s.append("<thead>" + getTableHead() + "</thead>");
			s.append("<tbody>");
			s.append(getPeriodRow(startDate, endDate, QueryButtons.NONE));
			s.append("</tbody>");
			s.append("</table>");
			return s.toString().replace("<th></th>", "");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}