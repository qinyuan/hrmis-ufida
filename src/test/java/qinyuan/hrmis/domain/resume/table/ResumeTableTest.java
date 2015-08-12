package qinyuan.hrmis.domain.resume.table;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import qinyuan.hrmis.dao.SimpleUserDao;
import qinyuan.hrmis.domain.table.ResumeTable;
import qinyuan.hrmis.domain.user.User;
import qinyuan.hrmis.domain.user.UserDao;

public class ResumeTableTest {

	@Test
	public void test() {
		ResumeTable rt = new ResumeTable();
		assertTrue(rt.toString().indexOf("table") > 0);
		User user;
		try {
			user = UserDao.getUser(SimpleUserDao.getInstances()[0].getId());
			assertTrue(rt.setUser(user).toString().indexOf("table") > 0);
		} catch (SQLException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}

}
