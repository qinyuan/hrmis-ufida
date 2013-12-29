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
			backup.export(folder);
			redirect("/download?fileURL=" + backup.getFileName());
		} catch (Exception e) {
			e.printStackTrace();
			print("数据库操作失败");
		}
	}
}
