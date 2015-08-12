package qinyuan.hrmis.domain.post;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.table.PostTable;

public class PostTableTest {

	@Test
	public void test() {
		PostTable pt = new PostTable();
		assertTrue(pt.toString().length() > 0);
		pt.setEditable(true);
		assertTrue(pt.toString().length() > 0);
	}

}
