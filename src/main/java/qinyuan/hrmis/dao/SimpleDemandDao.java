package qinyuan.hrmis.dao;

import java.util.List;
import qinyuan.lib.db.HConn;

public class SimpleDemandDao {

	public static SimpleDemand getInstance(int demandId) {
		return HConn.getOne(SimpleDemand.class, demandId);
	}

	public static SimpleDemand[] getInstancesByPostId(int postId) {
		return getInstancesByWhereClause("active=TRUE AND post.id=" + postId);
	}

	private static SimpleDemand[] getInstancesByWhereClause(String whereClause) {
		List<SimpleDemand> list = HConn.getOneList("FROM SimpleDemand WHERE "
				+ whereClause, SimpleDemand.class);
		SimpleDemand[] sds = new SimpleDemand[list.size()];
		list.toArray(sds);
		return sds;
	}

	public static SimpleDemand[] getInstancesByCusId(int cusId) {
		return getInstancesByWhereClause("active=TRUE AND customer.id=" + cusId);
	}

	public static SimpleDemand[] getActiveInstancesByCusId(int cusId) {
		return getInstancesByWhereClause("active=TRUE AND pause=FALSE AND customer.id="
				+ cusId);
	}

	public static SimpleDemand[] getPauseInstancesByCusId(int cusId) {
		return getInstancesByWhereClause("active=TRUE AND pause=TRUE AND customer.id="
				+ cusId);
	}

	public static SimpleDemand[] getCloseInstancesByCusId(int cusId) {
		return getInstancesByWhereClause("active=FALSE AND customer.id="
				+ cusId);
	}

	public static SimpleDemand[] getInstances() {
		return getInstancesByWhereClause("active=TRUE");
	}

	public static SimpleDemand[] getCloseInstances() {
		return getInstancesByWhereClause("active=FALSE OR active IS NULL");
	}

	public static String getFullDemandName(int demandId) {
		SimpleDemand sd = SimpleDemandDao.getInstance(demandId);
		return sd == null ? "" : sd.getFullName();
	}

	public static void main(String[] args) {
		getInstances();
	}
}
