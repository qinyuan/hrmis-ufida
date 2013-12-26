package qinyuan.hrmis.domain.resume;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import qinyuan.hrmis.lib.db.MyConn;

public class ResumeDetailTableTest {

	@Test
	public void test() throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.execute("SELECT resume_id FROM rec_resume");
			if (cnn.next()) {
				int resumeId = cnn.getInt(1);
				assertNotEquals("", new ResumeDetailTable(resumeId).toString());
				assertNotEquals("", new ResumeModifyTable(resumeId).toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
