package qinyuan.hrmis.dao;

import java.sql.SQLException;
import java.util.List;

import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.db.HConn;

public class CustomerDao {

	public static void add(String cusName) {
		add(cusName, -1);
	}

	public static void add(String cusName, int superior) {
		Customer cus = new Customer();
		cus.setName(cusName);
		if (superior > 0) {
			cus.setSuperior(superior);
		}
		HConn.saveOne(cus);
	}

	public static boolean isSuperior(int id) {
		return !HRMIS.empty("SELECT * FROM rec_customer WHERE superior=" + id);
	}

	public static boolean isUsed(int id) {
		return HRMIS.exists("FROM rec_demand WHERE customer_id=" + id);
	}

	public static boolean isCommented(int id) {
		return HRMIS.exists("FROM rec_customer_remark WHERE customer_id=" + id);
	}

	public static void delete(int id) {
		if (!isSuperior(id) && !isUsed(id)) {
			HConn.deleteOne(Customer.class, id);
		}
	}

	public static boolean exist(String cusName) {
		return HRMIS.exists("FROM rec_customer WHERE name='" + cusName
				+ "' AND superior IS NULL");
	}

	public static String getFullCusName(int cusId) {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare("SELECT name,superior FROM rec_customer WHERE customer_id=?");
			StringBuilder s = new StringBuilder();
			while (cusId > 0) {
				cnn.setInt(1, cusId).execute();
				cnn.next();
				cusId = cnn.getInt("superior");
				s.insert(0, "/" + cnn.getString("name"));
			}
			if (s.length() > 0) {
				s.deleteCharAt(0);
			}
			return s.toString();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void mdfName(int id, String newName) throws Exception {
		Customer cus = getInstance(id);
		cus.setName(newName);
		HConn.updateOne(cus);
	}

	public static void mdfSuperior(int id, int superior) throws Exception {
		Customer cus = getInstance(id);
		cus.setSuperior(superior);
		HConn.updateOne(cus);
	}

	public static void mdfSuperior(int id) throws Exception {
		Customer cus = getInstance(id);
		cus.setSuperior(null);
		HConn.updateOne(cus);
	}

	public static Customer getInstance(int customerId) {
		return HConn.getOne(Customer.class, customerId);
	}

	public static Customer[] getInstances() {
		List<Customer> list = HConn.getOneList("FROM Customer", Customer.class);
		Customer[] customers = new Customer[list.size()];
		list.toArray(customers);
		return customers;
	}

	public static void main(String[] args) {
		getInstances();
	}
}