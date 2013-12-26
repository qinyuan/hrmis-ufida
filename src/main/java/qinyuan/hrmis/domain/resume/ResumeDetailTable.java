package qinyuan.hrmis.domain.resume;

import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public class ResumeDetailTable {

	private int resumeId;

	public ResumeDetailTable(int resumeId) {
		this.resumeId = resumeId;
	}

	@Override
	public String toString() {
		Resume r = ResumeDao.getInstance(resumeId);
		if (r == null)
			return "";

		Table table = new Table();

		TableRow tr = new TableRow();
		tr.add("姓名", r.getApplicant(), "性别", r.getGenderName());
		table.add(tr);

		tr = new TableRow();
		tr.add("创建时间", r.getAddTime(), "修改时间", r.getMdfTime());
		table.add(tr);

		tr = new TableRow();
		tr.add("创建者", r.getCreatorName(), "公司", r.getCompany());
		table.add(tr);

		tr = new TableRow();
		tr.add("电话", r.getTel(), "Email", r.getEmail());
		table.add(tr);

		tr = new TableRow();
		tr.add("简历来源", r.getSourceName(), "是否被下载", r.getDownloaded() ? "是"
				: "否");
		table.add(tr);

		tr = new TableRow();
		tr.add("简历链接", getResumeLinkAnchor(r.getResumeLink()), "简历编号",
				r.getResumeNo());
		table.add(tr);

		tr = new TableRow();
		tr.add("职位", r.getPostName(), "目标地点", r.getTargetPlaceName());
		table.add(tr);

		tr = new TableRow();
		tr.add("工作年限", r.getExperience(), "期望薪资", r.getExpectSalary());
		table.add(tr);

		tr = new TableRow();
		tr.add("QQ", r.getQq(), "意向", r.getIntentionSpan());
		table.add(tr);

		tr = new TableRow();
		tr.add("离职原因");
		tr.add(getMultiTd(r.getJhReason()));
		table.add(tr);

		tr = new TableRow();
		tr.add("教育情况");
		tr.add(getMultiTd(r.getEducation()));
		table.add(tr);

		tr = new TableRow();
		tr.add("技术能力");
		tr.add(getMultiTd(r.getSkill()));
		table.add(tr);

		tr = new TableRow();
		tr.add("工作经历");
		tr.add(getMultiTd(r.getPrevJob()));
		table.add(tr);

		tr = new TableRow();
		tr.add("项目经验");
		tr.add(getMultiTd(r.getPrevProj()));
		table.add(tr);

		tr = new TableRow();
		tr.add("备注");
		tr.add(getMultiTd(r.getOther()));
		table.add(tr);

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
