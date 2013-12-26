package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;

import qinyuan.lib.web.html.Select;

public class StatusSelect {

	private String id;
	private int statusId;

	public StatusSelect(String id) {
		this.id = id;
	}

	public StatusSelect setStatusId(int statusId) {
		this.statusId = statusId;
		return this;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}

	public Select toSelect() {
		Select select = new Select().setId(id);
		select.addOption(0, "已录入");
		try {
			for (int i = 1; i <= Status.size(); i++) {
				select.addOption(i, Status.get(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (statusId > 0) {
			select.select(statusId);
		}
		return select;
	}

	public static void main(String[] args) {
		System.out.println(new StatusSelect("id"));
	}
}
