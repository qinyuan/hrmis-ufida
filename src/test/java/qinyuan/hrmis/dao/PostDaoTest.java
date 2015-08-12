package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class PostDaoTest {

	@Test
	public void test() {
		assertTrue(PostDao.getInstances().length>0);
	}

}
