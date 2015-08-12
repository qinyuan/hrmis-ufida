package qinyuan.lib.web;

import java.sql.SQLException;

public abstract class SimpleAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	final public String execute() {
		try {
			exec();
		} catch (SQLException e) {
			e.printStackTrace();
			setResult("数据库操作失败");
		} catch (Exception e2) {
			e2.printStackTrace();
			setResult("未知错误");
		}
		return BLANK_AJAX_INFO;
	}

	protected abstract void exec() throws Exception;
}
