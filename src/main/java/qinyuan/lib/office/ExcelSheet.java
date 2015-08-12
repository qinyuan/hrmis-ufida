package qinyuan.lib.office;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelSheet {

	private HSSFSheet sheet;
	private HSSFWorkbook book;

	ExcelSheet(HSSFSheet sheet) {
		this.sheet = sheet;
		this.book = sheet.getWorkbook();
	}

	public void addBorder(int startRowIndex, int endRowIndex,
			int startColumIndex, int endColumnIndex) {
		addBorder(startRowIndex, endRowIndex, startColumIndex, endColumnIndex,
				(short) 1);
	}

	public void addBorder(int startRowIndex, int endRowIndex,
			int startColumnIndex, int endColumnIndex, short borderWeight) {
		CellStyle cellStyle = null;
		for (int i = startRowIndex; i <= endRowIndex; i++) {
			for (int j = startColumnIndex; j <= endColumnIndex; j++) {
				HSSFCell cell = getCell(i, j);
				cellStyle = createCellStyle();
				cellStyle.cloneStyleFrom(cell.getCellStyle());
				cellStyle.setBorderBottom(borderWeight);
				cellStyle.setBorderLeft(borderWeight);
				cellStyle.setBorderRight(borderWeight);
				cellStyle.setBorderTop(borderWeight);
				cell.setCellStyle(cellStyle);
			}
		}
	}

	public HSSFCell getCell(int rowIndex, int columnIndex) {
		HSSFRow row = getRow(rowIndex);
		HSSFCell cell = getCell(row, columnIndex);
		return cell;
	}

	public CellStyle getCellStyle(int rowIndex, int columnIndex) {
		return getCell(rowIndex, columnIndex).getCellStyle();
	}

	public void merge(int startRowIndex, int endRowIndex, int startColumnIndex,
			int endColumnIndex) {
		if (startRowIndex <= endRowIndex && startColumnIndex <= endColumnIndex) {
			sheet.addMergedRegion(new CellRangeAddress(startRowIndex,
					endRowIndex, startColumnIndex, endColumnIndex));
		}
	}

	public void merge(int startRowIndex, int endRowIndex, int startColumnIndex,
			int endColumnIndex, boolean center) {
		if (startRowIndex <= endRowIndex && startColumnIndex <= endColumnIndex) {
			sheet.addMergedRegion(new CellRangeAddress(startRowIndex,
					endRowIndex, startColumnIndex, endColumnIndex));
			if (center) {
				CellStyle cellStyle = createCellStyle();
				HSSFCell cell = getCell(startRowIndex, startColumnIndex);
				cellStyle.cloneStyleFrom(cell.getCellStyle());
				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
				cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				cell.setCellStyle(cellStyle);
			}
		}
	}

	public void setColumnWidth(int columnIndex, int width) {
		sheet.setColumnWidth(columnIndex, width);
	}

	public int getColumnWidth(int columnIndex) {
		return sheet.getColumnWidth(columnIndex);
	}

	public void setComment(int rowIndex, int columnIndex, int rows, int cols,
			String text) {
		HSSFCell cell = getCell(rowIndex, columnIndex);
		if (cell.getCellComment() != null) {
			cell.removeCellComment();
		}
		ClientAnchor anchor = getCreationHelper().createClientAnchor();

		anchor.setCol1(columnIndex + 2);
		anchor.setCol2(columnIndex + 2 + cols);
		anchor.setRow1(rowIndex + 1);
		anchor.setRow2(rowIndex + 1 + rows);

		RichTextString str = getCreationHelper().createRichTextString(text);
		HSSFFont font = createFont();
		font.setFontHeightInPoints((short) 10);
		str.applyFont(font);

		Comment comment = getDrawing().createCellComment(anchor);
		comment.setString(str);

		cell.setCellComment(comment);
	}

	public void setValue(int rowIndex, int columnIndex, String value) {
		setValueAndStyle(rowIndex, columnIndex, value, null);
	}

	public void setValue(int rowIndex, int columnIndex, int value) {
		HSSFCell cell = getCell(rowIndex, columnIndex);
		cell.setCellValue(value);
		refreshRow(cell.getRow());
	}

	public void setValueAndColor(int rowIndex, int columnIndex, String value,
			ExcelColor color) {
		HSSFFont font = createFont();
		font.setColor(ExcelColor.getColorIndex(color));

		HSSFCellStyle cellStyle = createCellStyle();
		cellStyle.cloneStyleFrom(getCellStyle(rowIndex, columnIndex));
		cellStyle.setFont(font);

		setValueAndStyle(rowIndex, columnIndex, value, cellStyle);
	}

	public void setValueAndStyle(int rowIndex, int columnIndex, String value,
			CellStyle cellStyle) {
		HSSFCell cell = getCell(rowIndex, columnIndex);
		cell.setCellValue(value);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}

		refreshRow(cell.getRow());
	}

	private CreationHelper creationHelper;

	private CreationHelper getCreationHelper() {
		if (creationHelper == null) {
			creationHelper = book.getCreationHelper();
		}
		return creationHelper;
	}

	private Drawing drawing;

	private Drawing getDrawing() {
		if (drawing == null) {
			drawing = sheet.createDrawingPatriarch();
		}
		return drawing;
	}

	private HSSFCellStyle createCellStyle() {
		return book.createCellStyle();
	}

	private HSSFFont createFont() {
		return book.createFont();
	}

	private static void refreshRow(HSSFRow row) {
		row.setHeight(row.getHeight());
	}

	private HSSFCell getCell(HSSFRow row, int columnIndex) {
		HSSFCell cell = row.getCell(columnIndex);
		if (cell == null) {
			cell = row.createCell(columnIndex);
		}
		return cell;
	}

	private HSSFRow getRow(int rowIndex) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}
}
