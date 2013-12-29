package qinyuan.hrmis.domain.resume.export;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.CellStyle;

import qinyuan.hrmis.HrmisConfig;
import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.hrmis.dao.SimpleUser;
import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.hrmis.domain.recommend.Recommend;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.file.FileUtil;
import qinyuan.lib.office.ExcelBook;
import qinyuan.lib.office.ExcelColor;
import qinyuan.lib.office.ExcelSheet;

public class ResumeExporter {

	private int recruiterId;
	private String startDate;
	private String endDate;

	public void setRecruiterId(int recruiterId) {
		this.recruiterId = recruiterId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public File exportDailyReport() throws Exception {
		clearFile();
		File file = FileUtil.createTimeMarkFile(HrmisConfig
				.getDailyReportFileName());
		ExcelBook wb = new ExcelBook(file);

		ExcelSheet sh = wb.getSheet(0);
		Resume[] rs = ResumeDao.getInstances(startDate, endDate, recruiterId);
		exportDailyReport(rs, sh);

		wb.close();
		return file;
	}

	public File exportWeeklyReport() throws Exception {
		clearFile();
		File file = FileUtil.createTimeMarkFile(HrmisConfig
				.getWeeklyReportFileName());
		ExcelBook wb = new ExcelBook(file);

		ExcelSheet sh = wb.getSheet(0);
		Resume[] rs = ResumeDao.getInstances(startDate, endDate, recruiterId);
		exportDailyReport(rs, sh);

		sh = wb.getSheet(1);
		SheetNode sheetNode = new SheetNode(sh, 4);
		sheetNode.setStartDate(startDate);
		sheetNode.setEndDate(endDate);
		sheetNode.setRecruiterId(recruiterId);
		for (TargetPlaceNode targetPlaceNode : TargetPlaceNode.getInstances()) {
			targetPlaceNode.output(sheetNode);
		}
		sheetNode.outputSumRow();

		sheetNode.setValue(1, "总计");
		sheetNode.setValue(2, 7,
				startDate.replace("-", ".") + "-" + endDate.replace("-", "."));
		SimpleUser simpleUser = SimpleUserDao.getInstance(recruiterId);
		if (simpleUser == null) {
			sheetNode.setValue(2, 10, "（全部）");
		} else {
			sheetNode.setValue(2, 10, simpleUser.getName());
		}

		MyDate startDateObj = new MyDate(startDate);
		String title = startDateObj.getYear() + "年" + startDateObj.getMonth()
				+ "月第" + startDateObj.getWeekOfMonth() + "周招聘工作汇总表";
		sheetNode.setValue(0, 1, title);

		int lastRowIndex = sheetNode.getRowIndex();
		sh.addBorder(4, lastRowIndex, 0, 14);
		sh.addBorder(4, lastRowIndex - 1, 15, 28);

		sheetNode.setValue(lastRowIndex - 2, 15, "流失人员");
		sheetNode.merge(lastRowIndex - 2, lastRowIndex - 2, 15, 21, true);

		sheetNode.setValue(lastRowIndex - 2, 22, "流失原因");
		sheetNode.merge(lastRowIndex - 2, lastRowIndex - 2, 22, 28, true);

		sheetNode.setValue(lastRowIndex - 3, 15, "人员流失原因");
		sheetNode.merge(lastRowIndex - 3, lastRowIndex - 3, 15, 28, true);

		sheetNode.setValue(lastRowIndex - 4, 15, "问题内容");
		sheetNode.merge(lastRowIndex - 4, lastRowIndex - 4, 15, 21, true);

		sheetNode.setValue(lastRowIndex - 4, 22, "如何解决");
		sheetNode.merge(lastRowIndex - 4, lastRowIndex - 4, 22, 28, true);

		wb.close();
		return file;
	}

	private static void exportDailyReport(Resume[] rs, ExcelSheet sh)
			throws SQLException {
		int styleCount = 3;
		CellStyle[] styles = new CellStyle[styleCount];
		for (int i = 0; i < styleCount; i++) {
			styles[i] = sh.getCellStyle(i, 1);
		}

		for (int i = 0; i < rs.length; i++) {
			int statusId = ResumeDao.getStatusIdById(rs[i].getId());
			if (statusId <= 0) {
				updateRowByResume(rs[i], sh, i + 4, null);
			} else if (statusId == 1) {
				updateRowByResume(rs[i], sh, i + 4, styles[0]);
			} else if (statusId >= 2 && statusId <= 4) {
				updateRowByResume(rs[i], sh, i + 4, styles[1]);
			} else if (statusId >= 5) {
				updateRowByResume(rs[i], sh, i + 4, styles[2]);
			}
		}
	}

	private static void updateRowByResume(Resume r, ExcelSheet sh,
			int rowIndex, CellStyle style) throws SQLException {
		RowUpdator ru = new RowUpdator(sh, rowIndex);
		if (style != null) {
			ru.setCellStyle(style);
		}
		ru.add(r.getLongAddTime().replaceAll("\\s.*", ""));
		ru.add(r.getCompany());
		ru.add(r.getApplicant());

		String postName = r.getPostName();
		if (r.getTargetPlaceId() != 1) {
			postName += "(" + r.getTargetPlaceName() + ")";
		}

		ru.add(postName);
		ru.add(r.getTel());
		ru.add(r.getEmail());
		ru.add(r.getQq());
		String sourceName = r.getSourceName();
		if (r.getDownloaded()) {
			sourceName += "(已下载)";
		}
		ru.add(sourceName);

		if (r.isIntentionRed()) {
			ru.add(r.getIntention(), ExcelColor.RED);
		} else {
			ru.add(r.getIntention());
		}
		ru.add(String.valueOf(r.getExperience()).replace(".0", ""));
		ru.add(r.getExpectSalary());
		ru.add(r.getOther());
		if (style != null) {
			Recommend[] rms = Recommend.getInstancesByResumeId(r.getId());
			ru.add(rms[rms.length - 1].getDemand());
		} else {
			ru.next();
		}

		ru.add(r.getJhReason());
		ru.add(r.getEducation());
		ru.add(r.getSkill());
		ru.add(r.getPrevJob());
		ru.add(r.getPrevProj());
	}

	private static void clearFile() {
		File folder = new File(HrmisConfig.getDataFolderName());
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String fileName) {
				if (!fileName.matches("\\d+\\.xls")) {
					return false;
				} else {
					return System.currentTimeMillis()
							- Long.parseLong(fileName.replace(".xls", "")) > 60000;
				}
			}
		});
		for (File file : files) {
			file.delete();
		}
	}

	public static void main(String[] args) throws Exception {
		ResumeExporter e = new ResumeExporter();
		e.setStartDate("2012-1-1");
		e.setEndDate("2013-12-12");
		FileUtil.show(e.exportWeeklyReport());
	}

	private static class RowUpdator {
		private ExcelSheet sheet;
		private int rowIndex;
		private int columnIndex = 0;
		private CellStyle style;

		public RowUpdator(ExcelSheet sheet, int rowIndex) {
			this.sheet = sheet;
			this.rowIndex = rowIndex;
		}

		public void setCellStyle(CellStyle style) {
			this.style = style;
		}

		public void add(Object str, ExcelColor color) {
			if (str == null) {
				str = "";
			}
			if (style != null) {
				sheet.setValueAndStyle(rowIndex, columnIndex, str.toString(),
						style);
			} else if (color != null) {
				sheet.setValueAndColor(rowIndex, columnIndex, str.toString(),
						color);
			} else {
				sheet.setValue(rowIndex, columnIndex, str.toString());
			}
			next();
		}

		public void next() {
			columnIndex++;
		}

		public void add(Object str) {
			add(str, null);
		}
	}
}
