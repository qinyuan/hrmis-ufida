package qinyuan.lib.file;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipTool {

	private ZipTool() {
	}

	public static File zip(String srcPathName) {
		int lastDotIndex = srcPathName.lastIndexOf('.');
		String zipFilePath;
		if (lastDotIndex > 1) {
			zipFilePath = srcPathName.substring(0, lastDotIndex) + ".rar";
		} else {
			zipFilePath = srcPathName + ".rar";
		}
		return zip(srcPathName, zipFilePath);
	}

	public static File zip(String srcPathName, String zipFilePath) {
		File file = new File(srcPathName);
		if (!file.exists())
			throw new RuntimeException("source file or directory "
					+ srcPathName + " does not exist.");

		Project proj = new Project();
		FileSet fileSet = new FileSet();
		fileSet.setProject(proj);

		if (file.isDirectory()) {
			fileSet.setDir(file);
		} else {
			fileSet.setFile(file);
		}

		Zip zip = new Zip();
		zip.setProject(proj);
		zip.setDestFile(new File(zipFilePath));
		zip.addFileset(fileSet);
		zip.setEncoding("UTF-8");
		zip.execute();
		return new File(zipFilePath);
	}

	public static void unzip(String zipFilepath, String destDir)
			throws Exception {
		if (!new File(zipFilepath).exists())
			throw new RuntimeException("zip file " + zipFilepath
					+ " does not exist.");

		Project proj = new Project();
		Expand expand = new Expand();
		expand.setProject(proj);
		expand.setTaskType("unzip");
		expand.setTaskName("unzip");
		expand.setEncoding("UTF-8");

		expand.setSrc(new File(zipFilepath));
		expand.setDest(new File(destDir));
		expand.execute();
	}

	public static void main(String[] args) throws Exception {
		zip("e:/test", "e:/test.rar");
	}
}
