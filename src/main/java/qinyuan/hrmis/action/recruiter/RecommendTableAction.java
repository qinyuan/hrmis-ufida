package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.domain.recommend.RecommendEditTable;
import qinyuan.lib.web.MyAction;

public class RecommendTableAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int resumeId = getInt("resumeId");
		if (resumeId > 0) {
			try {
				setResult(new RecommendEditTable(resumeId));
			} catch (Exception e) {
				e.printStackTrace();
				printDBError();
			}
		}
		return BLANK_AJAX_INFO;
	}
}
