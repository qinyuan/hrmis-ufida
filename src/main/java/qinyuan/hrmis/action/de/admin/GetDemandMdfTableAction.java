package qinyuan.hrmis.action.de.admin;

import qinyuan.hrmis.domain.demand.DemandModifyTable;
import qinyuan.lib.web.MyAction;

public class GetDemandMdfTableAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int demandId = getInt("demandId");
		if (demandId > 0) {
			DemandModifyTable t = new DemandModifyTable(demandId);
			setResult(t.toString().replace("null", ""));
		}
		return BLANK_AJAX_INFO;
	}
}
