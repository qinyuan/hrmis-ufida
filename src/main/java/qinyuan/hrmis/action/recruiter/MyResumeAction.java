package qinyuan.hrmis.action.recruiter;

import java.sql.SQLException;
import qinyuan.hrmis.dao.ResumeDao;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.web.SimpleAction;

public class MyResumeAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void exec() {
		delete();
		modify();
		modifyContent();
		modifyRecStepDealTime();
	}

	private void modifyRecStepDealTime() {
		int mdfRecStepId = getInt("mdfRecStepId");
		String mdfRecStepDealTime = getString("mdfRecStepDealTime");
		if (mdfRecStepId > 0 && MyDateTime.isDateTime(mdfRecStepDealTime)) {
			try {
				HRMIS.execute("UPDATE rec_rec_step SET deal_time='"
						+ mdfRecStepDealTime + "' WHERE rec_step_id="
						+ mdfRecStepId);
			} catch (SQLException e) {
				printDBError();
				e.printStackTrace();
			}
		}
	}

	private void modifyContent() {
		int resumeContentId = getInt("resumeContentId");
		String content = getString("editor");
		if (resumeContentId > 0 && content != null) {
			try {
				ResumeDao.mdfContent(resumeContentId, content);
			} catch (Exception e) {
				printDBError();
				e.printStackTrace();
			}
		}
	}

	private void modify() {
		int resumeId = getInt("m_resumeId");
		if (resumeId <= 0)
			return;

		String applicant = getString("m_applicant");
		String company = getString("m_company");

		int postId = getInt("m_postId");
		if (postId <= 0)
			return;

		String tel = getString("m_tel");
		String email = getString("m_email");
		String qq = getString("m_qq");

		int sourceId = getInt("m_sourceId");
		if (sourceId <= 0)
			return;

		String resumeNo = getString("m_resumeNo");
		String resumeLink = getString("m_resumeLink");

		String experienceStr = getString("m_experience");
		Double experience = null;
		if (numeric(experienceStr)) {
			experience = Double.valueOf(experienceStr);
		}

		String expectSalary = getString("m_expectSalary");
		String intention = getString("m_intention");

		boolean intentionRed = false;
		if (hasParameter("m_intentionRed")) {
			intentionRed = true;
		}

		String jhReason = getString("m_jhReason");
		String education = getString("m_education");
		String skill = getString("m_skill");
		String prevJob = getString("m_prevJob");
		String prevProj = getString("m_prevProj");
		String other = getString("m_other");
		int targetPlaceId = getInt("m_targetPlaceId");
		if (targetPlaceId <= 0) {
			return;
		}

		boolean downloaded = false;
		if (hasParameter("m_downloaded")) {
			downloaded = true;
		}

		int genderId = getInt("m_genderId");
		if (genderId <= 0) {
			return;
		}

		try {
			ResumeDao.mdf(resumeId,/* addTime, */applicant, company, postId,
					tel, email, qq, resumeNo, resumeLink, sourceId, intention,
					experience, expectSalary, jhReason, education, skill,
					prevJob, prevProj, other, intentionRed, targetPlaceId,
					downloaded, genderId);
		} catch (Exception e) {
			e.printStackTrace();
			printDBError();
		}
	}

	private void delete() {
		int resumeId = getInt("delId");
		if (resumeId > 0) {
			try {
				if (ResumeDao.isRecommended(resumeId)) {
					setResult("已经被推荐的简历不能删除!");
				} else {
					ResumeDao.del(resumeId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				printDBError();
			}
		}
	}
}
