package qinyuan.hrmis.domain.resume.export;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.lib.file.FileUtil;

public class ResumeDetailExporterOld {

	public static File exportWord(int resumeId) throws Exception {
		return exportWord(ResumeDao.getInstance(resumeId));
	}

	public static File exportWord(Resume r) throws Exception {
		if (r == null) {
			throw new RuntimeException("invalid resume");
		}
		clearFile();
		File file = FileUtil.createTimeMarkFile(ExportFolder
				.getResumeDetailDoc());

		InputStream is = new FileInputStream(file);
		HWPFDocument hdt = new HWPFDocument(is);

		RangeReplace rp = new RangeReplace(hdt.getRange());
		rp.replace("name", r.getApplicant());
		rp.replace("experience",
				String.valueOf(r.getExperience()).replace(".0", ""));
		rp.replace("gender", r.getGenderId() == 1 ? "" : r.getGenderName());
		rp.replace("tel", r.getTel());
		rp.replace("education", r.getEducation());
		rp.replace("prevJob", r.getPrevJob());
		rp.replace("prevProj", r.getPrevProj());

		exportWordDoc(new File("d:/test2.doc"), hdt);
		exportWordDoc(file, hdt);

		is.close();
		return file;
	}

	private static void exportWordDoc(File file, HWPFDocument hdt)
			throws IOException, FileNotFoundException {
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		hdt.write(ostream);
		OutputStream os = new FileOutputStream(file);
		os.write(ostream.toByteArray());
		ostream.close();
		os.close();
	}

	private static void clearFile() {
		File folder = new File(ExportFolder.getDataFolderName());
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String fileName) {
				if (!fileName.matches("\\d+\\.doc")) {
					return false;
				} else {
					return System.currentTimeMillis()
							- Long.parseLong(fileName.replace(".doc", "")) > 60000;
				}
			}
		});
		for (File file : files) {
			file.delete();
		}
	}

	public static void main(String[] args) throws Exception {
		File file = exportWord(478);
		FileUtil.show(file);
	}

	private static class RangeReplace {
		private Range range;

		RangeReplace(Range range) {
			this.range = range;
		}

		void replace(String key, String text) {
			range.replaceText("${" + key + "}", text.replace("\r\n", "\r"));
		}
	}
}