package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerDaoTest {

	@Test
	public void test() {
		assertTrue(CustomerDao.getInstances().length>0);
	}
}
