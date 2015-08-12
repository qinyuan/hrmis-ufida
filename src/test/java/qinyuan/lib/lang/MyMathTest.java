package qinyuan.lib.lang;

import static org.junit.Assert.*;
import static qinyuan.lib.lang.MyMath.*;

import org.junit.Test;


public class MyMathTest {

	@Test
	public void test() {
		assertTrue(isNumeric("1"));
		assertTrue(isNumeric("12345"));
		assertTrue(isNumeric("1.2345"));
		assertTrue(isNumeric("0.1234"));
		assertFalse(isNumeric("0.0.0"));
		assertFalse(isNumeric(".1343"));
		assertFalse(isNumeric("aa"));

		assertEquals("1.24", round(1.245, 2));
		assertEquals("1.24", round(1.235, 2));
		assertEquals("1", round(1.245, 0));
		assertEquals("1", round(1.245, -1));
	}

}
