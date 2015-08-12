package qinyuan.hrmis.action;

import qinyuan.hrmis.domain.user.User;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.web.MyAction;

public class MdfPasswordAction extends MyAction {

	private static final long serialVersionUID = 1L;

	private String password1;
	private String password2;

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@Override
	public String execute() {
		User user = (User) getSession("user");
		if (user == null)
			return LOGIN;
		if (!password1.equals(password2)) {
			setResult("两次输入的密码不相等");
		} else if (password1.isEmpty()) {
			setResult("密码不能为空");
		} else {
			int userId = user.getId();
			try (MyConn cnn = new MyConn()) {
				cnn.prepare("UPDATE u_user SET password=? WHERE user_id=?")
						.setString(1, password1).setInt(2, userId).execute();
				setResult("密码修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				setResult(DB_ERROR);
			}
		}
		return SUCCESS;
	}
}
