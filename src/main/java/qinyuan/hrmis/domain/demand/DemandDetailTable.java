package qinyuan.hrmis.domain.demand;

import qinyuan.hrmis.dao.Demand;
import qinyuan.hrmis.dao.DemandDao;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public class DemandDetailTable {

	private int demandId;

	public DemandDetailTable(int demandId) {
		this.demandId = demandId;
	}

	public String toString() {
		Table table = new Table();

		Demand d = DemandDao.getInstance(demandId);
		TableRow tr = new TableRow();
		tr.add("需求全称");
		tr.add(getTd("name", d.getName()));
		table.add(tr);

		tr = new TableRow();
		tr.add("职位类型");
		tr.add(getTd("post", d.getPostName()));
		table.add(tr);

		tr = new TableRow();
		tr.add("工作地点");
		tr.add(getTd("targetPlace", d.getTargetPlaceName()));
		table.add(tr);

		tr = new TableRow();
		tr.add("岗位职责");
		tr.add(getTd("duty", d.getDuty()));
		table.add(tr);

		tr = new TableRow();
		tr.add("任职资格");
		tr.add(getTd("qualification", d.getQualification()));
		table.add(tr);

		tr = new TableRow();
		tr.add("岗位薪资");
		tr.add(getTd("salary", d.getSalary()));
		table.add(tr);

		tr = new TableRow();
		tr.add("需求人数");
		tr.add(getTd("postNumber", d.getPostNumber()));
		table.add(tr);

		tr = new TableRow();
		tr.add("发布日期");
		tr.add(getTd("startDate", d.getStartDate()));
		table.add(tr);

		tr = new TableRow();
		tr.add("结束日期");
		tr.add(getTd("endDate", d.getEndDate()));
		table.add(tr);

		tr = new TableRow();
		tr.add("需求状态");
		tr.add(getTd("active", d.getDemandStatus()));
		table.add(tr);

		return table.toString().replace("\n", "\n<br />");
	}

	private TableData getTd(String id, Object text) {
		if (text == null)
			text = "";
		return new TableData().setId(id).setText(text.toString());
	}
}
