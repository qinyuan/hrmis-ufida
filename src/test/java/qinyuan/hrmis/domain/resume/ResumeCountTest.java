package qinyuan.hrmis.domain.resume;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResumeCountTest {

	@Test
	public void test() {
		try {
			ResumeCount rc = new ResumeCount();
			rc.getCount();
			rc.setUserId(1);
			rc.getCount();
			rc.setStartDate("2012-1-1");
			rc.getCount();
			rc.setStatusId(0);
			rc.getCount();
			rc.setStatusId(1);
			rc.getCount();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
