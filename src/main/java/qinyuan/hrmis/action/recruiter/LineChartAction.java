package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.domain.resume.chart.DayChart;
import qinyuan.hrmis.domain.resume.chart.ResumeChart;
import qinyuan.hrmis.domain.resume.chart.WeekChart;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.web.MyAction;

public class LineChartAction extends MyAction {

	private static final long serialVersionUID = 1L;

	private int recruiterId;
	private int postId;
	private String startDate;
	private String endDate;

	@Override
	public String execute() {
		startDate = getString("startDate");
		if (!MyDate.isDate(startDate)) {
			setResult("开始日期未设置");
			return BLANK_AJAX_INFO;
		}

		endDate = getString("endDate");
		if (!MyDate.isDate(endDate)) {
			setResult("结束日期未设置");
			return BLANK_AJAX_INFO;
		}

		String style = getString("style");
		if (style == null) {
			return BLANK_AJAX_INFO;
		}

		recruiterId = getInt("recruiterId");
		postId = getInt("postId");

		ResumeChart rc = null;
		if (style.startsWith("week")) {
			rc = new WeekChart();
		} else if (style.startsWith("day")) {
			rc = new DayChart();
		}

		if (rc != null) {
			rc.setPostId(postId);
			rc.setUserId(recruiterId);
			rc.setStartDate(startDate);
			rc.setEndDate(endDate);
			if (style.endsWith("Table")) {
				setResult(rc.getTable());
			} else {
				setResult(rc.toString());
			}
		}
		return BLANK_AJAX_INFO;
	}
}
