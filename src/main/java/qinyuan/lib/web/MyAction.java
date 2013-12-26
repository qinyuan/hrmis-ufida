package qinyuan.lib.web;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import qinyuan.lib.lang.MyMath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Set;

public class MyAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	protected final static String RESULT = "result";
	protected final static String DB_ERROR = "数据库操作失败";
	protected final static String AJAX_INFO = "ajax-info";
	protected final static String BLANK_AJAX_INFO = "blank-ajax-info";

	protected static boolean empty(String str) {
		return str == null ? true : str.isEmpty();
	}

	protected static boolean numeric(String str) {
		return MyMath.isNumeric(str);
	}

	protected static int parseInt(String str) {
		return Integer.parseInt(str);
	}

	protected static double parseDouble(String str) {
		return Double.parseDouble(str);
	}

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	protected void debug() {
		System.out.println("session: ");
		Enumeration<String> sessEnum = getSession().getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String sessKey = sessEnum.nextElement();
			System.out.println(sessKey + ": " + sessEnum);
		}

		System.out.println("\n");

		for (Entry<String, String[]> entry : getRequest().getParameterMap().entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			System.out.print(key + ":");
			for (String value : values) {
				System.out.print(" " + value);
			}
			System.out.println();
		}
	}

	protected HttpServletRequest getRequest() {
		if (request == null)
			request = ServletActionContext.getRequest();
		return request;
	}

	public String getJsp() {
		return getRequest().getServletPath().replaceAll("[.]action$", ".jsp");
	}

	protected HttpServletResponse getResponse() {
		if (response == null)
			response = ServletActionContext.getResponse();
		return response;
	}

	protected void printDBError() {
		setResult(DB_ERROR);
	}

	protected void setResult(Object str) {
		if (str == null) {
			str = "";
		}
		Object result = getRequest().getAttribute(RESULT);
		if (result != null) {
			result = result.toString() + str.toString();
		} else {
			result = str.toString();
		}
		setAttribute(RESULT, result);
	}

	protected HttpSession getSession() {
		if (session == null)
			session = getRequest().getSession();
		return session;
	}

	protected String getString(String key) {
		return getRequest().getParameter(key);
	}

	protected boolean getBoolean(String key) {
		String str = getString(key);
		return str != null && !str.toLowerCase().equals("false");
	}

	protected Integer getInt(String key) {
		String str = getString(key);
		return numeric(str) ? parseInt(str) : 0;
	}

	protected String[] getParameterNames() {
		Set<String> set = getRequest().getParameterMap().keySet();
		String[] strs = new String[set.size()];
		set.toArray(strs);
		return strs;
	}

	protected Object getSession(String key) {
		return getSession().getAttribute(key);
	}

	protected boolean hasParameter(String key) {
		return getString(key) != null;
	}

	protected boolean hasSession(String key) {
		return getSession(key) != null;
	}

	protected void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	protected void setSession(String key, Object value) {
		getSession().setAttribute(key, value);
	}
}
