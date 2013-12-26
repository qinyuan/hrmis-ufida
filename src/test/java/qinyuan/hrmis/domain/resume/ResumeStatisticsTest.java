package qinyuan.hrmis.domain.resume;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import qinyuan.hrmis.domain.resume.statistics.ResumeStatistics;
import qinyuan.hrmis.domain.user.User;
import qinyuan.hrmis.domain.user.UserDao;

public class ResumeStatisticsTest {

	@Test
	public void test() throws SQLException {
		assertNotEquals("", new ResumeStatistics().toString());
		User[] users = UserDao.getUsersByPriId(6);
		if (users.length > 0) {
			assertNotEquals("",
					new ResumeStatistics().setUserId(users[0].getId())
							.toString());
		}
	}
}
