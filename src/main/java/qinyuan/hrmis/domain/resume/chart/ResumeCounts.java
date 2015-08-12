package qinyuan.hrmis.domain.resume.chart;

import java.sql.SQLException;

import qinyuan.hrmis.domain.resume.ResumeCount;

public abstract class ResumeCounts {

	private int statusId;
	private int userId;
	private int postId;
	private String startDate;
	private String endDate;

	public ResumeCounts setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public ResumeCounts setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public ResumeCounts setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public ResumeCounts setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public ResumeCounts setEndDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	public abstract Period[] getPeriods();

	public abstract String[] getCategories();

	public int[] getCounts() throws SQLException {
		ResumeCount rc = new ResumeCount();
		rc.setUserId(userId);
		rc.setPostId(postId);
		rc.setStatusId(statusId);
		Period[] periods = getPeriods();
		int[] counts = new int[periods.length];
		for (int i = 0; i < counts.length; i++) {
			Period p = periods[i];
			rc.setStartDate(p.startDate);
			rc.setEndDate(p.endDate);
			counts[i] = rc.getCount();
		}
		return counts;
	}

	protected class Period {
		Period(String startDate, String endDate) {
			this.startDate = startDate;
			this.endDate = endDate;
		}

		String startDate;
		String endDate;
	}
}