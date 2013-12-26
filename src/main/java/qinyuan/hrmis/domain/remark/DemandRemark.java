package qinyuan.hrmis.domain.remark;

import java.sql.SQLException;

import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.date.MyDateTime;

public class DemandRemark {

	private int id;
	private int demandId;
	private int userId;
	private String username;
	private String createTime;
	private String content;

	public int getId() {
		return id;
	}

	public int getDemandId() {
		return demandId;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getContent() {
		return content;
	}

	private final static String TABLE_NAME = "rec_demand_remark";

	public static DemandRemark[] getInstancesByDemandId(int demandId)
			throws SQLException {
		return getInstancesByWhereClause("r.demand_id=" + demandId);
	}

	public static DemandRemark[] getInstances() throws SQLException {
		return getInstancesByWhereClause("");
	}

	private static DemandRemark[] getInstancesByWhereClause(String whereClause)
			throws SQLException {
		String query = "SELECT * FROM " + TABLE_NAME
				+ " AS r LEFT JOIN u_user AS u USING(user_id)";
		if (!whereClause.trim().isEmpty()) {
			query += " WHERE (" + whereClause + ")";
		}
		query += " ORDER BY r.demand_remark_id DESC";
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			DemandRemark[] drs = new DemandRemark[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				DemandRemark dr = new DemandRemark();
				dr.id = cnn.getInt("r.demand_remark_id");
				dr.demandId = cnn.getInt("r.demand_id");
				dr.userId = cnn.getInt("r.user_id");
				dr.username = cnn.getString("u.name");
				dr.createTime = cnn.getString("create_time").replace(".0", "");
				dr.content = cnn.getString("r.content");
				drs[i++] = dr;
			}
			return drs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static int add(int demandId, int userId, String content)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare("INSERT INTO " + TABLE_NAME
					+ "(demand_id,user_id,create_time,content) VALUES(?,?,?,?)");
			cnn.setInt(1, demandId).setInt(2, userId).setString(3, getNow())
					.setString(4, content).execute();
			return cnn.getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void del(int id) throws SQLException {
		HRMIS.execute("DELETE FROM " + TABLE_NAME + " WHERE demand_remark_id="
				+ id);
	}

	public static void mdf(int id, String content) throws SQLException {

		try (MyConn cnn = new MyConn()) {
			cnn.prepare("UPDATE " + TABLE_NAME
					+ " SET content=?,create_time=? WHERE demand_remark_id=?");
			cnn.setString(1, content).setString(2, getNow()).setInt(3, id)
					.execute();
		}
	}

	private static String getNow() {
		return new MyDateTime().toString();
	}
}
