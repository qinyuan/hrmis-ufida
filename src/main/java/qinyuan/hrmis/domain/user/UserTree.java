package qinyuan.hrmis.domain.user;

import java.sql.SQLException;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.web.html.Tree;

public class UserTree {

	@Override
	public String toString() {
		Tree tree = new Tree();
		try (MyConn cnn = new MyConn()) {
			// get the independent customers
			cnn.execute("SELECT * FROM u_user WHERE superior IS NULL");
			InnerUser[] indeCustomers = getUsersByConn(cnn);

			cnn.prepare("SELECT * FROM u_user WHERE superior=?");
			for (InnerUser cus : indeCustomers) {
				addUserToTree(tree, cus, cnn);
			}
			return tree.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "数据库查询失败";
		}
	}

	private static void addUserToTree(Tree tree, InnerUser user, MyConn cnn)
			throws SQLException {
		InnerUser[] subUsers = getSubUsers(user.id, cnn);
		String id = "user" + user.id;
		String text = user.name;
		if (subUsers.length == 0) {
			tree.add(id, text);
		} else {
			Tree subTree = new Tree();
			subTree.setId(id).setText(text);
			for (InnerUser subUser : subUsers) {
				addUserToTree(subTree, subUser, cnn);
			}
			tree.add(subTree);
		}
	}

	private static InnerUser[] getSubUsers(int cusId, MyConn cnn)
			throws SQLException {
		cnn.setInt(1, cusId).execute();
		return getUsersByConn(cnn);
	}

	private static InnerUser[] getUsersByConn(MyConn cnn) throws SQLException {
		InnerUser[] users = new InnerUser[cnn.getRowCount()];
		for (int i = 0; cnn.next(); i++) {
			InnerUser user = new InnerUser();
			user.id = cnn.getInt("user_id");
			user.name = cnn.getString("name");
			users[i] = user;
		}
		return users;
	}

	private static class InnerUser {
		int id;
		String name;
	}
}
