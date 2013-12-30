package qinyuan.hrmis.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.db.HConn;

public class SimpleUserDao {

	public static void mdf(int userId, String name, String password) {
		SimpleUser su = getInstance(userId);
		su.setId(userId);
		su.setName(name);
		su.setPassword(password);
		HConn.updateOne(su);
	}

	public static void mdfSuperior(int userId, int supUserId) {
		SimpleUser su = getInstance(userId);
		if (supUserId > 0) {
			su.setSuperior(supUserId);
		} else {
			su.setSuperior(null);
		}
		HConn.updateOne(su);
	}

	public static SimpleUser getInstance(int userId) {
		return HConn.getOne(SimpleUser.class, userId);
	}

	public static SimpleUser[] getInstances() {
		return HConn.getOneArray("FROM SimpleUser", SimpleUser.class);
	}

	public static void add(String username, String password, int superiorId) {
		SimpleUser su = new SimpleUser();
		su.setName(username);
		su.setPassword(password);
		su.setSuperior(superiorId);
		HConn.saveOne(su);
	}

	public static void add(String username, String password) {
		SimpleUser su = new SimpleUser();
		su.setName(username);
		su.setPassword(password);
		HConn.saveOne(su);
	}

	public static boolean isUsed(int userId) {
		List<String> querys = new ArrayList<String>();
		querys.add("FROM rec_resume WHERE creator_id=" + userId
				+ " OR tracer_id=" + userId);
		querys.add("FROM rec_demand_remark WHERE user_id=" + userId);
		querys.add("FROM rec_customer_remark WHERE user_id=" + userId);
		for (String query : querys) {
			if (HRMIS.exists(query))
				return true;
		}
		return false;
	}

	public static boolean exists(String username) throws Exception {
		return HRMIS.exists("FROM u_user WHERE name='" + username + "'");
	}

	public static void del(int userId) throws SQLException {
		if (!isUsed(userId)) {
			HConn.deleteOne(SimpleUser.class, userId);
		}
	}

	public static Set<Integer> getSubUserIds(int userId) throws SQLException {
		Set<Integer> set = new HashSet<Integer>();
		set.add(userId);
		Set<Integer> tempSet = getDirSubUserIds(userId);
		for (Integer subUserId : tempSet) {
			set.addAll(getSubUserIds(subUserId));
		}
		return set;
	}

	private static Set<Integer> getDirSubUserIds(int userId)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			Set<Integer> set = new HashSet<Integer>();
			cnn.prepare("SELECT * FROM u_user WHERE superior=?")
					.setInt(1, userId).execute();
			while (cnn.next()) {
				set.add(cnn.getInt("user_id"));
			}
			return set;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(getSubUserIds(8));
	}
}
