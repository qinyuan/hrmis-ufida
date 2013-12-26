package qinyuan.hrmis.domain.resume;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.table.ResumeTable;

public class ResumeTableTest {

	@Test
	public void test() {
		assertNotEquals("", new ResumeTable().toString());
	}

}
