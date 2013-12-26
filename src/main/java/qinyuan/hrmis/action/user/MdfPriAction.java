package qinyuan.hrmis.action.user;

import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.web.MyAction;

public class MdfPriAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int priId = getInt("priId");
		int userId = getInt("userId");
		if (priId <= 0 || userId <= 0) {
			return BLANK_AJAX_INFO;
		}

		String status = getString("status");
		if (status.equals("true")) {
			try (MyConn cnn = new MyConn()) {
				cnn.prepare(
						"INSERT INTO u_privilege(user_id,pri_id) VALUES(?,?)")
						.setInt(1, userId).setInt(2, priId).execute();
				setResult("已保存更改");
			} catch (Exception e) {
				e.printStackTrace();
				setResult("数据库操作失败，未保存更改");
			}
		} else if (status.equals("false")) {
			try (MyConn cnn = new MyConn()) {
				cnn.prepare(
						"DELETE FROM u_privilege WHERE user_id=? AND pri_id=?")
						.setInt(1, userId).setInt(2, priId).execute();
				setResult("已保存更改");
			} catch (Exception e) {
				e.printStackTrace();
				setResult("数据库操作失败，未保存更改");
			}
		}
		return BLANK_AJAX_INFO;
	}
}
