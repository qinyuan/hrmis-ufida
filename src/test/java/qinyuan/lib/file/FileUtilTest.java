package qinyuan.lib.file;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void test() {
		String classesPath=FileUtil.getClassesPath();
		assertTrue(classesPath.indexOf("hrmis")>0);
	}

}
