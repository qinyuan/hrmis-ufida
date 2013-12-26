package qinyuan.hrmis.action.user;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import qinyuan.hrmis.dao.SimpleUser;
import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.hrmis.domain.user.Privilege;
import qinyuan.lib.web.SimpleAction;

public class PriPanelAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	private static Set<Integer> getPriSet(int userId) throws SQLException {
		Set<Integer> set = new HashSet<Integer>();
		for (int pri : Privilege.getPrisByUserId(userId)) {
			set.add(pri);
		}
		return set;
	}

	@Override
	public void exec() throws SQLException {
		int userId = getInt("userId");
		if (userId <= 0)
			return;

		SimpleUser su = SimpleUserDao.getInstance(userId);
		setResult("<h4>" + su.getName() + "详细信息：</h4>");
		setResult("<p>密码：" + su.getPassword() + "</p>");
		setResult("<p>权限：<br />");
		echoPrivileges(userId);
		setResult("</p>");
	}

	private void echoPrivileges(int userId) throws SQLException {
		Set<Integer> priSet = getPriSet(userId);
		final int colCount = 4;
		int colNum = 0;
		for (Entry<Integer, Privilege> entry : Privilege.priMap.entrySet()) {
			if ((++colNum) % colCount == 0) {
				setResult("<br />");
			}
			Privilege pri = entry.getValue();
			Integer priId = entry.getKey();
			setResult("<span><input type='checkbox' id='pri" + priId + "'");
			if (priSet.contains(priId)) {
				setResult("checked='checked'");
			}
			setResult(" />" + pri.getTitle() + " </span>");
		}
	}
}
