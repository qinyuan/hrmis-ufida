package qinyuan.hrmis.domain.resume.source;

import qinyuan.hrmis.dao.Source;
import qinyuan.hrmis.dao.SourceDao;
import qinyuan.lib.web.html.Select;

public class SourceSelect {

	private int sourceId;

	public SourceSelect() {
		this(-1);
	}

	public SourceSelect(int sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}

	public Select toSelect() {
		Select select = new Select().setId("sourceId").select(sourceId);
		for (Source source : SourceDao.getInstances()) {
			select.addOption(source.getId(), source.getName());
		}
		return select;
	}
}
