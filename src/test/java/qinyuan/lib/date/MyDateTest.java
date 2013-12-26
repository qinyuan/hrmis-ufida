package qinyuan.lib.date;

import static org.junit.Assert.*;
import static qinyuan.lib.date.MyDate.*;

import org.junit.Test;

public class MyDateTest {

	@Test
	public void test() {
		assertTrue(isDate("2012-2-29"));
		assertFalse(isDate("2012-2-30"));
		
		assertEquals(30, getDayCountOfMonth(2013, 4));
		
	}
}
