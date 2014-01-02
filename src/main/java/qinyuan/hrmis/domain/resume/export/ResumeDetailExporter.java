package qinyuan.hrmis.domain.resume.export;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import qinyuan.hrmis.HrmisConfig;
import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.lib.file.FileUtil;
import qinyuan.lib.file.PropertyUtil;
import qinyuan.lib.office.WordUtil;

public class ResumeDetailExporter {

	public static File exportWord(int resumeId) throws Exception {
		return exportWord(ResumeDao.getInstance(resumeId));
	}

	public static File exportWord(Resume r) throws Exception {
		if (r == null) {
			throw new RuntimeException("invalid resume");
		}
		clearFile();
		File xmlFile = FileUtil.createTimeMarkFile(HrmisConfig
				.getResumeDetailXml());
		String xmlFileName = xmlFile.getAbsolutePath();

		String xmlFileContent = FileUtil.readAll(xmlFileName);
		TextReplace tp = new TextReplace(xmlFileContent);
		tp.replace("name", r.getApplicant());
		tp.replace("experience",
				String.valueOf(r.getExperience()).replace(".0", ""));
		tp.replace("gender", r.getGenderId() == 1 ? "" : r.getGenderName());
		tp.replace("tel", r.getTel());
		tp.replace("education", r.getEducation());
		tp.replace("prevJob", r.getPrevJob());
		tp.replace("prevProj", r.getPrevProj());
		FileUtil.write(xmlFileName, tp.text);

		String docFileName = xmlFileName.replace(".xml", ".doc");
		WordUtil.createDocByXml(xmlFileName, docFileName);
		return new File(docFileName);
	}

	private static void clearFile() {
		File folder = new File(HrmisConfig.getDataFolderName());
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String fileName) {
				if (fileName.matches("\\d+\\.[doc|xml]")) {
					return System.currentTimeMillis()
							- Long.parseLong(fileName.replaceAll(
									"\\.[doc|xml]", "")) > 60000;
				} else {
					return false;
				}
			}
		});
		for (File file : files) {
			file.delete();
		}
	}

	public static void main(String[] args) throws Exception {
		File file = exportWord(689);
		FileUtil.show(file.getAbsolutePath().replaceAll("\\d+.doc", ""));
	}

	private static Map<String, String> map = PropertyUtil.parse("word");
	private static String paragraphPrefix = map.get("resumeParagraphPrefix");
	private static String paragraphSuffix = map.get("resumeParagraphSuffix");

	private static class TextReplace {
		String text;

		TextReplace(String text) {
			this.text = text;
		}

		void replace(String key, String text) {
			if (text == null) {
				text = "";
			}
			String[] strs = text.replace("\r\n", "\n").split("\n");
			StringBuilder s = new StringBuilder(strs[0]);
			for (int i = 1; i < strs.length; i++) {
				s.append(paragraphSuffix + paragraphPrefix + strs[i]);
			}
			this.text = this.text.replace("${" + key + "}", s.toString());
		}
	}
}