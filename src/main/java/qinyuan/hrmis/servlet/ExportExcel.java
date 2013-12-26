package qinyuan.hrmis.servlet;

import qinyuan.hrmis.domain.resume.export.ResumeExporter;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.lang.MyMath;
import qinyuan.lib.web.MyServlet;

public class ExportExcel extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute() {

		String type = getParameter("type");
		if (type != null) {
			ResumeExporter re = new ResumeExporter();
			String recruiterIdStr = getParameter("recruiterId");
			if (MyMath.isNumeric(recruiterIdStr)) {
				re.setRecruiterId(parseInt(recruiterIdStr));
			}

			String startDate = getParameter("startDate");
			if (MyDate.isDate(startDate)) {
				re.setStartDate(startDate);
			}

			String endDate = getParameter("endDate");
			if (MyDate.isDate(endDate)) {
				re.setEndDate(endDate);
			}
			try {
				String fileName = null;
				if (type.equals("daily")) {
					fileName = re.exportDailyReport().getAbsolutePath();
				} else if (type.equals("weekly")) {
					fileName = re.exportWeeklyReport().getAbsolutePath();
				}
				if (type != null) {
					redirect("/download?fileURL=" + fileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				print("<h3>数据库操作失败<h3>");
			}
		}
	}
}
