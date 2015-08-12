package qinyuan.hrmis.action.de.admin;

import qinyuan.hrmis.dao.DemandDao;
import qinyuan.hrmis.domain.demand.DemandStatus;
import qinyuan.lib.date.MyDate;

public class DemandAction extends qinyuan.hrmis.action.demand.DemandAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		super.exec();
		addDemand();
		delDemand();
		mdfDemand();
		mdfDemandStatus();
	}

	private void delDemand() {
		int demandId = getInt("delDemandId");
		if (demandId > 0) {
			if (DemandDao.isUsed(demandId)) {
				setResult("该需求已经被推荐，不能删除");
			} else {
				DemandDao.del(demandId);
				setResult(SUCCESS);
			}
		}
	}

	private void mdfDemand() {
		int demandId = getInt("demandId");
		int postId = getInt("postId");
		int cusId = getInt("cusId");
		int postNumber = getInt("postNumber");
		int targetPlaceId = getInt("targetPlaceId");
		String name = getString("name");
		String duty = getString("duty");
		String qualification = getString("qualification");
		String salary = getString("salary");
		if (demandId <= 0 || postId <= 0 || cusId <= 0 || postNumber < 0
				|| targetPlaceId <= 0 || name == null || duty == null
				|| qualification == null || salary == null) {
			return;
		}

		String startDate = getString("startDate");
		if (startDate != null) {
			if (startDate.trim().equals("")) {
				startDate = null;
			} else if (!MyDate.isDate(startDate)) {
				setResult("发布日期格式错误");
				return;
			}
		}

		String endDate = getString("endDate");
		if (endDate != null) {
			if (endDate.trim().equals("")) {
				endDate = null;
			} else if (!MyDate.isDate(endDate)) {
				setResult("结束日期格式错误");
				return;
			}
		}

		String demandStatusStr = getString("demandStatus");
		if (!numeric(demandStatusStr))
			return;
		int demandStatus = parseInt(demandStatusStr);
		if (demandStatus < 0 || demandStatus > 2)
			return;
		DemandStatus ds = DemandStatus.getInstance(demandStatus);

		DemandDao.mdf(demandId, name, postId, cusId, duty, qualification,
				startDate, endDate, ds.getActive(), salary, postNumber,
				targetPlaceId, ds.getPause());
		setResult(SUCCESS);
	}

	private void mdfDemandStatus() {
		int demandId = getInt("demandId");
		if (hasParameter("changeDemandStatus") && demandId > 0) {
			boolean active = "true".equals(getString("active"));
			boolean pause = "true".equals(getString("pause"));
			DemandDao.mdfStatus(demandId, active, pause);
		}
	}

	private void addDemand() {
		int postId = getInt("n_postId");
		int cusId = getInt("n_cusId");
		int postNumber = getInt("n_postNumber");
		int targetPlaceId = getInt("n_targetPlaceId");
		String name = getString("n_name");
		String duty = getString("n_duty");
		String qualification = getString("n_qualification");
		String salary = getString("n_salary");

		if (postId <= 0 || cusId <= 0 || postNumber < 0 || targetPlaceId <= 0
				|| name == null || duty == null || qualification == null
				|| salary == null) {
			return;
		}

		String startDate = getString("n_startDate");
		if (startDate != null) {
			if (startDate.trim().equals("")) {
				startDate = null;
			} else if (!MyDate.isDate(startDate)) {
				setResult("发布日期格式错误");
				return;
			}
		}

		String endDate = getString("n_endDate");
		if (endDate != null) {
			if (endDate.trim().equals("")) {
				endDate = null;
			} else if (!MyDate.isDate(endDate)) {
				setResult("结束日期格式错误");
				return;
			}
		}

		DemandDao.add(name, postId, cusId, duty, qualification, startDate,
				endDate, salary, postNumber, targetPlaceId);
		setResult(SUCCESS);
	}
}
