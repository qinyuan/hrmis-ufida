package qinyuan.lib.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import qinyuan.hrmis.lib.db.MyConn;

public class MySQLConnTest {

	@Test
	public void test() throws SQLException {
		MyConn cnn=new MyConn();
		assertNotNull(cnn);
		
		cnn.execute("SHOW DATABASES");
		assertTrue(cnn.next());
	}
}
