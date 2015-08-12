package qinyuan.hrmis;

import java.util.Map;

import qinyuan.lib.file.FileFormat;
import qinyuan.lib.file.FileUtil;
import qinyuan.lib.file.PropertyUtil;

public class HrmisConfig {

	private HrmisConfig() {
	}

	private static String dataFolder;
	private static String dailyReportFileName;
	private static String weeklyReportFileName;
	private static String resumeDetailDoc;
	private static String resumeDetailXml;
	private static Map<String, String> dataMap;
	static {
		dataMap = PropertyUtil.parse("data");

		dataFolder = dataMap.get("dataFolder");
		if (dataFolder != null) {
			dataFolder = FileUtil.getClassesPath() + dataFolder;
			dataFolder = FileFormat.getLinStyleFolder(dataFolder);
			dailyReportFileName = dataFolder + dataMap.get("dialyReportFile");
			weeklyReportFileName = dataFolder + dataMap.get("weeklyReportFile");
			resumeDetailDoc = dataFolder + dataMap.get("resumeDetailFile");
			resumeDetailXml = dataFolder + dataMap.get("resumeDetailXml");
		}
	}

	public static String getDataFolderName() {
		return dataFolder;
	}

	public static String getDailyReportFileName() {
		return dailyReportFileName;
	}

	public static String getWeeklyReportFileName() {
		return weeklyReportFileName;
	}

	public static String getResumeDetailDoc() {
		return resumeDetailDoc;
	}

	public static String getResumeDetailXml() {
		return resumeDetailXml;
	}

}
