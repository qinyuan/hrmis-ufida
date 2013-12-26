package qinyuan.lib.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessParam {

	private static boolean useSessionParam(HttpServletRequest request) {
		String str = request.getParameter("useSessionParam");
		if (str != null && str.toLowerCase().equals("false")) {
			return false;
		} else {
			return true;
		}
	}

	public static Integer getInt(HttpServletRequest request, String key) {
		String str = request.getParameter(key);
		if (str == null) {
			if (useSessionParam(request)) {
				HttpSession session = request.getSession();
				Integer value = (Integer) session.getAttribute(key);
				return value == null ? 0 : value;
			} else {
				return 0;
			}
		} else {
			return Integer.valueOf(str);
		}
	}

	public static String getStr(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		if (value == null) {
			if (useSessionParam(request)) {
				HttpSession session = request.getSession();
				value = (String) session.getAttribute(key);
				return value == null ? "" : value;
			} else {
				return "";
			}
		} else {
			return value;
		}
	}
}