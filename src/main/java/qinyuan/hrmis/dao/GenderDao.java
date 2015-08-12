package qinyuan.hrmis.dao;

import java.util.List;
import qinyuan.lib.db.HConn;

public class GenderDao {

	public static Gender[] getInstances() {
		List<Gender> list = HConn.getOneList("FROM Gender", Gender.class);
		Gender[] array = new Gender[list.size()];
		list.toArray(array);
		return array;
	}

	public static Gender getInstance(String genderName) {
		if (genderName == null)
			return null;
		genderName = genderName.replace(";", "");
		List<Gender> list = HConn.getOneList("FROM Gender WHERE name='"
				+ genderName + "'", Gender.class);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public static Gender getInstance(int genderId) {
		return HConn.getOne(Gender.class, genderId);
	}

	public static void main(String[] args) {
		Gender gender = getInstance("其他");
		System.out.println(gender.getId());
	}
}
