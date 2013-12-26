package qinyuan.hrmis.domain.resume.source;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.table.SourceMapTable;

public class SourceMapTableTest {

	@Test
	public void test() {
		assertTrue(new SourceMapTable().toString().length() > 0);
	}

}
