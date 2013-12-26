package qinyuan.hrmis.domain.remark;

import java.sql.SQLException;

import qinyuan.hrmis.dao.CustomerDao;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.date.MyDateTime;

public class CustomerRemark {

	private int id;
	private int cusId;
	private int userId;
	private String username;
	private String createTime;
	private String content;

	public int getId() {
		return id;
	}

	public int getCusId() {
		return cusId;
	}

	public String getCusName() throws SQLException {
		return CustomerDao.getFullCusName(cusId);
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

	private final static String TABLE_NAME = "rec_customer_remark";

	public static CustomerRemark[] getInstancesByCusId(int cusId)
			throws SQLException {
		return getInstancesByWhereClause("r.customer_id=" + cusId);
	}

	public static CustomerRemark[] getInstances() throws SQLException {
		return getInstancesByWhereClause("");
	}

	private static CustomerRemark[] getInstancesByWhereClause(String whereClause)
			throws SQLException {
		String query = "SELECT * FROM " + TABLE_NAME
				+ " AS r LEFT JOIN u_user AS u USING(user_id)";
		if (!whereClause.trim().isEmpty()) {
			query += " WHERE (" + whereClause + ")";
		}
		query += " ORDER BY r.customer_remark_id DESC";
		try (MyConn cnn = new MyConn()) {
			cnn.execute(query);
			CustomerRemark[] drs = new CustomerRemark[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				CustomerRemark dr = new CustomerRemark();
				dr.id = cnn.getInt("r.customer_remark_id");
				dr.cusId = cnn.getInt("r.customer_id");
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

	public static int add(int cusId, int userId, String content)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare("INSERT INTO "
					+ TABLE_NAME
					+ "(customer_id,user_id,create_time,content) VALUES(?,?,?,?)");
			cnn.setInt(1, cusId).setInt(2, userId).setString(3, getNow())
					.setString(4, content).execute();
			return cnn.getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void del(int id) throws SQLException {
		HRMIS.execute("DELETE FROM " + TABLE_NAME
				+ " WHERE customer_remark_id=" + id);
	}

	public static void mdf(int id, String content) throws SQLException {

		try (MyConn cnn = new MyConn()) {
			cnn.prepare("UPDATE " + TABLE_NAME
					+ " SET content=?,create_time=? WHERE customer_remark_id=?");
			cnn.setString(1, content).setString(2, getNow()).setInt(3, id)
					.execute();
		}
	}

	private static String getNow() {
		return new MyDateTime().toString();
	}
}
