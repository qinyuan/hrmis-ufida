package qinyuan.hrmis.dao;

import java.sql.SQLException;
import java.util.List;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.lib.db.HConn;

public class SimpleSourceDao {

	public static SimpleSource getInstance(int sourceId) {
		return HConn.getOne(SimpleSource.class, sourceId);
	}

	public static SimpleSource[] getInstances() {
		List<SimpleSource> list = HConn.getOneList("FROM SimpleSource",
				SimpleSource.class);
		SimpleSource[] sss = new SimpleSource[list.size()];
		list.toArray(sss);
		return sss;
	}

	public static boolean isUsed(int sourceId) throws SQLException {
		return HRMIS.exists("FROM rec_resume WHERE source_id=" + sourceId);
	}

	public static void delete(int id) {
		HConn.deleteOne(SimpleSource.class, id);
	}

	public static boolean exist(String sourceName) throws Exception {
		return HRMIS.exists("FROM rec_source WHERE name='" + sourceName + "'");
	}
	
	public static void main(String[] args) {
		getInstances();
	}
}
