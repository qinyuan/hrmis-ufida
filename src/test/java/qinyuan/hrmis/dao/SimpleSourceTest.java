package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleSourceTest {

	@Test
	public void test() {
		assertTrue(SimpleSourceDao.getInstances().length > 0);
	}

}
