package qinyuan.hrmis.domain.demand.table;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.table.DemandTable;

public class DemandTableTest {

	@Test
	public void test() {
		assertTrue(new DemandTable().toString().length() > 0);
	}
}
