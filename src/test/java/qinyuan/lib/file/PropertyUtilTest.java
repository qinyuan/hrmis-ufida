package qinyuan.lib.file;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class PropertyUtilTest {

	@Test
	public void test() {
		Map<String, String> map = PropertyUtil.parse("data");
		assertTrue(map.size() > 0);
	}

}
