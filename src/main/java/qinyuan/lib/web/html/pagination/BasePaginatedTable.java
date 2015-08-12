package qinyuan.lib.web.html.pagination;

import java.sql.SQLException;
import qinyuan.lib.db.MySQLConn;
import qinyuan.lib.db.MySQLDataSource;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public abstract class BasePaginatedTable implements PaginatedTable {

	private int pageNum = 1;
	private int pageSize = 20;
	private MySQLDataSource ds;

	public BasePaginatedTable(MySQLDataSource ds) {
		this.ds = ds;
	}

	public void setPageSize(int pageSize) {
		if (this.pageSize != pageSize) {
			this.pageSize = pageSize;
			setPageNum(1);
		}
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum <= 0 ? 1 : pageNum;
	}

	protected abstract String getQuery();

	protected abstract void doTableRow(TableRow tr, MySQLConn cnn)
			throws SQLException;

	@Override
	public String toString() {
		MySQLConn cnn = null;
		try {
			cnn = ds.getConn();
			String query = getQuery();
			if (!query.matches("SELECT\\s.*\\sFROM\\s.*")) {
				throw new RuntimeException("incorrect SQL command: " + query);
			}

			int rowCount = getRowCount(query, cnn);
			int maxPageNum = (int) Math.ceil((double) rowCount / pageSize);
			if (pageNum > maxPageNum) {
				pageNum = maxPageNum;
			} else if (pageNum <= 0 && rowCount > 0) {
				pageNum = 1;
			}

			query = getLimitQuery(query, pageNum, pageSize);
			cnn.execute(query);

			return getTable(cnn) + getPagination(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			return "<h3>数据库查询失败</h3>";
		} finally {
			if (cnn != null) {
				cnn.close();
			}
		}
	}

	protected Pagination getPagination(int rowCount) {
		return new Pagination().setPageNumber(pageNum).setPageSize(pageSize)
				.setTotal(rowCount);
	}

	protected abstract void doTableHead(TableHead th);

	protected abstract String getTitle();

	private String getTable(MySQLConn cnn) throws SQLException {
		StringBuilder s = new StringBuilder();

		s.append("<div class='easyui-panel'");
		String title = getTitle();
		if (title != null)
			s.append(" title='" + title + "'");
		s.append(">");

		s.append("<table>");

		TableHead th = new TableHead();
		doTableHead(th);
		s.append(th.toString());

		s.append("<tbody>");
		if (pageNum == 0) {
			TableRow tr = new TableRow();
			TableData td = new TableData().setAttr("colspan",
					th.getColumnCount());
			td.setText("无记录...");

			tr.add(td);
			s.append(tr);
		} else {
			while (cnn.next()) {
				TableRow tr = new TableRow();
				doTableRow(tr, cnn);
				s.append(tr);
			}
		}
		s.append("</tbody>");
		s.append("</table>");
		s.append("</div>");
		return s.toString();
	}

	protected static TableData getCompressedStrTd(String str) {
		if (str == null)
			str = "";
		TableData td = new TableData();
		td.setAttr("title", str);
		String text = str.length() > 15 ? str.substring(0, 15) + "..." : str;
		td.setText(text);
		return td;
	}

	protected static String getSelfLink(String href, String text) {
		return "<a href='" + href + "'>" + text + "</a>";
	}

	protected static String getNewLink(String href, Object text) {
		return "<a href='" + href + "' target='_blank'>" + text + "</a>";
	}

	protected static String getEmptyLink(String id, String text) {
		return "<a id='" + id + "' href='#'>" + text + "</a>";
	}

	private static int getRowCount(String query, MySQLConn cnn)
			throws Exception {
		int firstFromIndex = query.indexOf("FROM");
		query = "SELECT COUNT(*) " + query.substring(firstFromIndex);
		cnn.execute(query);
		cnn.next();
		return cnn.getInt(1);
	}

	private static String getLimitQuery(String query, int pageNum, int pageSize) {
		if (pageNum <= 1) {
			return query + " LIMIT " + pageSize;
		} else {
			return query + " LIMIT " + (pageNum - 1) * pageSize + ","
					+ pageSize;
		}
	}
}