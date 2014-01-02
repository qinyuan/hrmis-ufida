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

	public ResumeModifyTable(int resumeId) {
		r = ResumeDao.getInstance(resumeId);
	}

	@Override
	public String toString() {
		if (r == null)
			return "";
		Table table = new Table();

		TableRow tr = new TableRow();
		tr.add("姓名", getApplicant() + space, "创建时间", getAddTime());
		table.add(tr);

		tr = new TableRow();
		tr.add("创建者", getCreator() + space, "公司", getCompany());
		table.add(tr);

		tr = new TableRow();
		tr.add("电话", getTel() + space, "email", getEmail());
		table.add(tr);

		tr = new TableRow();
		tr.add("性别", getGender() + space, "简历来源", getSource());
		table.add(tr);

		tr = new TableRow();
		tr.add("简历链接", getResumeLink() + space, "简历编号", getResumeNo());
		table.add(tr);

		tr = new TableRow();
		tr.add("职位", getPost() + space, "目标地点", getTargetPlace());
		table.add(tr);

		tr = new TableRow();
		tr.add("工作年限", getExperience() + space, "期望薪资", getExpectSalary());
		table.add(tr);

		tr = new TableRow();
		tr.add("qq", getQq() + space, "意向", getIntention());
		table.add(tr);

		tr = new TableRow();
		tr.add("离职原因");
		tr.add(getTableData("m_jhReason", r.getJhReason()));
		table.add(tr);

		tr = new TableRow();
		tr.add("教育情况");
		tr.add(getTableData("m_education", r.getEducation()));
		table.add(tr);

		tr = new TableRow();
		tr.add("技术能力");
		tr.add(getTableData("m_skill", r.getSkill()));
		table.add(tr);

		tr = new TableRow();
		tr.add("工作经历");
		tr.add(getTableData("m_prevJob", r.getPrevJob()));
		table.add(tr);

		tr = new TableRow();
		tr.add("项目经验");
		tr.add(getTableData("m_prevProj", r.getPrevProj()));
		table.add(tr);

		tr = new TableRow();
		tr.add("备注");
		tr.add(getTableData("m_other", r.getOther()));
		table.add(tr);

		return new Hidden().setId("m_resumeId").setValue(r.getId())
				+ table.toString();
	}

	private Text getApplicant() {
		return new Text().setId("m_applicant").setValue(r.getApplicant());
	}

	private Text getAddTime() {
		return new Text().setId("m_addTime").setValue(r.getLongAddTime()).setDisabled(true);
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
		return new Text().setId("m_experience").setValue(
				String.valueOf(r.getExperience()));
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
