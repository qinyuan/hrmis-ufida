package qinyuan.hrmis.domain.targetplace;

import qinyuan.hrmis.dao.TargetPlace;
import qinyuan.hrmis.dao.TargetPlaceDao;
import qinyuan.lib.web.html.Select;

public class TargetPlaceSelect {

	private int defaultId;
	private String tagId = "targetPlaceId";
	private boolean withTotal;

	public TargetPlaceSelect setDefaultId(int defaultId) {
		this.defaultId = defaultId;
		return this;
	}

	public TargetPlaceSelect setTagId(String tagId) {
		this.tagId = tagId;
		return this;
	}

	public TargetPlaceSelect setWithTotal(boolean withTotal) {
		this.withTotal = withTotal;
		return this;
	}

	public Select toSelect() {
		Select select = new Select();
		if (withTotal) {
			select.addOption(0, "（全部）");
		}
		for (TargetPlace tp : TargetPlaceDao.getInstances()) {
			select.addOption(tp.getId(), tp.getName());
		}
		if (defaultId > 0) {
			select.select(defaultId);
		}
		if (tagId != null) {
			select.setId(tagId);
		}
		return select;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}
}
