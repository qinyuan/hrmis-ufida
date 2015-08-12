package qinyuan.hrmis.dao;

import java.util.List;

import qinyuan.lib.db.HConn;

public class DemandFactory extends AbstractDemandFactory {

	public Demand[] getInstances() {
		List<Demand> list = HConn.getOneList("FROM Demand" + getWhereClause(),
				Demand.class);
		Demand[] demands = new Demand[list.size()];
		list.toArray(demands);
		return demands;
	}
}
