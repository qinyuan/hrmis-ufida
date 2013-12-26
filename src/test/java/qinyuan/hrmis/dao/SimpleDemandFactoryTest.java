package qinyuan.hrmis.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleDemandFactoryTest {

	@Test
	public void test() {
		assertTrue(new SimpleDemandFactory().getInstances().length > 0);
	}

}
