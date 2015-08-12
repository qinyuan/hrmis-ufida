package qinyuan.hrmis.domain.demand;

import qinyuan.hrmis.dao.Demand;
import qinyuan.hrmis.dao.DemandDao;
import qinyuan.hrmis.domain.post.PostSelect;
import qinyuan.hrmis.domain.targetplace.TargetPlaceSelect;
import qinyuan.lib.web.html.Hidden;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.Text;

public class DemandModifyTable {

	private int demandId;
	private Table table;

	public DemandModifyTable(int demandId) {
		this.demandId = demandId;
	}

	public String toString() {
		table = new Table();
		Demand d = DemandDao.getInstance(demandId);

		add("需求全称", getText("name", d.getName()));
		add("职位类型", new PostSelect(d.getPostId()).toSelect().setId("postId"));
		add("工作地点", new TargetPlaceSelect().setTagId("targetPlaceId")
				.setDefaultId(d.getTargetPlaceId()));
		add("岗位职责", getArea("duty", d.getDuty()));
		add("岗位要求", getArea("qualification", d.getQualification()));
		add("薪资状况", getText("salary", d.getSalary()));
		add("需求人数", getText("postNumber", d.getPostNumber()));
		add("发布日期", getText("startDate", d.getStartDate()));
		add("结束日期", getText("endDate", d.getEndDate()));
		add("需求状态", DemandStatus.getInstance(d.isActive(), d.isPause()));

		String result = table.toString();
		result += new Hidden().setId("demandId").setValue(d.getId());
		result += new Hidden().setId("cusId").setValue(d.getCustomerId());

		return result;
	}

	private void add(String col1, Object col2) {
		if (col1 == null) {
			col1 = "";
		}
		if (col2 == null) {
			col2 = "";
		}
		TableRow tr = new TableRow();
		tr.add(col1, col2);
		table.add(tr);
	}

	private static String getText(String id, Object text) {
		if (text == null) {
			text = "";
		}
		return new Text().setId(id).setValue(text.toString()).toString();
	}

	private static String getArea(String id, String text) {
		if (text == null) {
			text = "";
		}
		return "<textarea id='" + id + "' name='" + id
				+ "' rows='8' cols='65'>" + text + "</textarea>";
	}
}
