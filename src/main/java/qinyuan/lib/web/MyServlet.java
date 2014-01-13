package qinyuan.lib.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qinyuan.lib.file.PropertyUtil;
import qinyuan.lib.lang.MyMath;

public abstract class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String PROJ;
	protected final static String DB_ERROR;
	protected final static String UNKNOWN_ERROR;
	protected final static String INDEX;
	protected final static String RESULT = "result";
	protected final static String SUCCESS = "success";
	protected static String LOGIN;
	protected PrintWriter out;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	private boolean ajaxFirstCall;

	static {
		Map<String, String> map = PropertyUtil.parse("page");
		PROJ = map.get("proj");
		DB_ERROR = map.get("db_error");
		UNKNOWN_ERROR = map.get("unknown_error");
		INDEX = map.get("index");
		LOGIN = map.get("login");
	}

	protected void ajax() {
		ajax("");
	}

	protected void ajax(Object str) {
		if (ajaxFirstCall) {
			print("ajax:");
			ajaxFirstCall = false;
		}
		print(str);
	}

	protected void ajaxDBError() {
		ajax("数据库操作失败");
	}

	protected void debug() {
		System.out.println("session: ");
		Enumeration<String> sessEnum = session.getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String sessKey = sessEnum.nextElement();
			System.out.println(sessKey + ": " + sessEnum);
		}

		System.out.println("\n");

		for (Entry<String, String[]> entry : getParameterMap().entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			System.out.print(key + ":");
			for (String value : values) {
				System.out.print(" " + value);
			}
			System.out.println();
		}
	}

	protected abstract void execute();

	protected void forward() {
		forward(getJspURI());
	}

	protected void forward(String path) {
		try {
			RequestDispatcher requestDispatcher = this.getServletContext()
					.getRequestDispatcher(path);
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String getParameter(String key) {
		return request.getParameter(key);
	}

	protected Map<String, String[]> getParameterMap() {
		return request.getParameterMap();
	}

	protected String[] getParameterNames() {
		Set<String> set = request.getParameterMap().keySet();
		String[] strs = new String[set.size()];
		set.toArray(strs);
		return strs;
	}

	protected Object getSession(String key) {
		return session.getAttribute(key);
	}

	protected boolean hasParameter(String key) {
		return getParameter(key) != null;
	}

	protected boolean hasSession(String key) {
		return getSession(key) != null;
	}

	protected void print(Object obj) {
		if (obj != null)
			out.write(obj.toString());
	}

	protected void redirect() {
		redirect(getJspURI());
	}

	protected void redirect(String path) {
		if (path.startsWith("/")) {
			path = "/" + PROJ + path;
		}
		try {
			response.sendRedirect(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void setAttribute(String key, Object value) {
		request.setAttribute(key, value);
	}

	protected void setSession(String key, Object value) {
		session.setAttribute(key, value);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		out = response.getWriter();
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		ajaxFirstCall = true;
		execute();
	}

	private String getJspURI() {
		String fullURI = request.getRequestURI() + ".jsp";
		return fullURI.replace("/hrmis", "");
	}

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
}
