package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleUserDaoTest {

	@Test
	public void test() {
		assertTrue(SimpleUserDao.getInstances().length>0);
	}

}
