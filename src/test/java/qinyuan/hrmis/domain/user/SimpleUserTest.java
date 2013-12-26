package qinyuan.hrmis.domain.user;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.dao.SimpleUser;
import qinyuan.hrmis.dao.SimpleUserDao;

public class SimpleUserTest {

	@Test
	public void test() {
		SimpleUser[] users = SimpleUserDao.getInstances();
		assertTrue(users.length > 0);
	}

}
