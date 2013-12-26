package qinyuan.hrmis.domain.demand;

import qinyuan.hrmis.dao.Demand;
import qinyuan.hrmis.dao.DemandDao;
import qinyuan.hrmis.domain.post.PostSelect;
import qinyuan.hrmis.domain.targetplace.TargetPlaceSelect;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class DemandModifyTable {

	private int demandId;

	public DemandModifyTable(int demandId) {
		this.demandId = demandId;
	}

	public String toString() {
		Table table = new Table();

		Demand d = DemandDao.getInstance(demandId);
		TableRow tr = new TableRow();
		tr.add("需求全称", getText("name", d.getName()));
		table.add(tr);

		tr = new TableRow();
		tr.add("职位类型", new PostSelect(d.getPostId()).toSelect().setId("postId")
				.toString());
		table.add(tr);

		tr = new TableRow();
		tr.add("工作地点", new TargetPlaceSelect().setTagId("targetPlaceId")
				.setDefaultId(d.getTargetPlaceId()));
		table.add(tr);

		tr = new TableRow();
		tr.add("岗位职责", getArea("duty", d.getDuty()));
		table.add(tr);

		tr = new TableRow();
		tr.add("岗位要求", getArea("qualification", d.getQualification()));
		table.add(tr);

		tr = new TableRow();
		tr.add("薪资状况", getText("salary", d.getSalary()));
		table.add(tr);

		tr = new TableRow();
		tr.add("需求人数", getText("postNumber", d.getPostNumber()));
		table.add(tr);

		tr = new TableRow();
		tr.add("发布日期", getText("startDate", d.getStartDate()));
		table.add(tr);

		tr = new TableRow();
		tr.add("结束日期", getText("endDate", d.getEndDate()));
		table.add(tr);

		tr = new TableRow();
		tr.add("需求状态", DemandStatus.getInstance(d.isActive(), d.isPause()));
		table.add(tr);

		return table.toString();
	}

	private String getText(String id, Object text) {
		if (text == null) {
			text = "";
		}
		return "<input type='text' name='" + id + "' id='" + id + "' value='"
				+ text + "' />";
	}

	private String getArea(String id, String text) {
		if (text == null) {
			text = "";
		}
		return "<textarea id='" + id + "' name='" + id
				+ "' rows='8' cols='55'>" + text + "</textarea>";
	}
}
