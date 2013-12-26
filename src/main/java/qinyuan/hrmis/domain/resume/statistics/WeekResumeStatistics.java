package qinyuan.hrmis.domain.resume.statistics;

import java.sql.SQLException;
import qinyuan.lib.date.Month;
import qinyuan.lib.date.Week;

public class WeekResumeStatistics extends ResumeStatistics {

	private Month month;

	public WeekResumeStatistics(int year, int month) {
		this.month = new Month(year, month);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		try {
			for (Week week : month.getWeeks()) {
				s.append(getPeriodRow(week.getLongDay(0), week.getLongDay(6),
						QueryButtons.DAY).append(
						"&nbsp;&nbsp;" + week.getShortDay(0) + "<br />&nbsp;~"
								+ week.getShortDay(6)).setClass("detailRow"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s.toString();
	}
}