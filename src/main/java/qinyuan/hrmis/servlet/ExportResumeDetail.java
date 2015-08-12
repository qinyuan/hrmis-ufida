package qinyuan.hrmis.servlet;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;

import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.hrmis.domain.resume.export.ResumeDetailExporter;
import qinyuan.lib.web.MyServlet;

public class ExportResumeDetail extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute() {

		String resumeIdStr = getParameter("resumeId");
		if (numeric(resumeIdStr)) {
			int resumeId = parseInt(resumeIdStr);
			Resume r = ResumeDao.getInstance(resumeId);
			try {
				String exportTel = getParameter("exportTel");
				File file = ResumeDetailExporter.exportWord(r,
						exportTel != null && exportTel.equals("true"));
				if (file != null) {
					String fileName = getFileName(r);
					redirect("/download?fileURL=" + file.getAbsolutePath()
							+ "&outputFileName=" + fileName);
				} else {
					print("未找到相关简历");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				print("<h3>数据库操作失败<h3>");
			} catch (Exception e) {
				e.printStackTrace();
				print("未知错误");
			}
		}
	}

	private String getFileName(Resume r) throws UnsupportedEncodingException {
		String str = "UFIDA-" + r.getPostName() + "-" + r.getApplicant()
				+ ".doc";
		return URLEncoder.encode(str, "UTF-8");
	}
}
