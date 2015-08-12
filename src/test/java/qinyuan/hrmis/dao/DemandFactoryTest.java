package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class DemandFactoryTest {

	@Test
	public void test() {
		DemandFactory df=new DemandFactory();
		df.setPostId(1).setCustomerId(1).setTargetPlaceId(1).setActive(true);
		assertTrue(df.getInstances().length>=0);
	}

}
