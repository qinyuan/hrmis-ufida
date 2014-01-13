package qinyuan.hrmis.servlet;

import java.net.URLEncoder;

import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.hrmis.domain.resume.export.ResumeExporter;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.web.MyServlet;

public class ExportExcel extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute() {

		String type = getParameter("type");
		if (type != null) {
			ResumeExporter re = new ResumeExporter();

			String recruiterIdStr = getParameter("recruiterId");
			String recruiterName = "";
			if (numeric(recruiterIdStr)) {
				int recruiterId = parseInt(recruiterIdStr);
				recruiterName = SimpleUserDao.getInstance(recruiterId)
						.getName();
				re.setRecruiterId(recruiterId);
			}

			String startDate = getParameter("startDate");
			if (MyDate.isDate(startDate)) {
				re.setStartDate(startDate);
			}

			String endDate = getParameter("endDate");
			if (MyDate.isDate(endDate)) {
				re.setEndDate(endDate);
			}

			re.setExportJhReason(hasParameter("jhReason"));
			re.setExportEducation(hasParameter("education"));
			re.setExportSkill(hasParameter("skill"));
			re.setExportPrevJob(hasParameter("prevJob"));
			re.setExportPrevProj(hasParameter("prevProj"));

			try {
				String fileName = null;
				if (type.equals("daily")) {
					fileName = re.exportDailyReport().getAbsolutePath();
					recruiterName += "日报";
				} else if (type.equals("weekly")) {
					fileName = re.exportWeeklyReport().getAbsolutePath();
					recruiterName += "周报";
				}
				recruiterName += ".xls";
				recruiterName = URLEncoder.encode(recruiterName, "UTF-8");
				if (type != null) {
					redirect("/download?fileURL=" + fileName
							+ "&outputFileName=" + recruiterName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				print("<h3>数据库操作失败<h3>");
			}
		}
	}
}
