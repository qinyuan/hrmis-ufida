package qinyuan.hrmis.dao;

import static org.junit.Assert.*;
import static qinyuan.hrmis.dao.ResumeDao.*;
import org.junit.Test;

public class ResumeDaoTest {

	@Test
	public void test() {
		try {
			assertTrue(getApplicantById(0) == null);
			assertTrue(getContentById(0) == null);
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}
	}

}
