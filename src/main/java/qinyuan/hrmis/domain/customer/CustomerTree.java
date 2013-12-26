package qinyuan.hrmis.domain.customer;

import java.sql.SQLException;

import qinyuan.hrmis.dao.CustomerDao;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.web.html.ImageUtil;
import qinyuan.lib.web.html.Tree;

public class CustomerTree {

	private boolean displayRemark = true;

	public CustomerTree setDisplayRemark(boolean bool) {
		displayRemark = bool;
		return this;
	}

	@Override
	public String toString() {
		Tree tree = new Tree();
		try (MyConn cnn = new MyConn()) {
			// get the independent customers
			cnn.execute("SELECT * FROM rec_customer WHERE superior IS NULL");
			InnerCustomer[] indeCustomers = getCustomersByConn(cnn);

			cnn.prepare("SELECT * FROM rec_customer WHERE superior=?");
			for (InnerCustomer cus : indeCustomers) {
				addCustomer(tree, cus, cnn, displayRemark);
			}
			return tree.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "数据库查询失败";
		}
	}

	private static void addCustomer(Tree tree, InnerCustomer customer,
			MyConn cnn, boolean displayRemark) throws SQLException {
		InnerCustomer[] subCustomers = getSubCustomer(customer.id, cnn);
		String id = "cus" + customer.id;
		String text = customer.name;
		if (CustomerDao.isCommented(customer.id) && displayRemark) {
			text += ImageUtil.getCommentImage("cusRemark" + customer.id);
		}
		if (subCustomers.length == 0) {
			tree.add(id, text);
		} else {
			Tree subTree = new Tree();
			subTree.setId(id).setText(text);
			for (InnerCustomer subCustomer : subCustomers) {
				addCustomer(subTree, subCustomer, cnn, displayRemark);
			}
			tree.add(subTree);
		}
	}

	private static InnerCustomer[] getSubCustomer(int cusId, MyConn cnn)
			throws SQLException {
		cnn.setInt(1, cusId).execute();
		InnerCustomer[] customers = new InnerCustomer[cnn.getRowCount()];
		for (int i = 0; i < customers.length; i++) {
			cnn.next();
			InnerCustomer customer = new InnerCustomer();
			customer.id = cnn.getInt("customer_id");
			customer.name = cnn.getString("name");
			customers[i] = customer;
		}
		return customers;
	}

	private static InnerCustomer[] getCustomersByConn(MyConn cnn)
			throws SQLException {
		InnerCustomer[] customers = new InnerCustomer[cnn.getRowCount()];
		int i = 0;
		while (cnn.next()) {
			InnerCustomer customer = new InnerCustomer();
			customer.id = cnn.getInt("customer_id");
			customer.name = cnn.getString("name");
			customers[i++] = customer;
		}
		return customers;
	}

	private static class InnerCustomer {
		int id;
		String name;
	}
}
