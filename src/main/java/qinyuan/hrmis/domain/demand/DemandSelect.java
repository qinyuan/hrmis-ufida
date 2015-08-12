package qinyuan.hrmis.domain.demand;

import qinyuan.hrmis.dao.SimpleDemand;
import qinyuan.hrmis.dao.SimpleDemandDao;
import qinyuan.lib.web.html.Select;

public class DemandSelect {

	private int postId;
	private int demandId;

	public DemandSelect setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public DemandSelect setDemandId(int demandId) {
		this.demandId = demandId;
		return this;
	}

	@Override
	public String toString() {
		Select select = new Select().setId("demandId");

		select.addOption("-1", "（全部）");
		if (demandId > 0)
			select.select(demandId);

		SimpleDemand[] sds = postId > 0 ? SimpleDemandDao
				.getInstancesByPostId(postId) : SimpleDemandDao.getInstances();
		for (SimpleDemand sd : sds) {
			select.addOption(sd.getId(), sd.getFullName());
		}

		return select.toString();
	}

	public static void main(String[] args) {
		System.out.println(new DemandSelect());
	}
}
