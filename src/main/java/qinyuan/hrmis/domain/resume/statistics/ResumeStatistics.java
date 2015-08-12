package qinyuan.hrmis.domain.resume.statistics;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import qinyuan.hrmis.domain.recommend.Status;
import qinyuan.lib.date.Month;
import qinyuan.lib.date.Week;
import qinyuan.lib.web.html.DataOptions;
import qinyuan.lib.web.html.ImageUtil;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class ResumeStatistics {

	private int userId;
	private int postId;
	private int year;
	private int month;
	private int day;

	public ResumeStatistics() {
		Calendar cal = new GregorianCalendar();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DATE);
	}

	public ResumeStatistics setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public ResumeStatistics setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	protected TableRow getTableHead() throws SQLException {
		TableRow tr = new TableRow(true).add("", "简历数");
		for (int i = 1; i <= Status.size(); i++) {
			tr.add(Status.get(i));
		}
		tr.add();
		return tr;
	}

	@Override
	public String toString() {
		Table table = new Table();
		try {
			table.addHead(getTableHead().add(""));

			Week week = new Week();
			String startOfWeek = week.getLongDay(0);
			String endOfWeek = week.getLongDay(6);

			String today = year + "-" + month + "-" + day;
			String startOfToday = today;
			String endOfToday = today;

			Month mon = new Month();
			String startOfMonth = mon.getFirstDay();
			String endOfMonth = mon.getLastDay();

			String startOfYear = year + "-01-01";
			String endOfYear = year + "-12-31";

			table.add(getPeriodRow(startOfToday, endOfToday).append("今天"));
			table.add(getPeriodRow(startOfWeek, endOfWeek, QueryButtons.DAY)
					.append(getWeekTitle()));
			table.add(getPeriodRow(startOfMonth, endOfMonth, QueryButtons.WEEK)
					.append(getMonthTitle()));
			table.add(getPeriodRow(startOfYear, endOfYear).append(
					getYearTitle()));
			table.add(getPeriodRow("1900-1-1", "2100-1-1").append("历史总计"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table.toString();
	}

	private String getWeekTitle() {
		DataOptions dp = getDataOptions();
		dp.add("year", year);
		dp.add("month", month);
		dp.add("day", day);
		return "本周 " + getDownArrowImage("展开每日统计", dp);
	}

	private static String getDownArrowImage(String alt, DataOptions dp) {
		return ImageUtil.getDownArrowImage(null, alt, dp.getBody());
	}

	private DataOptions getDataOptions() {
		DataOptions dp = new DataOptions();
		dp.add("recruiterId", userId);
		dp.add("postId", postId);
		return dp;
	}

	private String getMonthTitle() {
		DataOptions dp = getDataOptions();
		dp.add("month", month);
		dp.add("year", year);
		return "本月 " + getDownArrowImage("展开每周统计", dp);
	}

	private String getYearTitle() {
		DataOptions dp = getDataOptions();
		dp.add("year", year);
		return "本年 " + getDownArrowImage("展开每月统计", dp);
	}

	protected TableRow getPeriodRow(String startDate, String endDate)
			throws SQLException {
		return getPeriodRow(startDate, endDate, QueryButtons.EMPTY);
	}

	protected TableRow getPeriodRow(String startDate, String endDate,
			QueryButtons type) throws SQLException {
		ResumeStatisticsRow rsr = new ResumeStatisticsRow();
		rsr.setPostId(postId);
		rsr.setUserId(userId);
		rsr.setStartDate(startDate);
		rsr.setEndDate(endDate);
		return rsr.toTableRow(type);
	}

	public static void main(String[] args) {
		System.out.println(new ResumeStatistics());
	}
}