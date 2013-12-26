package qinyuan.hrmis.action.recruiter;

import java.sql.SQLException;
import qinyuan.hrmis.domain.recommend.Recommend;
import qinyuan.lib.web.MyAction;

public class PrevStepAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		int recommendId = getInt("recommendId");
		if (recommendId > 0) {
			try {
				Recommend.prevStep(recommendId);
			} catch (SQLException e) {
				e.printStackTrace();
				printDBError();
			}
		}
		return BLANK_AJAX_INFO;
	}
}
