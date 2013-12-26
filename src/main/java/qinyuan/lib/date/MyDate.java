package qinyuan.lib.date;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate {
	private static int DAY_COUNT_OF_COMMON[] = new int[] { 31, 28, 31, 30, 31,
			30, 31, 31, 30, 31, 30, 31 };
	private static int DAY_COUNT_OF_LEAP[] = new int[] { 31, 29, 31, 30, 31,
			30, 31, 31, 30, 31, 30, 31 };
	private Calendar cal;

	public MyDate() {
		cal = new GregorianCalendar();
	}

	/**
	 * Constructor, receive a date string as parameter, the format of date
	 * string should likes 2000-1-1
	 * 
	 * @param dateString
	 * @throws DateFormatException
	 */
	public MyDate(String dateString) {
		if (isDate(dateString)) {
			int[] dateArray = ConvertToDateArray(dateString);
			cal = new GregorianCalendar(dateArray[0], dateArray[1] - 1,
					dateArray[2]);
		} else {
			throw new RuntimeException("Incorrect date format: " + dateString);
		}
	}

	public int compareTo(MyDate otherDate) {
		return dayDiff(this, otherDate);
	}

	public void add(int daysCount) {
		cal.add(Calendar.DATE, daysCount);
	}

	@Override
	public MyDate clone() {
		return new MyDate(this.toString());
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof MyDate) {
			return getTimeStamp() == ((MyDate) other).getTimeStamp();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(getTimeStamp()).hashCode();
	}

	public int getTimeStamp() {
		return (int) (cal.getTimeInMillis() / 1000);
	}

	public String getShortStr() {
		return getLongStr().substring(5);
	}

	public String getLongStr() {
		return toString();
	}

	/**
	 * return meaningful string represents the date, such as 2000-01-01
	 */
	@Override
	public String toString() {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		DecimalFormat monDayFormat = new DecimalFormat("00");

		return year + "-" + monDayFormat.format(month) + "-"
				+ monDayFormat.format(day);
	}

	public int getWeekOfMonth() {
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	public int getMonth() {
		return cal.get(Calendar.MONTH) + 1;
	}
	
	public int getYear(){
		return cal.get(Calendar.YEAR);
	}

	public static int dayDiff(String startDate, String endDate) {
		return dayDiff(new MyDate(startDate), new MyDate(endDate));
	}

	public static int dayDiff(MyDate startDate, MyDate endDate) {
		int startTimeStamp = startDate.getTimeStamp();
		int endTimeStemp = endDate.getTimeStamp();

		return (endTimeStemp - startTimeStamp) / 24 / 3600;
	}

	/**
	 * this function accept a string represented a date, then judge whether it
	 * is correct day format
	 * 
	 * @return
	 */
	public static boolean isDate(String dateStr) {
		try {
			ConvertToDateArray(dateStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static MyDate[] newInstances(String startDate, String endDate) {
		if (!MyDate.isDate(startDate) || !MyDate.isDate(endDate))
			return new MyDate[0];

		if (startDate.compareTo(endDate) > 0) {
			String tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}
		MyDate date = new MyDate(startDate);
		MyDate lastDate = new MyDate(endDate);
		int dateCount = dayDiff(date, lastDate) + 1;
		MyDate[] dates = new MyDate[dateCount];
		for (int i = 0; i < dateCount; i++) {
			dates[i] = date.clone();
			date.add(1);
		}
		return dates;
	}

	static int[] ConvertToDateArray(String dateStr) {
		RuntimeException e = new RuntimeException("incorrect date format: "
				+ dateStr);

		String[] strDateArr = dateStr.split("-");
		if (strDateArr.length != 3)
			throw e;

		// record year/month/day value to dateArray
		int[] dateArr = new int[3];
		for (int i = 0; i < 3; i++) {
			dateArr[i] = Integer.parseInt(strDateArr[i]);
		}

		// record the year/month/day
		int year = dateArr[0];
		int month = dateArr[1];
		int day = dateArr[2];

		if (year < 0) {
			throw e;
		} else if (month < 1 || month > 12) {
			throw e;
		} else if (day < 1 || day > getDayCountOfMonth(year, month)) {
			throw e;
		}

		return dateArr;
	}

	static int getDayCountOfMonth(int year, int month) {
		if (isLeapYear(year)) {
			return DAY_COUNT_OF_LEAP[month - 1];
		} else {
			return DAY_COUNT_OF_COMMON[month - 1];
		}
	}

	private static boolean isLeapYear(int year) {
		if (year % 4 == 0 && (year % 100) != 0) {
			return true;
		} else if (year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}
}
