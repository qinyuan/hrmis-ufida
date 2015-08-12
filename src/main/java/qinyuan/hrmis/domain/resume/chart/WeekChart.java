package qinyuan.hrmis.domain.resume.chart;

public class WeekChart extends ResumeChart {

	@Override
	protected ResumeCounts getResumeCounts() {
		return new WeekResumeCounts();
	}
}
