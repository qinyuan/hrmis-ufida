package qinyuan.lib.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelBook implements AutoCloseable {

	private FileInputStream fis;
	private HSSFWorkbook wb;
	private File file;

	public ExcelBook(String fileName) {
		this(new File(fileName));
	}

	public ExcelBook(File file) {
		this.file = file;
		try {
			fis = new FileInputStream(file);
			wb = new HSSFWorkbook(fis);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ExcelSheet getSheet(int index) {
		return new ExcelSheet(wb.getSheetAt(index));
	}

	@Override
	public void close() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
