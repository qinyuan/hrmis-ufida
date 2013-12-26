package qinyuan.hrmis.domain.resume.table;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.table.ResumeTable;

public class ResumeTableTest {

	@Test
	public void test() {
		assertTrue(new ResumeTable().toString().length()>0);
	}

}
