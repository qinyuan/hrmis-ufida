package qinyuan.hrmis.domain.resume.statistics;

import java.sql.SQLException;
import qinyuan.lib.date.Month;

public class MonthResumeStatistics extends ResumeStatistics {

	private int year;

	public MonthResumeStatistics(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		try {
			for (int i = 1; i <= 12; i++) {
				Month mon = new Month(year, i);
				s.append(getPeriodRow(mon.getFirstDay(), mon.getLastDay(),
						QueryButtons.WEEK).append("&nbsp;&nbsp;" + i + "月")
						.setClass("detailRow"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s.toString();
	}
}