package qinyuan.lib.file;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import org.apache.commons.io.FileUtils;

public class FileUtil {

	private FileUtil() {
	}

	private static String createTimeMarkFileName(File templateFile) {
		String path = templateFile.getParent();
		String name = templateFile.getName();
		name = name.replaceAll(".*\\.", System.currentTimeMillis() + ".");
		return path + "\\" + name;
	}

	public static File createTimeMarkFile(File templateFile) throws IOException {
		if (!templateFile.isFile()) {
			throw new RuntimeException("invalid file "
					+ templateFile.getAbsolutePath());
		}
		File file = new File(createTimeMarkFileName(templateFile));
		FileUtils.copyFile(templateFile, file);
		return file;
	}

	public static File createTimeMarkFile(String templateFileName)
			throws IOException {
		return createTimeMarkFile(new File(templateFileName));
	}

	public static void append(String fileName, String strToAppend)
			throws IOException {
		write(fileName, strToAppend, true);
	}

	public static void delete(String fileName) {
		delete(new File(fileName));
	}

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for (File f : subFiles) {
				delete(f);
			}
			file.delete();
		} else {
			throw new RuntimeException("invalid file" + file.getPath());
		}
	}

	public static String readAll(String fileName) throws IOException {
		StringBuilder o = new StringBuilder();
		File file = getFile(fileName);
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr)) {
			String str;
			while ((str = br.readLine()) != null) {
				o.append(str + "\r\n");
			}
			return o.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("error to read file" + fileName
					+ " in class MyFileIO");
		}
	}

	public static String readAllAsOneLine(String fileName) throws IOException {
		String str = readAll(fileName);
		return str.replace("\r\n", "");
	}

	public static String[] readAsStrArr(String fileName) throws IOException {
		String str = readAll(fileName);
		return str.split("\r\n");
	}

	public static void show(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void show(String fileName) {
		show(new File(fileName));
	}

	public static void write(String fileName, String strToWrite)
			throws IOException {
		write(fileName, strToWrite, false);
	}

	private static void write(String fileName, String strToWrite, boolean append)
			throws IOException {
		File file = getFile(fileName);
		try (FileOutputStream fos = new FileOutputStream(file, append);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				BufferedWriter bw = new BufferedWriter(osw)) {
			bw.write(strToWrite);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("error to write file" + fileName
					+ " in class MyFileIO");
		}
	}

	private static String classesPath = initClassesPath();

	private static String initClassesPath() {
		String str = FileUtil.class.getResource("/").getPath();
		try {
			str = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (str.indexOf(":") > 0) {
			str = str.replaceAll("^/", "");
		}
		str = str.replace("/test-classes/", "/classes/");
		return str;
	}

	public static String getClassesPath() {
		return classesPath;
	}

	private static File getFile(String fileName) {
		File file = new File(fileName);
		if (!file.isFile()) {
			throw new RuntimeException("invalid file name: " + fileName);
		}
		return file;
	}
}
