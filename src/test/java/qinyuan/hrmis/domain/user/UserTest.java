package qinyuan.hrmis.domain.user;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import qinyuan.hrmis.domain.user.User;

public class UserTest {

	@Test
	public void test() throws SQLException {
		User user = UserDao.getUser("qinyuan", "111");
		assertNull(user);
		user = UserDao.getUser("覃源", "test");
		assertNotNull(user);

		assertFalse(user.getNavi().trim().isEmpty());
		assertFalse(user.getNaviDetails().trim().isEmpty());
	}
}
