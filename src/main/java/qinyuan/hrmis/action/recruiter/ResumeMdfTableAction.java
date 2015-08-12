package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.hrmis.domain.resume.ResumeModifyTable;
import qinyuan.lib.web.SimpleAction;

public class ResumeMdfTableAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void exec() throws Exception {
		int resumeId = getInt("resumeId");
		if (resumeId > 0) {
			setResult(new ResumeModifyTable(resumeId));
		}
		int resumeContentId = getInt("resumeContentId");
		if (resumeContentId > 0) {
			setResult(ResumeDao.getContentById(resumeContentId));
		}
	}
}
