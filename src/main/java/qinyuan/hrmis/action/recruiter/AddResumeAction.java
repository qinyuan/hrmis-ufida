package qinyuan.hrmis.action.recruiter;

import qinyuan.hrmis.dao.Gender;
import qinyuan.hrmis.dao.GenderDao;
import qinyuan.hrmis.dao.Resume;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.hrmis.domain.resume.SimpleResumeTable;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.SimpleAction;

public class AddResumeAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		addResume();
		searchTel();
		searchResumeNo();
	}

	private void searchResumeNo() {
		String searchResumeNo = getString("searchResumeNo");
		if (!empty(searchResumeNo)) {
			Resume[] rs = ResumeDao.getInstancesByResumeNo(searchResumeNo);
			if (rs.length > 0) {
				setResult(new SimpleResumeTable(rs));
			}
		}
	}

	private void searchTel() {
		String searchTel = getString("searchTel");
		if (!empty(searchTel)) {
			Resume[] rs = ResumeDao.getInstancesByTel(searchTel);
			if (rs.length > 0) {
				setResult(new SimpleResumeTable(rs));
			}
		}
	}

	private void addResume() throws Exception {
		User user = (User) getSession("user");
		int userId = user.getId();

		String postIdStr = getString("postId");
		if (!numeric(postIdStr))
			return;
		int postId = parseInt(postIdStr);

		String sourceIdStr = getString("sourceId");
		if (!numeric(sourceIdStr))
			return;
		int sourceId = parseInt(sourceIdStr);

		String experienceStr = getString("experience");
		if (!numeric(experienceStr))
			return;
		double experience = parseDouble(experienceStr);

		String applicant = getString("applicant");
		if (empty(applicant)) {
			setAttribute(RESULT, "姓名未填写");
			return;
		}

		String company = getString("company");
		String tel = getString("tel");
		String email = getString("email");
		String qq = getString("qq");
		String resumeNo = getString("resumeNo");
		String resumeLink = getString("resumeLink");
		String intention = getString("intention");
		String expectSalary = getString("expectSalary");
		String jhReason = getString("jhReason");
		String education = getString("education");
		String skill = getString("skill");
		String prevJob = getString("prevJob");
		String prevProj = getString("prevProj");
		String other = getString("other");
		String content = getString("editor");
		boolean intentionRed = false;
		if (hasParameter("intentionRed")) {
			intentionRed = true;
		}
		int targetPlaceId = getInt("targetPlaceId");
		if (targetPlaceId <= 0) {
			return;
		}

		boolean downloaded = false;
		if (hasParameter("downloaded")) {
			downloaded = true;
		}

		String genderName = getString("genderId");
		Gender gender = GenderDao.getInstance(genderName);
		int genderId = 1;
		if (gender != null) {
			genderId = gender.getId();
		}

		ResumeDao.add(userId, applicant, company, postId, tel, email, qq,
				resumeNo, resumeLink, sourceId, intention, experience,
				expectSalary, jhReason, education, skill, prevJob, prevProj,
				other, content, intentionRed, targetPlaceId, downloaded,
				genderId);
		setResult(SUCCESS);
	}
}
