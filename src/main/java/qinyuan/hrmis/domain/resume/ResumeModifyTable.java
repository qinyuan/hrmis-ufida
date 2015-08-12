package qinyuan.hrmis.domain.resume;

import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.hrmis.domain.gender.GenderSelect;
import qinyuan.hrmis.domain.post.PostSelect;
import qinyuan.hrmis.domain.resume.source.SourceSelect;
import qinyuan.hrmis.domain.targetplace.TargetPlaceSelect;
import qinyuan.lib.web.html.ChkBox;
import qinyuan.lib.web.html.Hidden;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.Text;

public class ResumeModifyTable {

	private final static String space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private Resume r;
	private Table table;

	public ResumeModifyTable(int resumeId) {
		r = ResumeDao.getInstance(resumeId);
	}

	@Override
	public String toString() {
		if (r == null)
			return "";
		table = new Table();

		addRow("姓名", getApplicant(), "创建时间", getAddTime());
		addRow("创建者", getCreator(), "公司", getCompany());
		addRow("电话", getTel(), "email", getEmail());
		addRow("性别", getGender(), "简历来源", getSource());
		addRow("简历链接", getResumeLink(), "简历编号", getResumeNo());
		addRow("职位", getPost(), "目标地点", getTargetPlace());
		addRow("工作年限", getExperience(), "期望薪资", getExpectSalary());
		addRow("qq", getQq(), "意向", getIntention());

		addRow("离职原因", getTableData("m_jhReason", r.getJhReason()));
		addRow("教育情况", getTableData("m_education", r.getEducation()));
		addRow("技术能力", getTableData("m_skill", r.getSkill()));
		addRow("工作经历", getTableData("m_prevJob", r.getPrevJob()));
		addRow("项目经验", getTableData("m_prevProj", r.getPrevProj()));
		addRow("备注", getTableData("m_other", r.getOther()));

		return new Hidden().setId("m_resumeId").setValue(r.getId())
				+ table.toString();
	}

	private void addRow(Object str1, TableData str2) {
		TableRow tr = new TableRow();
		tr.add(createRightAlignTd(str1 + "&nbsp;&nbsp;"));
		tr.add(str2);
		table.add(tr);
	}

	private void addRow(Object str1, Object str2, Object str3, Object str4) {
		TableRow tr = new TableRow();

		tr.add(createRightAlignTd(str1 + "&nbsp;&nbsp;"));
		tr.add(str2 + space);
		tr.add(createRightAlignTd(str3 + "&nbsp;&nbsp;"));
		tr.add(str4);

		table.add(tr);
	}

	private static TableData createRightAlignTd(String text) {
		return new TableData().setAttr("style", "text-align:right;").setText(
				text);
	}

	private Text getApplicant() {
		return new Text().setId("m_applicant").setValue(r.getApplicant());
	}

	private Text getAddTime() {
		return new Text().setId("m_addTime").setValue(r.getLongAddTime())
				.setDisabled(true);
	}

	private Text getCreator() {
		return new Text().setDisabled(true).setValue(r.getCreatorName());
	}

	private Text getCompany() {
		return new Text().setId("m_company").setValue(r.getCompany());
	}

	private Text getTel() {
		return new Text().setId("m_tel").setValue(r.getTel());
	}

	private Text getEmail() {
		return new Text().setId("m_email").setValue(r.getEmail());
	}

	private String getGender() {
		return new GenderSelect().setTagId("m_genderId")
				.setGenderId(r.getGenderId()).toString();
	}

	private String getSource() {
		return new SourceSelect(r.getSourceId()).toSelect().setId("m_sourceId")
				+ "&nbsp;&nbsp;"
				+ r.getDownloadedCheckBox().setId("m_downloaded") + "已下载";
	}

	private Text getResumeLink() {
		return new Text().setId("m_resumeLink").setValue(r.getResumeLink());
	}

	private Text getResumeNo() {
		return new Text().setId("m_resumeNo").setValue(r.getResumeNo());
	}

	private String getPost() {
		return new PostSelect(r.getPostId()).toSelect().setId("m_postId")
				.toString();
	}

	private String getTargetPlace() {
		return new TargetPlaceSelect().setTagId("m_targetPlaceId")
				.setDefaultId(r.getTargetPlaceId()).toString();
	}

	private Text getExperience() {
		return new Text().setId("m_experience").setValue(r.getExperienceStr());
	}

	private Text getExpectSalary() {
		return new Text().setId("m_expectSalary").setValue(r.getExpectSalary());
	}

	private Text getQq() {
		return new Text().setId("m_qq").setValue(r.getQq());
	}

	private String getIntention() {
		return new Text().setId("m_intention").setValue(r.getIntention())
				+ " "
				+ new ChkBox().setId("m_intentionRed").setChecked(
						r.isIntentionRed()) + "标记红色";
	}

	private static TableData getTableData(String id, String text) {
		if (text == null)
			text = "";
		TableData td = new TableData();
		td.setAttr("colspan", "3");
		td.setText("<textarea id='" + id + "' name='" + id
				+ "' rows='3' cols='70'>" + text + "</textarea>");
		return td;
	}
}
