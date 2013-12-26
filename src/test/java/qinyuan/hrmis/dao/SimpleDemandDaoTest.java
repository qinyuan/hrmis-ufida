package qinyuan.hrmis.dao;

import static org.junit.Assert.*;
import static qinyuan.hrmis.dao.SimpleDemandDao.*;

import org.junit.Test;

public class SimpleDemandDaoTest {

	@Test
	public void test() {
		assertTrue(getInstances().length>0);
		getInstance(1);
		getInstancesByCusId(1);
		getInstancesByPostId(2);
	}

}
