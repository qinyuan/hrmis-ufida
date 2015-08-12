package qinyuan.hrmis.action.recruiter;

import java.sql.SQLException;
import qinyuan.hrmis.domain.recommend.Recommend;
import qinyuan.lib.web.MyAction;

public class NextStepAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int recommendId = getInt("recommendId");
		if (recommendId > 0) {
			try {
				Recommend.nextStep(recommendId);
			} catch (SQLException e) {
				e.printStackTrace();
				printDBError();
			}
		}
		return BLANK_AJAX_INFO;
	}
}
