package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.domain.resume.statistics.DayResumeStatistics;
import qinyuan.hrmis.domain.resume.statistics.MonthResumeStatistics;
import qinyuan.hrmis.domain.resume.statistics.PeriodResumeStatistics;
import qinyuan.hrmis.domain.resume.statistics.WeekResumeStatistics;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.web.MyAction;

public class StatisticsAction extends MyAction {

	private static final long serialVersionUID = 1L;

	private int recruiterId;
	private int postId;

	@Override
	public String execute() {
		recruiterId = getInt("recruiterId");
		postId = getInt("postId");
		getPeriodResumeSum();
		getMonthResumeSum();
		getWeekResumeSum();
		getDayResumeSum();
		return BLANK_AJAX_INFO;
	}

	private void getPeriodResumeSum() {
		String startDate = getString("startDate");
		String endDate = getString("endDate");
		System.out.println(recruiterId);
		if (MyDate.isDate(startDate) && MyDate.isDate(endDate)) {
			setResult(new PeriodResumeStatistics(startDate, endDate).setUserId(
					recruiterId).setPostId(postId));
		}
	}

	private void getMonthResumeSum() {
		int year = getInt("year");
		if (!hasParameter("month") && year > 0) {
			setResult(new MonthResumeStatistics(year).setUserId(recruiterId)
					.setPostId(postId));
		}
	}

	private void getWeekResumeSum() {
		int year = getInt("year");
		int month = getInt("month");
		if (!hasParameter("day") && year > 0 && month > 0) {
			setResult(new WeekResumeStatistics(year, month).setUserId(
					recruiterId).setPostId(postId));
		}
	}

	private void getDayResumeSum() {
		int day = getInt("day");
		int month = getInt("month");
		int year = getInt("year");

		String date = year + "-" + month + "-" + day;
		if (MyDate.isDate(date)) {
			setResult(new DayResumeStatistics(date).setUserId(recruiterId)
					.setPostId(postId));
		}
	}
}
