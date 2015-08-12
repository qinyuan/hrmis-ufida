package qinyuan.hrmis.domain.gender;

import qinyuan.hrmis.dao.Gender;
import qinyuan.hrmis.dao.GenderDao;
import qinyuan.lib.web.html.Select;

public class GenderSelect {

	private int genderId;
	private String tagId = "genderId";

	public GenderSelect setGenderId(int genderId) {
		this.genderId = genderId;
		return this;
	}

	public GenderSelect setTagId(String tagId) {
		this.tagId = tagId;
		return this;
	}

	public Select toSelect() {
		Select select = new Select();

		if (tagId != null) {
			select.setId(tagId);
		}
		if (genderId > 0) {
			select.select(genderId);
		}

		for (Gender gender : GenderDao.getInstances()) {
			select.addOption(gender.getId(), gender.getName());
		}

		return select;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}
}
