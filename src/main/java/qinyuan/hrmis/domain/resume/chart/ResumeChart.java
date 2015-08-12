package qinyuan.hrmis.domain.resume.chart;

import java.sql.SQLException;

import qinyuan.hrmis.domain.recommend.Status;
import qinyuan.lib.web.HighChart;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public abstract class ResumeChart {

	private int userId;
	private int postId;
	private String startDate;
	private String endDate;

	public ResumeChart setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public ResumeChart setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public ResumeChart setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public ResumeChart setEndDate(String endDate) {
		this.endDate = endDate;
		return this;
	}

	protected abstract ResumeCounts getResumeCounts();

	public String getTable() {
		Table t = new Table();
		ResumeCounts rc = getAdjustedResumeCounts();

		try {
			TableRow tr = new TableRow().add("");
			for (String category : rc.getCategories()) {
				tr.add(category);
			}
			t.addHead(tr);

			tr = new TableRow().add("简历数");
			rc.setStatusId(0);
			for (int count : rc.getCounts()) {
				tr.add(count > 0 ? String.valueOf(count) : "");
			}
			t.add(tr);

			for (int i = 1; i <= Status.size(); i++) {
				tr = new TableRow().add(Status.get(i));
				rc.setStatusId(i);
				for (int count : rc.getCounts()) {
					tr.add(count > 0 ? String.valueOf(count) : "");
				}
				t.add(tr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return t.toString();
	}

	private ResumeCounts getAdjustedResumeCounts() {
		return getResumeCounts().setUserId(userId).setPostId(postId)
				.setStartDate(startDate).setEndDate(endDate);
	}

	@Override
	public String toString() {
		ResumeCounts rc = getAdjustedResumeCounts();
		HighChart chart = new HighChart();
		chart.setTitle("简历统计图");
		chart.setYTitle("数量");

		try {
			chart.setCategories(rc.getCategories());
			rc.setStatusId(0);
			chart.addSeries("简历数", rc.getCounts());
			for (int i = 1; i <= Status.size(); i++) {
				rc.setStatusId(i);
				chart.addSeries(Status.get(i), rc.getCounts());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chart.toString();
	}
}