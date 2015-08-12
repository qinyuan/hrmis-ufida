package qinyuan.hrmis.domain.resume.statistics;

import java.sql.SQLException;
import qinyuan.lib.date.Week;

public class DayResumeStatistics extends ResumeStatistics {

	private Week week;

	public DayResumeStatistics(String date) {
		this.week = new Week(date);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		try {
			for (int i = 0; i < 7; i++) {
				String date = week.getLongDay(i);
				s.append(getPeriodRow(date, date).append(
						"&nbsp;&nbsp;" + Week.getWeekName(i)).setClass(
						"detailRow"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s.toString();
	}
}