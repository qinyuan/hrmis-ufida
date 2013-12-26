package qinyuan.hrmis.domain.data;

import java.io.File;

import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLBackup;
import qinyuan.lib.file.FileUtil;
import qinyuan.lib.file.ZipTool;

public class HRMISBackup {

	private MySQLBackup backup;
	private String fileName;

	public HRMISBackup() throws Exception {
		backup = new MySQLBackup("hrmis", new MyDataSource());
	}

	public String getFileName() {
		return fileName;
	}

	public boolean export(String supFolder) {
		clearZipFile(supFolder);
		if (backup.export(supFolder)) {
			String folderName = backup.getBackupFolderName();
			fileName = folderName.substring(0, folderName.length() - 1)
					+ ".rar";
			try {
				ZipTool.zip(folderName, fileName);
			} catch (Exception e) {
				e.printStackTrace();
				fileName = null;
				return false;
			} finally {
				FileUtil.delete(folderName);
			}
			return true;
		} else {
			fileName = null;
			return false;
		}
	}

	private static void clearZipFile(String folder) {
		File file = new File(folder);
		if (!file.isDirectory()) {
			return;
		}

		File[] files = file.listFiles();
		for (File f : files) {
			String fileName = f.getName();
			if (fileName.startsWith("hrmis") && fileName.endsWith(".rar")) {
				f.delete();
			}
		}
	}
}
