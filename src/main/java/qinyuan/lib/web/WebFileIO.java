package qinyuan.lib.web;

import java.io.IOException;

import javax.servlet.ServletContext;

import qinyuan.lib.file.FileUtil;

public class WebFileIO {

	private ServletContext application;

	public WebFileIO(ServletContext application) {
		this.application = application;
	}

	public void append(String fileName, String strToAppend) throws IOException {
		FileUtil.append(getRealPath(fileName), strToAppend);
	}

	public String readAll(String fileName) throws IOException {
		String realFileName = getRealPath(fileName);
		return FileUtil.readAll(realFileName);
	}

	public String[] readAsStrArr(String fileName) throws Exception {
		String realFileName = getRealPath(fileName);
		return FileUtil.readAsStrArr(realFileName);
	}

	public void write(String fileName, String strToWrite) throws IOException {
		String realFileName = getRealPath(fileName);
		FileUtil.write(realFileName, strToWrite);
	}

	private String getRealPath(String fileName) {
		return application.getRealPath(fileName);
	}
}
