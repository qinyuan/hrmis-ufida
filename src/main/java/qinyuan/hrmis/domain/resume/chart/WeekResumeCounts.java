package qinyuan.hrmis.domain.resume.chart;

import qinyuan.lib.date.Week;

public class WeekResumeCounts extends ResumeCounts {

	private Week[] getWeeks() {
		Week startWeek = new Week(getStartDate());
		Week endWeek = new Week(getEndDate());
		int weekCount = Week.getWeekDiff(startWeek, endWeek) + 1;
		Week[] weeks = new Week[weekCount];
		for (int i = 0; i < weekCount; i++) {
			weeks[i] = new Week(startWeek.getWeekNum());
			startWeek.add(1);
		}
		return weeks;
	}

	@Override
	public Period[] getPeriods() {
		Week[] weeks = getWeeks();
		Period[] periods = new Period[weeks.length];
		for (int i = 0; i < weeks.length; i++) {
			Week week = weeks[i];
			periods[i] = new Period(week.getFirstLongDay(),
					week.getLastLongDay());
		}
		return periods;
	}

	@Override
	public String[] getCategories() {
		Week[] weeks = getWeeks();
		String[] strs = new String[weeks.length];
		for (int i = 0; i < weeks.length; i++) {
			Week week = weeks[i];
			strs[i] = week.getShortDay(0) + "~" + week.getShortDay(6);
		}
		return strs;
	}
}