package qinyuan.lib.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessParam {

	private HttpServletRequest request;
	private HttpSession session;
	private boolean useSessionParam;
	private String keyPrefix = "";

	public SessParam(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
		String str = request.getParameter("useSessionParam");
		if (str != null && str.toLowerCase().equals("false")) {
			useSessionParam = false;
		} else {
			useSessionParam = true;
		}
	}

	public void setPrefix(String keyPrefix) {
		if (keyPrefix == null) {
			this.keyPrefix = "";
		} else {
			this.keyPrefix = keyPrefix;
		}
	}

	private String getFullKey(String key) {
		if (keyPrefix.isEmpty()) {
			return key;
		} else {
			return keyPrefix + "_" + key;
		}
	}

	public boolean hasKey(String key) {
		return request.getParameter(key) != null
				|| session.getAttribute(getFullKey(key)) != null;
	}

	public String getStr(String key) {
		String value = request.getParameter(key);
		if (value == null) {
			if (useSessionParam) {
				value = (String) session.getAttribute(getFullKey(key));
				return value == null ? "" : value;
			} else {
				return "";
			}
		} else {
			return value;
		}
	}

	public Integer getInt(String key) {
		String str = request.getParameter(key);
		if (str == null) {
			if (useSessionParam) {
				Object value = session.getAttribute(getFullKey(key));
				if (value == null) {
					return 0;
				} else if (value instanceof Integer) {
					return (Integer) value;
				} else {
					return Integer.valueOf(value.toString());
				}
			} else {
				return 0;
			}
		} else {
			return Integer.valueOf(str);
		}
	}

	public static Integer getInt(HttpServletRequest request, String key) {
		return new SessParam(request).getInt(key);
	}

	public static String getStr(HttpServletRequest request, String key) {
		return new SessParam(request).getStr(key);
	}
}