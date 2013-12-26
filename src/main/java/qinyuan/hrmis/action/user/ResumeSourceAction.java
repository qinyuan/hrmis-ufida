package qinyuan.hrmis.action.user;

import java.sql.SQLException;
import qinyuan.hrmis.dao.SimpleSourceDao;
import qinyuan.hrmis.dao.SourceDao;
import qinyuan.lib.web.SimpleAction;

public class ResumeSourceAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void exec() throws SQLException {
		mdf();
		add();
		del();
	}

	private void add() {
		String name = getString("addName");
		String idPattern = getString("addIdPattern");
		String hrefPrefix = getString("addHrefPrefix");
		String hrefSuffix = getString("addHrefSuffix");
		String sign = getString("addSign");
		boolean downloaded = getBoolean("addDownloaded");
		if (!empty(name)) {
			SourceDao.add(name, idPattern, hrefPrefix, hrefSuffix, sign,
					downloaded);
		}
	}

	private void del() throws SQLException {
		int id = getInt("delId");
		if (id > 0) {
			if (SimpleSourceDao.isUsed(id)) {
				setResult("该简历来源已经被使用，不能删除");
			} else {
				SimpleSourceDao.delete(id);
			}
		}
	}

	private void mdf() {
		int id = getInt("mdfId");
		String name = getString("mdfName");
		String idPattern = getString("mdfIdPattern");
		String hrefPrefix = getString("mdfHrefPrefix");
		String hrefSuffix = getString("mdfHrefSuffix");
		String sign = getString("mdfSign");
		boolean downloaded = getBoolean("mdfDownloaded");
		if (id > 0) {
			SourceDao.mdf(id, name, idPattern, hrefPrefix, hrefSuffix, sign,
					downloaded);
		}
	}
}
