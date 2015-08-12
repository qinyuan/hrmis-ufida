package qinyuan.hrmis.action.user;

import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.lib.web.SimpleAction;

public class ListAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		int delId = getInt("delId");
		if (delId > 0) {
			if (SimpleUserDao.isUsed(delId)) {
				setResult("该用户已经被某些表使用，不能删除！");
			} else {
				SimpleUserDao.del(delId);
			}
		}

		int mdfId = getInt("mdfId");
		String mdfName = getString("mdfName");
		String mdfPassword = getString("mdfPassword");
		if (mdfId > 0 && mdfName != null && mdfPassword != null) {
			SimpleUserDao.mdf(mdfId, mdfName, mdfPassword);
		}
	}
}
