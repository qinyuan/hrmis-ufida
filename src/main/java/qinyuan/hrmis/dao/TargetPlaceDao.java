package qinyuan.hrmis.dao;

import java.util.List;
import qinyuan.lib.db.HConn;

public class TargetPlaceDao {

	public static void add(String name) {
		TargetPlace tp = new TargetPlace();
		tp.setName(name);
		HConn.saveOne(tp);
	}

	public static void delete(int id) {
		HConn.deleteOne(TargetPlace.class, id);
	}

	public static void modify(int id, String name) {
		TargetPlace tp=getInstance(id);
		tp.setName(name);
		HConn.updateOne(tp);
	}

	public static TargetPlace[] getInstances() {
		List<TargetPlace> list = HConn.getOneList(
				"FROM TargetPlace ORDER BY id", TargetPlace.class);
		TargetPlace[] tps = new TargetPlace[list.size()];
		list.toArray(tps);
		return tps;
	}

	public static TargetPlace getInstance(int targetPlaceId) {
		return HConn.getOne(TargetPlace.class, targetPlaceId);
	}
}
