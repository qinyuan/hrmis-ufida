package qinyuan.hrmis.domain.table;

import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.lib.web.html.Anchor;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class SimpleResumeTable {

	private Resume[] rs;

	public SimpleResumeTable(Resume[] rs) {
		this.rs = rs;
	}

	private void doTableRow(TableRow tr, Resume r) {
		int resumeId = r.getId();
		tr.setId("resume" + resumeId);

		Anchor a = new Anchor().setNew(true)
				.setHref("resume-detail.jsp?id=" + resumeId)
				.setText(r.getApplicant());
		tr.add(a);

		String addTime = r.getAddTime().substring(2, 16);
		tr.add(addTime);

		String mdfTime = r.getMdfTime();
		if (mdfTime == null) {
			mdfTime = addTime;
		} else {
			mdfTime = mdfTime.substring(2, 16);
		}
		tr.add(mdfTime);

		tr.add(r.getCreator());

		String postName = r.getPostName();
		if (r.getTargetPlaceId() > 1) {
			postName += "(" + r.getTargetPlaceName() + ")";
		}
		tr.add(postName);
		tr.add(r.getTel());

		String intention = r.getIntention();
		if (r.isIntentionRed()) {
			tr.add("<span style='color:red;'>" + intention + "<span>");
		} else {
			tr.add(intention);
		}

		tr.add(r.getExpectSalary());
	}

	@Override
	public String toString() {
		Table table = new Table();
		TableRow th = new TableRow();

		th.add("姓名");
		th.add("创建日期");
		th.add("修改日期");
		th.add("创建者");
		th.add("职位");
		th.add("电话");
		th.add("意向");
		th.add("期望薪资");

		table.addHead(th);

		for (Resume r : rs) {
			TableRow tr = new TableRow();
			doTableRow(tr, r);
			table.add(tr);
		}

		return table.toString();
	}
	
	public static void main(String[] args) {
		Resume[] rs=ResumeDao.getInstacesByTel("18918192577");
		SimpleResumeTable srt=new SimpleResumeTable(rs);
		System.out.println(srt);
	}
}
