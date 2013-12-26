package qinyuan.hrmis.action.user;

import qinyuan.hrmis.domain.resume.source.SourceMapDao;
import qinyuan.lib.web.SimpleAction;

public class ResumeSourceMapAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void exec() throws Exception {
		int delId = getInt("delId");
		if (delId > 0) {
			SourceMapDao.delete(delId);
		}

		int addSourceId = getInt("addSourceId");
		String addSourceSelector = getString("addSourceSelector");
		String addTargetSelector = getString("addTargetSelector");
		String addRemark = getString("addRemark");
		if (addSourceId > 0 && addSourceSelector != null
				&& addTargetSelector != null) {
			SourceMapDao.add(addSourceId, addSourceSelector, addTargetSelector,
					addRemark);
		}

		int mdfId = getInt("mdfId");
		int mdfSourceId = getInt("mdfSourceId");
		String mdfSourceSelector = getString("mdfSourceSelector");
		String mdfTargetSelector = getString("mdfTargetSelector");
		String mdfRemark = getString("mdfRemark");
		if (mdfId > 0 && mdfSourceId > 0 && mdfSourceSelector != null
				&& mdfTargetSelector != null) {
			SourceMapDao.edit(mdfId, mdfSourceId, mdfSourceSelector,
					mdfTargetSelector, mdfRemark);
		}
	}
}