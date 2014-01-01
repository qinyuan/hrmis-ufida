package qinyuan.hrmis.domain.data;

import java.io.File;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.file.FileUtil;
import qinyuan.lib.file.ZipTool;

public class HRMISRecover {

	private String errorInfo;

	public boolean recover(String fileName) {
		File file = new File(fileName);
		if (!fileName.endsWith(".rar")) {
			errorInfo = "备份文件应以.rar结尾";
			return false;
		} else if (!file.isFile()) {
			errorInfo = "未找到文件" + fileName;
			return false;
		}

		String sqlFileName = fileName.replace(".rar", ".sql");
		try {
			ZipTool.unzip(fileName, file.getParent());
		} catch (Exception e) {
			errorInfo = "解压" + fileName + "失败";
			e.printStackTrace();
			return false;
		}

		try (MyConn cnn = new MyConn()) {
			cnn.executeSource(sqlFileName);
			errorInfo = null;
			FileUtil.delete(sqlFileName);
			return true;
		} catch (Exception e) {
			errorInfo = "数据库操作失败";
			e.printStackTrace();
			return false;
		}
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public static void main(String[] args) {
		HRMISRecover r = new HRMISRecover();
		r.recover("h:/hrmis2013.09.21.18.49.15.rar");
		System.out.println(r.getErrorInfo());
	}
}