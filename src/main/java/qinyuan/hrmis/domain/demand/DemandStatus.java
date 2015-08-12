package qinyuan.hrmis.domain.demand;

import qinyuan.lib.web.html.Select;

public class DemandStatus {

	private boolean active;
	private boolean pause;
	private int value;

	private DemandStatus() {
	}

	public boolean getActive() {
		return active;
	}

	public boolean getPause() {
		return pause;
	}

	public int getValue() {
		return value;
	}

	public Select toSelect() {
		return toSelect("demandStatus");
	}

	public Select toSelect(String tagId) {
		Select select = new Select();
		select.setId(tagId);
		select.addOption(2, "开启");
		select.addOption(1, "暂停");
		select.addOption(0, "关闭");
		select.select(value);
		return select;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}

	public static DemandStatus getInstance(boolean active, boolean pause) {
		DemandStatus ds = new DemandStatus();
		ds.active = active;
		ds.pause = pause;
		if (active) {
			ds.value = pause ? 1 : 2;
		} else {
			ds.value = 0;
		}
		return ds;
	}

	public static DemandStatus getInstance(int value) {
		DemandStatus ds = new DemandStatus();
		ds.value = value;

		switch (value) {
		case 0:
			// 开启
			ds.active = false;
			ds.pause = false;
			break;
		case 1:
			// 暂停
			ds.active = true;
			ds.pause = true;
			break;
		case 2:
			// 关闭
			ds.active = true;
			ds.pause = false;
			break;
		default:
			throw new RuntimeException("incorrect");
		}

		return ds;
	}
}
