package qinyuan.hrmis.action.de.admin;

import java.sql.SQLException;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.MyAction;
import static qinyuan.hrmis.domain.remark.CustomerRemark.*;

public class CustomerRemarkAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		try {
			addRemark();
			delRemark();
			mdfRemark();
		} catch (SQLException e) {
			printDBError();
		}
		return BLANK_AJAX_INFO;
	}

	private void addRemark() throws SQLException {
		User user = (User) getSession("user");
		if (user == null)
			return;
		int cusId = getInt("addCusId");
		String content = getString("addContent");
		if (cusId > 0 && content != null) {
			add(cusId, user.getId(), content);
		}
	}

	private void delRemark() throws SQLException {
		int id = getInt("delId");
		if (id > 0) {
			del(id);
		}
	}

	private void mdfRemark() throws SQLException {
		int id = getInt("mdfId");
		String content = getString("mdfContent");
		if (id > 0 && content != null) {
			mdf(id, content);
		}
	}
}
