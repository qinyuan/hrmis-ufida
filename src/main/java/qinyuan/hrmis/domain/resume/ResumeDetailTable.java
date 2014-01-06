package qinyuan.hrmis.domain.resume;

import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public class ResumeDetailTable {

	private int resumeId;
	private Table table;

	public ResumeDetailTable(int resumeId) {
		this.resumeId = resumeId;
	}

	private void addRow(Object col1, Object col2, Object col3, Object col4) {
		TableRow tr = new TableRow();
		tr.add(col1, col2, col3, col4);
		table.add(tr);
	}

	private void addRow(Object col1, Object col2) {
		TableRow tr = new TableRow();
		tr.add(col1, getMultiTd(col2.toString()));
		table.add(tr);
	}

	@Override
	public String toString() {
		Resume r = ResumeDao.getInstance(resumeId);
		if (r == null)
			return "";

		table = new Table();

		addRow("姓名", r.getApplicant(), "性别", r.getGenderName());
		addRow("创建时间", r.getAddTime(), "修改时间", r.getMdfTime());
		addRow("创建者", r.getCreatorName(), "公司", r.getCompany());
		addRow("电话", r.getTel(), "Email", r.getEmail());
		addRow("简历来源", r.getSourceName(), "是否被下载", r.getDownloaded() ? "是"
				: "否");
		addRow("简历链接", getResumeLinkAnchor(r.getResumeLink()), "简历编号",
				r.getResumeNo());
		addRow("职位", r.getPostName(), "目标地点", r.getTargetPlaceName());
		addRow("工作年限", r.getExperience(), "期望薪资", r.getExpectSalary());
		addRow("QQ", r.getQq(), "意向", r.getIntentionSpan());

		addRow("离职原因", r.getJhReason());
		addRow("教育情况", r.getEducation());
		addRow("技术能力", r.getSkill());
		addRow("工作经历", r.getPrevJob());
		addRow("项目经验", r.getPrevProj());
		addRow("备注", r.getOther());

		return table.toString();
	}

	private static TableData getMultiTd(String text) {
		TableData td = new TableData();
		td.setAttr("colspan", "3");
		if (text == null) {
			td.setText("");
		} else {
			td.setText(text.replace("\n", "<br />\n"));
		}
		return td;
	}

	private static String getResumeLinkAnchor(String resumeLink) {
		if (resumeLink == null)
			return "";

		resumeLink = resumeLink.trim();
		int shortLinkLen = 20;
		String shortResumeLink = resumeLink.length() > shortLinkLen ? resumeLink
				.substring(0, shortLinkLen) + "..."
				: resumeLink;
		if (!resumeLink.isEmpty()) {
			return "<a href='" + resumeLink + "' title='" + resumeLink
					+ "' target='_blank'>" + shortResumeLink + "</a>";
		} else {
			return "";
		}
	}
}
