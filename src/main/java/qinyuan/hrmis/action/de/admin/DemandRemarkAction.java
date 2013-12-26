package qinyuan.hrmis.action.de.admin;

import java.sql.SQLException;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.MyAction;
import static qinyuan.hrmis.domain.remark.DemandRemark.*;

public class DemandRemarkAction extends MyAction {

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
		int demandId = getInt("addDemandId");
		String content = getString("addContent");
		if (demandId > 0 && content != null) {
			add(demandId, user.getId(), content);
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
