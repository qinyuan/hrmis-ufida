package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.dao.Source;
import qinyuan.hrmis.dao.SourceDao;
import qinyuan.lib.web.SimpleAction;

public class ResumeSourceAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void exec() {
		setResult(getResumeSourceMapArray());
	}

	private static String getResumeSourceMapArray() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		Source[] sources = SourceDao.getInstances();
		for (Source source : sources) {
			s.append(source.toJsObject() + ",");
		}
		if (s.charAt(s.length() - 1) == ',') {
			s.deleteCharAt(s.length() - 1);
		}
		s.append("]");
		return s.toString();
	}
}
