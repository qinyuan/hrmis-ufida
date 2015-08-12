package qinyuan.hrmis.domain.recommend;

import java.sql.SQLException;
import qinyuan.lib.web.html.pagination.PaginatedTable;
import qinyuan.lib.web.html.pagination.Pagination;

public class PaginatedRecommendTable extends RecommendTable implements
		PaginatedTable {

	private int pageNum = 1;
	private int pageSize = 20;
	private int rowCount;

	public void setPageSize(int pageSize) {
		if (this.pageSize != pageSize) {
			this.pageSize = pageSize;
			setPageNum(1);
		}
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum <= 0 ? 1 : pageNum;
	}

	@Override
	protected Recommend[] getRecommends() throws SQLException {
		Recommend[] rms = super.getRecommends();
		rowCount = rms.length;
		if (rowCount <= (pageNum - 1) * pageSize) {
			pageNum = 1;
		}
		int startIndex = (pageNum - 1) * pageSize;
		int endIndex = pageNum * pageSize - 1;
		if (endIndex >= rms.length) {
			endIndex = rms.length - 1;
		}

		Recommend[] items = new Recommend[endIndex - startIndex + 1];
		for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
			items[j] = rms[i];
		}
		return items;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str + getPagination();
	}

	private Pagination getPagination() {
		return new Pagination().setPageNumber(pageNum).setPageSize(pageSize)
				.setTotal(rowCount);
	}

	public static void main(String[] args) throws SQLException {
	}
}