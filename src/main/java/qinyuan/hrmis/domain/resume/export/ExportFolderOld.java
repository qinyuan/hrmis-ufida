package qinyuan.hrmis.domain.resume.export;

import java.util.Map;

import qinyuan.lib.file.FileFormat;
import qinyuan.lib.file.PFile;

public class ExportFolderOld {

	private static String dailyReportFileName;
	private static String dataFolder;
	private static String weeklyReportFileName;
	private static String resumeDetailDoc;
	private static String resumeDetailXml;
	static {
		Map<String, String> map = PFile.parse("data");
		dataFolder = FileFormat.getLinStyleFolder(map.get("dataFolder"));
		dailyReportFileName = dataFolder + map.get("dialyReportFile");
		weeklyReportFileName = dataFolder + map.get("weeklyReportFile");
		resumeDetailDoc = dataFolder + map.get("resumeDetailFile");
		resumeDetailXml=dataFolder+map.get("resumeDetailXml");
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
	
	public static String getResumeDetailXml(){
		return resumeDetailXml;
	}

	public static void main(String[] args) {
		System.out.println(getResumeDetailDoc());
	}
}
