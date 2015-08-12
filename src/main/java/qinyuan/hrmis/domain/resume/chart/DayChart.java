package qinyuan.hrmis.domain.resume.chart;

public class DayChart extends ResumeChart{

	@Override
	protected ResumeCounts getResumeCounts() {
		return new DayResumeCounts();
	}
}
