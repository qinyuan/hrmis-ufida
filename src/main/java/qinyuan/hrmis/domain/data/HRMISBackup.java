package qinyuan.hrmis.domain.data;

import java.io.File;

import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.db.MySQLDump;
import qinyuan.lib.file.FileFormat;
import qinyuan.lib.file.ZipTool;

public class HRMISBackup {

	public File export(String supFolder) {
		clearBackupFile(supFolder);
		String fileName = FileFormat.getLinStyleFolder(supFolder) + "hrmis"
				+ new MyDateTime().toString().replaceAll("\\D", ".") + ".sql";
		MySQLDump dump = new MySQLDump(DB_NAME, new MyDataSource());
		dump.export(fileName);
		return ZipTool.zip(fileName);
	}

	private static void clearBackupFile(String folder) {
		File file = new File(folder);
		if (!file.isDirectory()) {
			return;
		}

		File[] files = file.listFiles();
		for (File f : files) {
			String fileName = f.getName();
			if (fileName.startsWith(DB_NAME)
					&& (fileName.endsWith(".rar") || fileName.endsWith(".sql"))) {
				f.delete();
			}
		}
	}

	private final static String DB_NAME = "hrmis";

	public static void main(String[] args) {
		new HRMISBackup().export("d:/test");
	}
}
