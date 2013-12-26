package qinyuan.hrmis.domain.user;

import java.sql.SQLException;

import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.hrmis.lib.db.MyConn;

public class UserDao {

	public static User getUser(String name, String password)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			String query = "SELECT user_id,name,password FROM u_user "
					+ "WHERE name=? AND password=?";
			cnn.prepare(query).setString(1, name).setString(2, password)
					.execute();
			return cnn.next() ? getUserByConn(cnn) : null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static User getUser(int userId) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			String query = "SELECT * FROM u_user WHERE user_id=" + userId;
			cnn.execute(query);
			return cnn.next() ? getUserByConn(cnn) : null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static User[] getUsersByPriId(int priId) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			String query = "SELECT * FROM u_user WHERE user_id IN "
					+ "(SELECT user_id FROM u_privilege WHERE pri_id=?)";
			cnn.prepare(query).setInt(1, priId).execute();
			User[] users = new User[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				users[i++] = getUserByConn(cnn);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static User getUserByConn(MyConn cnn) throws SQLException {
		User user = new User();
		int userId = cnn.getInt("user_id");
		user.setId(userId);
		user.setName(cnn.getString("name"));
		user.setPassword(cnn.getString("password"));
		user.setPriIds(Privilege.getPrisByUserId(user.getId()));
		user.setSubUserIds(SimpleUserDao.getSubUserIds(userId));
		return user;
	}
}
