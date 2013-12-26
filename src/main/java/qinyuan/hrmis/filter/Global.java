package qinyuan.hrmis.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map.Entry;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import qinyuan.hrmis.domain.user.LoginManager;
import qinyuan.hrmis.domain.user.User;

public class Global implements Filter {

	private final static String INDEX = "/hrmis/index.jsp";

	private HttpServletRequest hrequest;
	private HttpServletResponse hresponse;
	private final static String[] excludeSuffix = new String[] { ".css", ".js",
			".png", ".gif", ".jpg" };

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		hrequest = (HttpServletRequest) request;
		hresponse = (HttpServletResponse) response;

		String requestURI = hrequest.getRequestURI();
		for (String str : excludeSuffix) {
			if (requestURI.endsWith(str)) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		User user = getUser();
		if (user == null) {
			autoCreateUser();
			user=getUser();
		}

		if (isIndex(requestURI)) {
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

	private boolean isIndex(String requestURI) {
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
