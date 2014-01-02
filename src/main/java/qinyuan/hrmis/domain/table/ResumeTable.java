package qinyuan.hrmis.domain.table;

import static qinyuan.lib.web.html.ImageUtil.getDelImage;
import static qinyuan.lib.web.html.ImageUtil.getDownArrowImage;
import static qinyuan.lib.web.html.ImageUtil.getMdfImage;
import static qinyuan.lib.web.html.ImageUtil.getRightArrowImage;
import java.sql.SQLException;
import qinyuan.hrmis.dao.SimpleDemandDao;
import qinyuan.hrmis.domain.recommend.Status;
import qinyuan.hrmis.domain.user.User;
import qinyuan.hrmis.lib.db.MyDataSource;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.pagination.BasePaginatedTable;
import qinyuan.lib.web.html.pagination.TableHead;

public class ResumeTable extends BasePaginatedTable {

	private User user;
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
	private boolean editable = true;

	public ResumeTable() {
		super(new MyDataSource());
	}

	public ResumeTable setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	public ResumeTable setEndAddDate(String endAddDate) {
		this.endAddDate = MyDate.isDate(endAddDate) ? endAddDate : null;
		return this;
	}

	public ResumeTable setEndHandleDate(String endHandleDate) {
		this.endHandleDate = MyDate.isDate(endHandleDate) ? endHandleDate
				: null;
		return this;
	}

	public ResumeTable setEndMdfDate(String endMdfDate) {
		this.endMdfDate = MyDate.isDate(endMdfDate) ? endMdfDate : null;
		return this;
	}

	public ResumeTable setMarkStatus(int markStatus) {
		this.markStatus = markStatus;
		return this;
	}

	public ResumeTable setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public ResumeTable setSearchName(String searchName) {
		this.searchName = searchName;
		return this;
	}

	public ResumeTable setSearchTel(String searchTel) {
		this.searchTel = searchTel;
		return this;
	}

	public ResumeTable setStartAddDate(String startAddDate) {
		this.startAddDate = MyDate.isDate(startAddDate) ? startAddDate : null;
		return this;
	}

	public ResumeTable setStartHandleDate(String startHandleDate) {
		this.startHandleDate = MyDate.isDate(startHandleDate) ? startHandleDate
				: null;
		return this;
	}

	public ResumeTable setStartMdfDate(String startMdfDate) {
		this.startMdfDate = MyDate.isDate(startMdfDate) ? startMdfDate : null;
		return this;
	}

	public ResumeTable setStatusId(int statusId) {
		this.status = statusId;
		return this;
	}

	public ResumeTable setTargetPlaceId(int targetPlaceId) {
		this.targetPlaceId = targetPlaceId;
		return this;
	}

	public ResumeTable setUser(User user) {
		this.user = user;
		return this;
	}

	public ResumeTable setRecruiterId(int recruiterId) {
		this.recruiterId = recruiterId;
		return this;
	}

	@Override
	protected String getQuery() {
		String recommendSubQuery = "(SELECT *,MAX(recommend_id) "
				+ "FROM rec_recommend GROUP BY resume_id)";
		String recStepSubQuery = "(SELECT *,Max(status_id) AS max_status_id "
				+ "FROM rec_rec_step GROUP BY recommend_id)";
		String s = "SELECT * FROM rec_resume AS r "
				+ "LEFT JOIN u_user AS u1 ON r.creator_id=u1.user_id "
				+ "LEFT JOIN u_user AS u2 ON r.tracer_id=u2.user_id "
				+ "LEFT JOIN rec_post AS p ON r.post_id=p.post_id "
				+ "LEFT JOIN "
				+ recommendSubQuery
				+ " AS rm ON r.resume_id=rm.resume_id "
				+ "LEFT JOIN rec_demand AS d ON rm.demand_id=d.demand_id "
				+ "LEFT JOIN "
				+ recStepSubQuery
				+ " AS rs ON rm.recommend_id=rs.recommend_id "
				+ "LEFT JOIN rec_status AS s ON rs.max_status_id=s.status_id "
				+ "LEFT JOIN rec_target_place AS tp ON tp.target_place_id=r.target_place_id ";
		s = s + getWhereClause() + " ORDER BY add_time DESC";
		return s;
	}

	private String getWhereClause() {
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
			String ss = "(r.resume_id IN (SELECT resume_id FROM rec_recommend "
					+ "WHERE recommend_id IN (SELECT recommend_id FROM rec_rec_step "
					+ "WHERE status_id=" + status;
			if (startHandleDate != null)
				ss += " AND DATE(deal_time)>='" + startHandleDate + "'";
			if (endHandleDate != null)
				ss += " AND DATE(deal_time)<='" + endHandleDate + "'";
			ss += ")))";
			s += ss;
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

	private void addEditTableData(TableRow tr, int resumeId, int recommendId)
			throws SQLException {
		StringBuilder s = new StringBuilder();
		s.append(getMdfImage(resumeId));
		s.append(getDelImage(resumeId));
		if (recommendId > 0) {
			s.append(getDownArrowImage("detail" + resumeId, "查看推荐记录"));
		} else {
			s.append(getRightArrowImage("recommend" + resumeId, "推荐"));
		}
		tr.add(s);
	}

	@Override
	protected void doTableRow(TableRow tr, MySQLConn cnn) throws SQLException {
		int resumeId = cnn.getInt("r.resume_id");
		tr.setId("resume" + resumeId);
		tr.add(getNewLink("resume-detail.jsp?id=" + resumeId,
				cnn.getString("applicant")));

		String addTime = cnn.getString("add_time").substring(2, 16);
		tr.add(addTime);
		String mdfTime = cnn.getString("mdf_time");
		if (mdfTime == null) {
			mdfTime = addTime;
		} else {
			mdfTime = mdfTime.substring(2, 16);
		}
		tr.add(mdfTime);

		tr.add(cnn.getString("u1.name"));
		String postName = cnn.getString("p.name");
		if (cnn.getInt("tp.target_place_id") > 1) {
			postName += "(" + cnn.getString("tp.name") + ")";
		}
		tr.add(postName);
		tr.add(cnn.getString("tel"));

		String intention = cnn.getString("intention");
		if (cnn.getBoolean("intention_red")) {
			tr.add("<span style='color:red;'>" + intention + "<span>");
		} else {
			tr.add(intention);
		}

		tr.add(cnn.getString("expect_salary"));
		tr.add(SimpleDemandDao.getFullDemandName(cnn.getInt("d.demand_id")));
		tr.add(Status.get(cnn.getInt("s.status_id")));

		if (editable && isEditable(cnn)) {
			int recommendId = cnn.getInt("rm.recommend_id");
			addEditTableData(tr, resumeId, recommendId);
		} else {
			tr.add("");
		}
	}

	private boolean isEditable(MySQLConn cnn) throws SQLException {
		return user != null && (user.hasPri(5) || user.hasPri(6))
				&& user.hasSubUser(cnn.getInt("r.creator_id"));
	}

	@Override
	protected void doTableHead(TableHead th) {
		th.add("姓名", 40);
		th.add("创建时间", 100);
		th.add("修改时间", 100);
		th.add("创建者", 50);
		th.add("职位", 70);
		th.add("电话", 70);
		th.add("意向", 150);
		th.add("期望薪资", 60);
		th.add("推送岗位", 110);
		th.add("状态", 50);
		th.add("");
	}

	@Override
	protected String getTitle() {
		return "简历列表";
	}
}
