package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class SourceDaoTest {

	@Test
	public void test() {
		assertTrue(SourceDao.getInstances().length > 0);
		assertEquals(SourceDao.getInstance(0), null);
	}

}
