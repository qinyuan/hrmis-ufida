package qinyuan.hrmis.action.recruiter;

import java.sql.SQLException;
import qinyuan.hrmis.domain.recommend.Recommend;
import qinyuan.lib.web.MyAction;

public class MdfFeedbackAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int recommendId = getInt("recommendId");
		String feedback = getString("feedback");
		if (recommendId > 0 && feedback != null) {
			try {
				Recommend.mdfFeedback(recommendId, feedback);
			} catch (SQLException e) {
				e.printStackTrace();
				printDBError();
			}
		}
		return BLANK_AJAX_INFO;
	}
}
