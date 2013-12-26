package qinyuan.hrmis.domain.resume.source;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.table.SourceTable;

public class SourceTableTest {

	@Test
	public void test() {
		assertTrue(new SourceTable().toString().length()>0);
	}

}
