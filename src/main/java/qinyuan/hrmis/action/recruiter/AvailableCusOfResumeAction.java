package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.domain.demand.DemandListOfResume;
import qinyuan.lib.web.SimpleAction;

public class AvailableCusOfResumeAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void exec() {
		int resumeId = getInt("resumeId");
		if (resumeId > 0) {
			setResult("可推送需求：<br />");
			setResult(new DemandListOfResume(resumeId));
		}
	}
}
