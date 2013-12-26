package qinyuan.lib.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyTime {

	private int hour;
	private int minute;
	private int second;

	public MyTime() {
		Calendar cal = new GregorianCalendar();
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
	}

	public MyTime(String timeValue) {
		String[] timeArr = timeValue.split(":");
		hour = Integer.parseInt(timeArr[0]);
		minute = Integer.parseInt(timeArr[1]);
		second = Integer.parseInt(timeArr[2]);
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	/**
	 * return String likes 08:08:08
	 */
	public String toString() {
		String strHour = pad(hour);
		String strMinute = pad(minute);
		String strSecond = pad(second);
		return strHour + ":" + strMinute + ":" + strSecond;
	}
	
	private static String pad(int num) {
		return num < 10 ? "0" + String.valueOf(num) : String.valueOf(num);
	}

	public static double hourDiff(String startTime, String endTime) {
		return hourDiff(new MyTime(startTime), new MyTime(endTime));
	}

	public static double hourDiff(MyTime startTime, MyTime endTime) {
		double result = endTime.getHour() - startTime.getHour();
		result += (endTime.getMinute() - startTime.getMinute()) / 60.0;
		result += (endTime.getSecond() - startTime.getSecond()) / 3600.0;
		return result;
	}
}
