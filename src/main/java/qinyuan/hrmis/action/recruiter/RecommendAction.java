package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.lib.web.MyAction;

public class RecommendAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int demandId = getInt("demandId");
		int resumeId = getInt("resumeId");
		if (demandId > 0 && resumeId > 0) {
			try {
				ResumeDao.recommend(resumeId, demandId);
			} catch (Exception e) {
				e.printStackTrace();
				printDBError();
			}
		}
		return BLANK_AJAX_INFO;
	}
}
