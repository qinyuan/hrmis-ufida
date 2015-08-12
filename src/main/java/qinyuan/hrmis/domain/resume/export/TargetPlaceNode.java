package qinyuan.hrmis.domain.resume.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import qinyuan.hrmis.dao.Demand;
import qinyuan.hrmis.dao.DemandFactory;
import qinyuan.hrmis.dao.TargetPlace;
import qinyuan.hrmis.dao.TargetPlaceDao;

public class TargetPlaceNode implements ExportNode {

	private TargetPlace targetPlace;

	private List<CustomerNode> customerNodeList = new ArrayList<CustomerNode>();

	public TargetPlaceNode(TargetPlace targetPlace) {
		this.targetPlace = targetPlace;

		DemandFactory df = new DemandFactory();
		df.setActive(true).setTargetPlaceId(targetPlace.getId());
		Demand[] demands = df.getInstances();
		Arrays.sort(demands, new Comparator<Demand>() {
			@Override
			public int compare(Demand o1, Demand o2) {
				int postWeight = 100000;
				int result1 = o1.getCustomerId();
				if (!o1.isPause()) {
					result1 += postWeight;
				}
				int result2 = o2.getCustomerId();
				if (!o2.isPause()) {
					result2 += postWeight;
				}
				return result2 - result1;
			}
		});

		CustomerNode lastCustomerNode = null;
		CustomerNode newCustomerNode;
		for (Demand demand : demands) {
			newCustomerNode = new CustomerNode(demand.getCustomer());
			if (!CustomerNode.equals(newCustomerNode, lastCustomerNode)) {
				lastCustomerNode = newCustomerNode;
				customerNodeList.add(lastCustomerNode);
			}
			lastCustomerNode.addDemandNode(new DemandNode(demand));
		}
	}

	public String getText() {
		return targetPlace.getName();
	}

	public void output(SheetNode sheetNode) {
		sheetNode.setValue(0, targetPlace.getName());

		int startRowIndex = sheetNode.getRowIndex();
		for (CustomerNode customerNode : customerNodeList) {
			customerNode.output(sheetNode);
		}
		int endRowIndex = sheetNode.getRowIndex();
		sheetNode.merge(startRowIndex, endRowIndex, 0);
	}

	public static TargetPlaceNode[] getInstances() {
		TargetPlace[] targetPlaces = TargetPlaceDao.getInstances();
		int len = targetPlaces.length;
		TargetPlaceNode[] targetPlaceNodes = new TargetPlaceNode[len];
		for (int i = 0; i < len; i++) {
			targetPlaceNodes[i] = new TargetPlaceNode(targetPlaces[i]);
		}
		return targetPlaceNodes;
	}
}
