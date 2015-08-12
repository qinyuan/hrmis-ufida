package qinyuan.hrmis.domain.resume.export;

import qinyuan.hrmis.dao.Demand;

public class DemandNode implements ExportNode {

	private Demand demand;

	public DemandNode(Demand demand) {
		this.demand = demand;
	}

	public void output(SheetNode sheetNode) {
		String demandName = demand.getName();
		if (demand.isPause()) {
			demandName += "(暂停)";
		}
		sheetNode.setValue(2, demandName);
		sheetNode.setValue(3, demand.getPostNumber());
		sheetNode.outputResumeCounts(demand);

		sheetNode.next();
	}
}
