package qinyuan.hrmis.action.user;

import java.sql.SQLException;

import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.lib.web.SimpleAction;

public class UserAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		addUser();
		delUser();
		mdfUser();
		updateSuperior();
	}

	private void delUser() throws SQLException {
		int delId = getInt("delId");
		if (delId > 0) {
			if (SimpleUserDao.isUsed(delId)) {
				setResult("该用户已经被某些表使用，不能删除！");
			} else {
				SimpleUserDao.del(delId);
			}
		}
	}

	private void mdfUser() {
		int mdfId = getInt("mdfId");
		String mdfName = getString("mdfName");
		String mdfPassword = getString("mdfPassword");
		if (mdfId > 0 && mdfName != null && mdfPassword != null) {
			SimpleUserDao.mdf(mdfId, mdfName, mdfPassword);
		}
	}

	private void updateSuperior() {
		int subUserId = getInt("subUserId");
		if (subUserId <= 0) {
			return;
		}
		int supUserId = getInt("supUserId");
		SimpleUserDao.mdfSuperior(subUserId, supUserId);
	}

	private void addUser() throws Exception {
		String username = getString("username");
		String password = getString("password");
		if (username == null || password == null)
			return;

		if (username.trim().isEmpty()) {
			setResult("用户名不能为空");
			return;
		}

		if (SimpleUserDao.exists(username)) {
			setResult("该用记已经存在");
		} else {
			SimpleUserDao.add(username, password);
			setResult("用户添加成功");
		}
	}
}
