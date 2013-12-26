package qinyuan.hrmis.action;

import java.sql.SQLException;

import qinyuan.hrmis.domain.user.LoginManager;
import qinyuan.lib.web.MyAction;

public class LogoutAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws SQLException {
		getSession().invalidate();
		LoginManager lm = new LoginManager(getRequest(), getResponse());
		lm.setAutoLogin(false);
		return LOGIN;
	}
}
