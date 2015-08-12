package qinyuan.hrmis;

import static org.junit.Assert.*;

import org.junit.Test;

public class HrmisConfigTest {

	@Test
	public void test() {
		assertTrue(HrmisConfig.getDataFolderName()!=null);
	}
}
