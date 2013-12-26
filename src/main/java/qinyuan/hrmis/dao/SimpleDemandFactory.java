package qinyuan.hrmis.dao;

import java.util.List;

import qinyuan.lib.db.HConn;

public class SimpleDemandFactory extends AbstractDemandFactory {

	@Override
	public SimpleDemand[] getInstances() {
		List<SimpleDemand> list = HConn.getOneList("FROM SimpleDemand"
				+ getWhereClause(), SimpleDemand.class);
		SimpleDemand[] sds = new SimpleDemand[list.size()];
		list.toArray(sds);
		return sds;
	}
}