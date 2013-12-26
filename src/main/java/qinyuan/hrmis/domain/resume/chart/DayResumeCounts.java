package qinyuan.hrmis.domain.resume.chart;

import qinyuan.lib.date.MyDate;

public class DayResumeCounts extends ResumeCounts {

	@Override
	public Period[] getPeriods() {
		MyDate[] dates = getDates();
		Period[] periods = new Period[dates.length];
		for (int i = 0; i < dates.length; i++) {
			String dateStr = dates[i].toString();
			periods[i] = new Period(dateStr, dateStr);
		}
		return periods;
	}

	private MyDate[] getDates() {
		return MyDate.newInstances(getStartDate(), getEndDate());
	}

	@Override
	public String[] getCategories() {
		MyDate[] dates = getDates();
		String[] strs = new String[dates.length];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = dates[i].getShortStr();
		}
		return strs;
	}
	
	public static void main(String[] args) {
		DayResumeCounts drc=new DayResumeCounts();
		System.out.println(drc.toString());
	}
}