package qinyuan.lib.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Month {

	private int year;
	private int month;

	public Month(int year, int month) {
		this.year = year;
		this.month = month;
	}

	public Month() {
		Calendar cal = new GregorianCalendar();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
	}

	public Week[] getWeeks() {
		List<Week> weekList = new ArrayList<Week>(4);

		Week week = new Week(getFirstDay());
		MyDate date = new MyDate(week.getLongDay(0));
		MyDate lastDate = new MyDate(getLastDay());

		while (MyDate.dayDiff(date, lastDate) >= 0) {
			week = new Week(date.toString());
			weekList.add(week);
			date.add(7);
		}

		Week[] weeks = new Week[weekList.size()];
		weekList.toArray(weeks);
		return weeks;
	}

	public String getFirstDay() {
		return year + "-" + getMonthStr() + "-01";
	}

	public String getLastDay() {
		return year + "-" + getMonthStr() + "-"
				+ MyDate.getDayCountOfMonth(year, month);
	}

	@Override
	public String toString() {
		return year + "-" + month;
	}

	private String getMonthStr() {
		return month < 10 ? ("0" + month) : String.valueOf(month);
	}

	public static void main(String[] args) {
		Week[] weeks = new Month().getWeeks();
		for (Week week : weeks) {
			System.out.println(week.getLongDay(0) + " " + week.getLongDay(6));
		}
	}
}
