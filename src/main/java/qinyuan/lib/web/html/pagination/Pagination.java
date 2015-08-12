package qinyuan.lib.web.html.pagination;

public class Pagination {

	private int total;
	private int pageSize;
	private int pageNumber;

	public Pagination setTotal(int total) {
		this.total = total;
		return this;
	}

	public Pagination setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Pagination setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("<div id='paginationDiv' ");
		s.append("style='background:#efefef;border:1px solid #ccc;'>");
		s.append("</div>");

		s.append("<script>");
		s.append("$('#paginationDiv').pagination({");
		s.append("total:" + total + ",");
		s.append("pageSize:" + pageSize + ",");
		s.append("pageNumber:" + pageNumber + ",");

		s.append("onSelectPage:function(pageNumber,pageSize){");
		s.append("addLocationParamAndReload({'pageNumber':pageNumber,'pageSize':pageSize});");
		s.append("}");

		s.append("});");
		s.append("</script>");
		return s.toString();
	}
}
