package qinyuan.hrmis.action;

import java.sql.SQLException;
import qinyuan.hrmis.domain.user.LoginManager;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.IdentifyCode;
import qinyuan.lib.web.SimpleAction;

public class LoginAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws SQLException {
		LoginManager lm = new LoginManager(getRequest(), getResponse());

		if (!lm.validateIdentityCode()) {
			setResult("验证码输入错误");
			return;
		}

		String remoteAddr = getRequest().getRemoteAddr();
		if (!lm.validateUserInput()) {
			setResult("用户名或密码错误");
			IdentifyCode.addBlackList(remoteAddr);
		} else {
			lm.saveUserByInput();
			IdentifyCode.remoteBlackList(remoteAddr);
			if (hasParameter("recordChkBox")) {
				lm.setAutoLogin(true);
			}

			setResult(SUCCESS);
			setResult(((User) getSession("user")).getFirstPage());
		}
	}
}
