package qinyuan.hrmis.domain.data;

import java.io.File;

import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.db.MySQLBackup;
import qinyuan.lib.file.FileUtil;
import qinyuan.lib.file.ZipTool;

public class HRMISBackup {

	private MySQLBackup backup;

	public HRMISBackup() throws Exception {
		backup = new MySQLBackup("hrmis", new MyDataSource());
	}

	public String export(String supFolder) throws Exception {
		clearZipFile(supFolder);
		backup.export(supFolder);
		String folderName = backup.getBackupFolderName();
		String fileName = folderName.substring(0, folderName.length() - 1)
				+ ".rar";
		try {
			ZipTool.zip(folderName, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileUtil.delete(folderName);
		}

		return fileName;
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
