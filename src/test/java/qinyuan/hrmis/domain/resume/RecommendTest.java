package qinyuan.hrmis.domain.resume;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import qinyuan.hrmis.domain.recommend.Recommend;

public class RecommendTest {

	@Test
	public void test() throws SQLException {
		assertTrue(Recommend.getInstancesByResumeId(3).length>=0);
	}

}
