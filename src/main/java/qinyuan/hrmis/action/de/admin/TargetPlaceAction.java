package qinyuan.hrmis.action.de.admin;

import qinyuan.hrmis.dao.TargetPlaceDao;
import qinyuan.lib.web.SimpleAction;

public class TargetPlaceAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		String newTargetPlaceName = getString("newTargetPlaceName");
		if (newTargetPlaceName != null) {
			TargetPlaceDao.add(newTargetPlaceName);
		}

		int delId = getInt("delId");
		if (delId > 0) {
			TargetPlaceDao.delete(delId);
		}

		int mdfId = getInt("mdfId");
		String mdfName = getString("mdfName");
		if (mdfId > 0 && mdfName != null) {
			TargetPlaceDao.modify(mdfId, mdfName);
		}
	}
}
