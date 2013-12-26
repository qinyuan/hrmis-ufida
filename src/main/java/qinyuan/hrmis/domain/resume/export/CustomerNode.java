package qinyuan.hrmis.domain.resume.export;

import java.util.ArrayList;
import java.util.List;
import qinyuan.hrmis.dao.Customer;

public class CustomerNode implements ExportNode {

	private Customer customer;
	private List<DemandNode> demandNodeList;

	public CustomerNode(Customer customer) {
		this.customer = customer;
	}

	public void addDemandNode(DemandNode demandNode) {
		if (demandNodeList == null) {
			demandNodeList = new ArrayList<DemandNode>();
		}
		demandNodeList.add(demandNode);
	}

	@Override
	public void output(SheetNode sheetNode) {
		sheetNode.setValue(1, customer.getName());

		int startRowIndex = sheetNode.getRowIndex();
		if (demandNodeList != null) {
			for (DemandNode demandNode : demandNodeList) {
				demandNode.output(sheetNode);
			}
		}
		int endRowIndex = sheetNode.getRowIndex();
		sheetNode.merge(startRowIndex, endRowIndex, 1);
	}

	public static boolean equals(CustomerNode node1, CustomerNode node2) {
		if (node1 == null || node2 == null) {
			return false;
		} else {
			return node1.customer.getId() == node2.customer.getId();
		}
	}
}
