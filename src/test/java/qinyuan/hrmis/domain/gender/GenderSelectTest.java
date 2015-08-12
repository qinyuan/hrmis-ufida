package qinyuan.hrmis.domain.gender;

import static org.junit.Assert.*;

import org.junit.Test;

public class GenderSelectTest {

	@Test
	public void test() {
		try {
			new GenderSelect().toSelect();
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
