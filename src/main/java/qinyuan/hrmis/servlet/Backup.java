package qinyuan.hrmis.servlet;

import qinyuan.hrmis.HrmisConfig;
import qinyuan.hrmis.domain.data.HRMISBackup;
import qinyuan.lib.web.MyServlet;

public class Backup extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute() {
		try {
			HRMISBackup backup = new HRMISBackup();
			String folder = HrmisConfig.getDataFolderName();
			String fileName = backup.export(folder);
			redirect("/download?fileURL=" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
			print("数据库操作失败");
			print("<br />"+e.getMessage());
		}
	}
}
