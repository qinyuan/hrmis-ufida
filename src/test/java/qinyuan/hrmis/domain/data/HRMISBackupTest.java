package qinyuan.hrmis.domain.data;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import qinyuan.hrmis.domain.data.HRMISBackup;

public class HRMISBackupTest {

	@Test
	public void test() throws Exception {
		HRMISBackup backup=new HRMISBackup();
		assertTrue(backup.export("e:/"));
	
		String fileName= backup.getFileName();
		File file=new File(fileName);
		
		assertTrue(file.exists());
		
		file.delete();
		assertFalse(file.exists());
	}

}
