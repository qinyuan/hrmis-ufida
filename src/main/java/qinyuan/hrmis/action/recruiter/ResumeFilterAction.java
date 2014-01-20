package qinyuan.hrmis.action.recruiter;

import qinyuan.lib.date.MyDate;
import qinyuan.lib.date.Week;
import qinyuan.lib.web.SimpleAction;

public class ResumeFilterAction extends SimpleAction {

	private static final long serialVersionUID = 1L;
	private static final String SESS_PREFIX = "resumeList_";

	@Override
	protected void exec() throws Exception {
		locateToday();
		locateThisWeek();

		for (String key : getParameterNames()) {
			setSession(SESS_PREFIX + key, getString(key));
		}
	}

	private void locateToday() {
		String todayType = getString("today");
		if (todayType != null) {
			String today = new MyDate().toString();
			if (todayType.equals("todayAddDate")) {
				setSession(SESS_PREFIX + "startAddDate", today);
				setSession(SESS_PREFIX + "endAddDate", today);
			} else if (todayType.equals("todayMdfDate")) {
				setSession(SESS_PREFIX + "startMdfDate", today);
				setSession(SESS_PREFIX + "endMdfDate", today);
			} else if (todayType.equals("todayHandleDate")) {
				setSession(SESS_PREFIX + "startHandleDate", today);
				setSession(SESS_PREFIX + "endHandleDate", today);
			}
		}
	}

	private void locateThisWeek() {
		String thisWeekType = getString("thisWeek");
		if (thisWeekType != null) {
			Week week = new Week();
			String weekStartDate = week.getFirstLongDay();
			String weekEndDate = week.getLastLongDay();
			if (thisWeekType.equals("thisWeekAddDate")) {
				setSession(SESS_PREFIX + "startAddDate", weekStartDate);
				setSession(SESS_PREFIX + "endAddDate", weekEndDate);
			} else if (thisWeekType.equals("thisWeekMdfDate")) {
				setSession(SESS_PREFIX + "startMdfDate", weekStartDate);
				setSession(SESS_PREFIX + "endMdfDate", weekEndDate);
			} else if (thisWeekType.equals("thisWeekHandleDate")) {
				setSession(SESS_PREFIX + "startHandleDate", weekStartDate);
				setSession(SESS_PREFIX + "endHandleDate", weekEndDate);
			}
		}
	}
}
