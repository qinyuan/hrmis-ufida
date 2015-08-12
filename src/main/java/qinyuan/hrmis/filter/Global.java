package qinyuan.hrmis.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qinyuan.hrmis.domain.user.LoginManager;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.file.PropertyUtil;

public class Global implements Filter {

	private final static String INDEX = "/hrmis/index.jsp";
	private static boolean printRequests = false;
	private static boolean printSessions = false;
	static {
		Map<String, String> map = PropertyUtil.parse("debug");
		printRequests = ("true".equals(map.get("printRequests")));
		printSessions = ("true".equals(map.get("printSessions")));
	}

	private HttpServletRequest hrequest;
	private HttpServletResponse hresponse;
	private String requestURI;
	private final static String[] excludeSuffix = new String[] { ".css", ".js",
			".png", ".gif", ".jpg" };

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		initHttpFields(request, response);

		if (isExcluded()) {
			chain.doFilter(request, response);
			return;
		}

		if (printRequests)
			printRequests();
		if (printSessions)
			printSessions();

		User user = getUser();
		if (user == null) {
			autoCreateUser();
			user = getUser();
		}

		if (isIndex()) {
			if (user == null) {
				chain.doFilter(request, response);
			} else {
				hresponse.sendRedirect(user.getFirstPage());
			}
		} else {
			if (check(requestURI, user)) {
				chain.doFilter(request, response);
			} else {
				hresponse.sendRedirect(INDEX);
			}
		}
	}

	private void initHttpFields(ServletRequest request, ServletResponse response) {
		hrequest = (HttpServletRequest) request;
		try {
			hrequest.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		hresponse = (HttpServletResponse) response;
		requestURI = hrequest.getRequestURI();
	}

	private boolean isExcluded() {
		if (requestURI == null)
			return true;
		for (String str : excludeSuffix) {
			if (requestURI.endsWith(str)) {
				return true;
			}
		}
		return false;
	}

	private void printRequests() {
		Map<String, String[]> map = hrequest.getParameterMap();
		if (map.size() == 0) {
			return;
		}
		System.out.println("============ " + requestURI
				+ " requests =============");
		for (Entry<String, String[]> entry : map.entrySet()) {
			String[] strs = entry.getValue();
			String str = entry.getKey() + ": ";
			for (String s : strs) {
				str += s + ", ";
			}
			System.out.println(str);
		}
		System.out.println("===================================\n");
	}

	private void printSessions() {
		HttpSession session = hrequest.getSession();
		Enumeration<String> e = session.getAttributeNames();
		if (!e.hasMoreElements()) {
			return;
		}
		System.out.println("=========== " + requestURI
				+ " sessions ============");
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			System.out.println(key + ": " + session.getAttribute(key));
		}
		System.out.println("=================================\n");
	}

	private boolean isIndex() {
		return requestURI.matches("/hrmis/(index.jsp)?(\\?.*)?");
	}

	private void autoCreateUser() {
		LoginManager lm = new LoginManager(hrequest, hresponse);
		try {
			if (lm.isLogined()) {
				lm.saveUserByAutoLogin();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private User getUser() {
		return (User) hrequest.getSession().getAttribute("user");
	}

	private static boolean check(String requestURI, User user) {
		for (Entry<String, Integer> entry : User.getHrefPattern().entrySet()) {
			if (requestURI.matches(entry.getKey())) {
				if (user == null || (!user.hasPri(entry.getValue()))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
