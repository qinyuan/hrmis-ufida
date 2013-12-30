package qinyuan.hrmis.servlet;

import qinyuan.lib.date.MyDate;
import qinyuan.lib.date.Week;
import qinyuan.lib.web.MyServlet;

public class Update extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute() {
		if (updateIntSess("postId")) {
			setSession("demandId", Integer.valueOf(-1));
		}
		updateIntSess("recruiterId");
		updateIntSess("demandId");
		updateIntSess("sessResumeStatusId");
		updateIntSess("sessMarkStatus");
		updateIntSess("sessTargetPlaceId");

		updateStringSess("sessStartDate");
		updateStringSess("sessEndDate");
		updateStringSess("resumeStartAddDate");
		updateStringSess("resumeEndAddDate");
		updateStringSess("resumeStartMdfDate");
		updateStringSess("startHandleDate");
		updateStringSess("endHandleDate");
		updateStringSess("resumeEndMdfDate");
		updateStringSess("sessSearchName");
		updateStringSess("sessSearchTel");

		locateThisWeek();
		locateToday();

		ajax();
	}

	private void locateThisWeek() {
		String thisWeekType = getParameter("thisWeek");
		if (thisWeekType != null) {
			Week week = new Week();
			String weekStartDate = week.getFirstLongDay();
			String weekEndDate = week.getLastLongDay();
			if (thisWeekType.equals("thisWeekAddDate")) {
				updateStringSess("resumeStartAddDate", weekStartDate);
				updateStringSess("resumeEndAddDate", weekEndDate);
			} else if (thisWeekType.equals("thisWeekMdfDate")) {
				updateStringSess("resumeStartMdfDate", weekStartDate);
				updateStringSess("resumeEndMdfDate", weekEndDate);
			} else if (thisWeekType.equals("thisWeekHandleDate")) {
				updateStringSess("startHandleDate", weekStartDate);
				updateStringSess("endHandleDate", weekEndDate);
			}
		}
	}

	private void locateToday() {
		String todayType = getParameter("today");
		if (todayType != null) {
			String today = new MyDate().toString();
			if (todayType.equals("todayAddDate")) {
				updateStringSess("resumeStartAddDate", today);
				updateStringSess("resumeEndAddDate", today);
			} else if (todayType.equals("todayMdfDate")) {
				updateStringSess("resumeStartMdfDate", today);
				updateStringSess("resumeEndMdfDate", today);
			} else if (todayType.equals("todayHandleDate")) {
				updateStringSess("startHandleDate", today);
				updateStringSess("endHandleDate", today);
			}
		}
	}

	private void updateStringSess(String key) {
		String value = getParameter(key);
		if (value != null)
			setSession(key, value);
	}

	private void updateStringSess(String key, String value) {
		if (value != null)
			setSession(key, value);
	}

	private boolean updateIntSess(String key) {
		String value = getParameter(key);
		if (numeric(value)) {
			setSession(key, Integer.valueOf(value));
			return true;
		} else {
			return false;
		}
	}
}
