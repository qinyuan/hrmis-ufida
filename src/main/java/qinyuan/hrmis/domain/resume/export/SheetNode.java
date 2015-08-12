package qinyuan.hrmis.domain.resume.export;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import qinyuan.hrmis.dao.Demand;
import qinyuan.hrmis.domain.resume.ResumeCount;
import qinyuan.lib.office.ExcelSheet;

public class SheetNode {

	private ExcelSheet sheet;
	private int rowIndex;
	private Map<Integer, Integer> sumMap = new HashMap<Integer, Integer>();
	private String startDate;
	private String endDate;
	private int recruiterId;
	private Set<String> placeAndPostRecordSet = new HashSet<String>();

	public SheetNode(ExcelSheet sheet, int startRowIndex) {
		this.sheet = sheet;
		this.rowIndex = startRowIndex;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setRecruiterId(int recruiterId) {
		this.recruiterId = recruiterId;
	}

	public void next() {
		rowIndex++;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setValue(int rowIndex, int columnIndex, String value) {
		sheet.setValue(rowIndex, columnIndex, value);
	}

	public void setValue(int columnIndex, String value) {
		sheet.setValue(rowIndex, columnIndex, value);
	}

	public void setValue(int columnIndex, int value) {
		if (value == 0) {
			return;
		}

		sheet.setValue(rowIndex, columnIndex, value);
		Integer sum = sumMap.get(columnIndex);
		if (sum == null) {
			sum = 0;
		}
		sum += value;
		sumMap.put(columnIndex, sum);
	}

	private void setComment(int columnIndex, String[] strs) {
		StringBuilder s = new StringBuilder();
		for (String str : strs) {
			s.append(str + ", ");
		}
		if (s.length() > 0) {
			s.delete(s.length() - 2, s.length() - 1);
		}
		sheet.setComment(rowIndex, columnIndex, 4, 4, s.toString());
	}

	public void outputSumRow() {
		for (Entry<Integer, Integer> entry : sumMap.entrySet()) {
			setValue(entry.getKey(), entry.getValue());
		}
	}

	public void outputResumeCounts(Demand demand) {
		ResumeCount rc = new ResumeCount();
		rc.setStartDate(startDate);
		rc.setEndDate(endDate);
		rc.setUserId(recruiterId);

		int postId = demand.getPostId();
		int targetPlaceId = demand.getTargetPlaceId();
		rc.setPostId(postId);
		rc.setTargetPlaceId(targetPlaceId);

		try {
			rc.setStatusId(0);
			boolean recorded = hasRecord(postId, targetPlaceId);
			if (!recorded) {
				setValue(4, rc.getCount());
				record(postId, targetPlaceId);
			}

			rc.setDemandId(demand.getId());

			outputRecommendResumeCount(rc, 1, 5);
			outputRecommendResumeCount(rc, 2, 7);
			outputRecommendResumeCount(rc, 3, 8);
			outputRecommendResumeCount(rc, 4, 9);
			outputRecommendResumeCount(rc, 5, 12);

			if (!recorded) {
				int downloadedCount = rc.getDownloadedCount();
				if (downloadedCount > 0) {
					setValue(14, "已下载：" + downloadedCount);
				}
				record(postId, targetPlaceId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void outputRecommendResumeCount(ResumeCount rc, int statusId,
			int columnIndex) throws SQLException {
		rc.setStatusId(statusId);
		String[] applicants = rc.getRecommendApplicant();
		if (applicants.length > 0) {
			setValue(columnIndex, applicants.length);
			setComment(columnIndex, applicants);
		}
	}

	private final static String splitStr = ",";

	private void record(int postId, int targetPlaceId) {
		placeAndPostRecordSet.add(postId + splitStr + targetPlaceId);
	}

	private boolean hasRecord(int postId, int targetPlaceId) {
		return placeAndPostRecordSet
				.contains(postId + splitStr + targetPlaceId);
	}

	public void merge(int startRowIndex, int endRowIndex, int columnIndex) {
		sheet.merge(startRowIndex, endRowIndex - 1, columnIndex, columnIndex);
	}

	public void merge(int startRowIndex, int endRowIndex, int startColumnIndex,
			int endColumnIndex, boolean center) {
		sheet.merge(startRowIndex, endRowIndex, startColumnIndex,
				endColumnIndex, center);
	}

}
