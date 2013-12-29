package qinyuan.hrmis.domain.data;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import qinyuan.hrmis.HrmisConfig;
import qinyuan.hrmis.domain.data.HRMISBackup;

public class HRMISBackupTest {

	@Test
	public void test() throws Exception {
		HRMISBackup backup = new HRMISBackup();
		String fileName = backup.export(HrmisConfig.getDataFolderName());

		File file = new File(fileName);

		assertTrue(file.exists());

		file.delete();
		assertFalse(file.exists());
	}

}
