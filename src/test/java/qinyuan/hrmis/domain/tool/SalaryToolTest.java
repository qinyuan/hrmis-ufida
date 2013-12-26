package qinyuan.hrmis.domain.tool;

import static org.junit.Assert.*;

import org.junit.Test;

public class SalaryToolTest {

	@Test
	public void test() {
		SalaryTool s=new SalaryTool();
		assertTrue(s.getEndowmentRate()>0);
		assertTrue(s.getFundsRate()>0);
		assertTrue(s.getIdlenessRate()>0);
		assertTrue(s.getMedicareRate()>0);
		assertTrue(s.getFundsBase()>0);
		assertTrue(s.getSecurityBase()>0);
	}

}
