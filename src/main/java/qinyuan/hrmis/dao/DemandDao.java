package qinyuan.hrmis.dao;

import java.sql.SQLException;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.lib.db.HConn;

public class DemandDao {

	public static Demand getInstance(int demandId) {
		return HConn.getOne(Demand.class, demandId);
	}

	public static void add(String name, int postId, int customerId,
			String duty, String qualification, String startDate,
			String endDate, String salary, int postNumber, int targetPlaceId) {
		Demand d = new Demand();

		d.setName(name);
		d.setPost(PostDao.getInstance(postId));
		d.setCustomer(CustomerDao.getInstance(customerId));
		d.setDuty(duty);
		d.setQualification(qualification);
		d.setStartDate(startDate);
		d.setEndDate(endDate);
		d.setSalary(salary);
		d.setPostNumber(postNumber);
		d.setTargetPlace(TargetPlaceDao.getInstance(targetPlaceId));
		d.setActive(true);
		d.setPause(false);

		HConn.saveOne(d);
	}

	public static void mdf(int id, String name, int postId, int customerId,
			String duty, String qualification, String startDate,
			String endDate, boolean active, String salary, int postNumber,
			int targetPlaceId, boolean pause) {
		HConn cnn = new HConn();
		Demand d = cnn.get(Demand.class, id);

		if (d != null) {
			d.setName(name);
			if (postId != d.getPostId()) {
				d.setPost(PostDao.getInstance(postId));
			}
			if (customerId != d.getCustomerId()) {
				d.setCustomer(CustomerDao.getInstance(customerId));
			}
			d.setDuty(duty);
			d.setQualification(qualification);
			d.setStartDate(startDate);
			d.setEndDate(endDate);
			d.setActive(active);
			d.setSalary(salary);
			d.setPostNumber(postNumber);
			if (targetPlaceId != d.getTargetPlaceId()) {
				d.setTargetPlace(TargetPlaceDao.getInstance(targetPlaceId));
			}
			d.setPause(pause);

			cnn.update(d);
		}
		cnn.close();
	}

	public static void mdfStatus(int demandId, boolean active, boolean pause) {
		HConn cnn = new HConn();
		Demand d = cnn.get(Demand.class, demandId);
		if (d != null) {
			d.setActive(active);
			d.setPause(pause);
			cnn.update(d);
		}
		cnn.close();
	}

	public static void del(int demandId) {
		if (!isUsed(demandId)) {
			HConn.deleteOne(Demand.class, demandId);
		}
	}

	public static boolean isUsed(int demandId) {
		return HRMIS.exists("FROM rec_recommend WHERE demand_id=" + demandId);
	}

	public static boolean isCommented(int demandId) throws SQLException {
		return HRMIS.exists("FROM rec_demand_remark WHERE demand_id="
				+ demandId);
	}
}