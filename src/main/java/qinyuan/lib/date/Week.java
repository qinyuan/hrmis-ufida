package qinyuan.lib.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Week {

	private final static String[] WEEK_NAMES = { "周日", "周一", "周二", "周三", "周四",
			"周五", "周六" };
	private final static int MONTH_START_INDEX = 5;
	private Calendar cal;
	private int weekNum;

	public Week() {
		this(0);
	}

	public Week(int weekNum) {
		Calendar tempCal = new GregorianCalendar();
		tempCal.add(Calendar.DATE, weekNum * 7);
		int year = tempCal.get(Calendar.YEAR);
		int month = tempCal.get(Calendar.MONTH) + 1;
		int day = tempCal.get(Calendar.DAY_OF_MONTH);
		init(year, month, day);
	}

	public Week(int year, int month, int day) {
		init(year, month, day);
	}

	public Week(String date) {
		if (!MyDate.isDate(date)) {
			throw new RuntimeException("incorrect date string format: " + date
					+ " in class " + getClass());
		}
		String[] dateArr = date.split("\\D");
		int year = Integer.parseInt(dateArr[0]);
		int month = Integer.parseInt(dateArr[1]);
		int day = Integer.parseInt(dateArr[2]);
		init(year, month, day);
	}

	private void init(int year, int month, int day) {
		cal = new GregorianCalendar(year, month - 1, day);
		cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK));
		long stamp = cal.getTimeInMillis();
		long nowStamp = System.currentTimeMillis();
		double dayDiff = ((double) (stamp - nowStamp)) / 1000 / 3600 / 24;
		weekNum = (int) (Math.ceil(dayDiff / 7));
	}

	public void add(int weeksToAdd) {
		this.weekNum += weeksToAdd;
		cal.add(Calendar.DATE, weeksToAdd * 7);
	}
	
	/**
	 * return date String such as 2000-01-01
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public String getLongDay(int dayOfWeek) {
		moveDay(dayOfWeek);
		String result = getCalStr();
		moveDay(-dayOfWeek);
		return result;
	}
	
	public String getFirstLongDay(){
		return getLongDay(0);
	}
	
	public String getFirstShortDay(){
		return getShortDay(6);
	}
	
	public String getLastLongDay(){
		return getLongDay(6);
	}
	
	public String getLastShortDay(){
		return getShortDay(6);
	}

	public String[] getLongDays() {
		String[] days = new String[7];
		for (int i = 0; i < 7; i++) {
			days[i] = getCalStr();
			moveDay(1);
		}
		moveDay(-7);
		return days;
	}

	/**
	 * return String such as 12-05
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public String getShortDay(int dayOfWeek) {
		return getLongDay(dayOfWeek).substring(MONTH_START_INDEX);
	}

	public String[] getShortDays() {
		String[] days = new String[7];
		for (int i = 0; i < 7; i++) {
			days[i] = getCalStr().substring(MONTH_START_INDEX);
			moveDay(1);
		}
		moveDay(-7);
		return days;
	}

	public int getWeekNum() {
		return weekNum;
	}

	private void moveDay(int dayCount) {
		cal.add(Calendar.DATE, dayCount);
	}

	private String getCalStr() {
		int intYear = cal.get(Calendar.YEAR);
		String strMonth = padZero(cal.get(Calendar.MONTH) + 1);
		String strDay = padZero(cal.get(Calendar.DATE));

		return intYear + "-" + strMonth + "-" + strDay;
	}

	public static int getWeekDiff(Week startWeek, Week endWeek) {
		return endWeek.getWeekNum() - startWeek.getWeekNum();
	}

	public static Week[] newInstances(String startDate, String endDate) {
		Week startWeek = new Week(startDate);
		Week endWeek = new Week(endDate);
		int weekCount = Week.getWeekDiff(startWeek, endWeek);
		Week[] weeks = new Week[weekCount + 1];
		int i = 0;
		while (getWeekDiff(startWeek, endWeek) >= 0) {
			weeks[i++] = new Week(startWeek.getWeekNum());
			startWeek.add(1);
		}
		return weeks;
	}

	/**
	 * return week name such as 周一
	 * 
	 * @param weeknum
	 * @return
	 */
	public static String getWeekName(int weeknum) {
		return WEEK_NAMES[weeknum];
	}

	private static String padZero(int num) {
		return (num < 10 ? "0" : "") + num;
	}

	public static void main(String[] args) {
		Week week = new Week();
		System.out.println(week.getLongDay(0));
		System.out.println(week.getWeekNum());
		week.add(-2);
		System.out.println(week.getLongDay(0));
		System.out.println(week.getLongDay(7));
		System.out.println(week.getWeekNum());

		week=new Week("2013-10-1");
		System.out.println(week.getLongDay(0));
		System.out.println(week.getWeekNum());
		
		week=new Week("2013-11-1");
		System.out.println(week.getLongDay(0));
		System.out.println(week.getWeekNum());
	}
}