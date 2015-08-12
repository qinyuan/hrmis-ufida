package qinyuan.hrmis.domain.resume;

import qinyuan.lib.date.MyDate;

public class ResumeFilter {

	private int recruiterId;
	private int postId;
	private String startAddDate;
	private String endAddDate;
	private String startMdfDate;
	private String endMdfDate;
	private String startHandleDate;
	private String endHandleDate;
	private int status = -1;
	private int markStatus = 0;
	private String searchName;
	private String searchTel;
	private int targetPlaceId;

	public int getStatusId() {
		return this.status;
	}

	public void setEndAddDate(String endAddDate) {
		this.endAddDate = MyDate.isDate(endAddDate) ? endAddDate : null;
	}

	public void setEndHandleDate(String endHandleDate) {
		this.endHandleDate = MyDate.isDate(endHandleDate) ? endHandleDate
				: null;
	}

	public void setEndMdfDate(String endMdfDate) {
		this.endMdfDate = MyDate.isDate(endMdfDate) ? endMdfDate : null;
	}

	public void setMarkStatus(int markStatus) {
		this.markStatus = markStatus;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setSearchTel(String searchTel) {
		this.searchTel = searchTel;
	}

	public void setStartAddDate(String startAddDate) {
		this.startAddDate = MyDate.isDate(startAddDate) ? startAddDate : null;
	}

	public void setStartHandleDate(String startHandleDate) {
		this.startHandleDate = MyDate.isDate(startHandleDate) ? startHandleDate
				: null;
	}

	public void setStartMdfDate(String startMdfDate) {
		this.startMdfDate = MyDate.isDate(startMdfDate) ? startMdfDate : null;
	}

	public void setStatusId(int statusId) {
		this.status = statusId;
	}

	public void setTargetPlaceId(int targetPlaceId) {
		this.targetPlaceId = targetPlaceId;
	}

	public void setRecruiterId(int recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getWhereClause() {
		String s = "";
		if (recruiterId > 0) {
			s = getWherePrefix(s);
			s += "r.tracer_id=" + recruiterId;
		}
		if (postId > 0) {
			s = getWherePrefix(s);
			s += "r.post_id=" + postId;
		}
		if (searchName != null && !searchName.trim().isEmpty()) {
			s = getWherePrefix(s);
			s += "r.applicant LIKE '%" + searchName + "%'";
		}
		if (searchTel != null && !searchTel.trim().isEmpty()) {
			s = getWherePrefix(s);
			s += "r.tel LIKE '%" + searchTel + "%'";
		}
		if (targetPlaceId > 0) {
			s = getWherePrefix(s);
			s += "r.target_place_id=" + targetPlaceId;
		}
		if (markStatus == 1) {
			s = getWherePrefix(s);
			s += "r.intention_red=TRUE";
		} else if (markStatus == 2) {
			s = getWherePrefix(s);
			s += "r.intention_red=FALSE";
		}
		if (status <= 0) {
			if (startAddDate != null) {
				s = getWherePrefix(s);
				s += "DATE(r.add_time)>='" + startAddDate + "'";
			}
			if (endAddDate != null) {
				s = getWherePrefix(s);
				s += "DATE(r.add_time)<='" + endAddDate + "'";
			}
			if (startMdfDate != null) {
				s = getWherePrefix(s);
				s += "DATE(r.mdf_time)>='" + startMdfDate + "'";
			}
			if (endMdfDate != null) {
				s = getWherePrefix(s);
				s += "DATE(r.mdf_time)<='" + endMdfDate + "'";
			}
		} else {
			s = getWherePrefix(s);
			s += "max_status_id>=" + status;

			if (startHandleDate != null) {
				s = getWherePrefix(s);
				s += "DATE(min_deal_time)>='" + startHandleDate + "'";
			}
			if (endHandleDate != null) {
				s = getWherePrefix(s);
				s += "DATE(min_deal_time)<='" + endHandleDate + "'";
			}
		}
		return s;
	}

	private String getWherePrefix(String s) {
		if (s.contains("WHERE")) {
			return s + " AND ";
		} else {
			return s + " WHERE ";
		}
	}
}
