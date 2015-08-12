package qinyuan.lib.web.html.pagination;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import qinyuan.lib.lang.MyMath;

public class PaginatedTableUtil {

	private static Map<String, PaginatedTable> map = new HashMap<String, PaginatedTable>();

	public static <T extends PaginatedTable> T get(HttpServletRequest request,
			Class<T> type) throws Exception {
		update(request, type);

		String key = getKey(request, type);
		@SuppressWarnings("unchecked")
		T pt = (T) map.get(key);
		if (pt == null) {
			pt = (T) type.getConstructor().newInstance();
			map.put(key, pt);
		}

		return pt;
	}

	private static <T extends PaginatedTable> String getKey(
			HttpServletRequest request, Class<T> type) throws Exception {
		String s = "";
		if (request != null) {
			s += request.getRequestURI();
		}
		if (type != null) {
			s += type.getName();
		}
		return s;
	}

	private static <T extends PaginatedTable> void update(
			HttpServletRequest request, Class<T> type) throws Exception {
		if (request == null)
			return;
		String key = getKey(request, type);
		PaginatedTable pt = map.get(key);

		if (pt != null) {
			String pageNumStr = request.getParameter("pageNumber");
			if (pageNumStr != null && MyMath.isNumeric(pageNumStr)) {
				int pageNum = Integer.parseInt(pageNumStr);
				if (pageNum > 0)
					pt.setPageNum(pageNum);
			}

			String pageSizeStr = request.getParameter("pageSize");
			if (pageSizeStr != null && MyMath.isNumeric(pageSizeStr)) {
				int pageSize = Integer.parseInt(pageSizeStr);
				if (pageSize > 0) {
					pt.setPageSize(pageSize);
				}
			}
		}
	}
}
