package qinyuan.hrmis.domain.user;

import java.sql.SQLException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.lang.MyMath;

public class LoginManager {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	private String getCookieKey() {
		return request.getServerName() + "HrmisAutoLogin";
	}

	public LoginManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		session = request.getSession();
	}

	public boolean validateUserInput() throws SQLException {
		return getInputUser() != null;
	}

	public boolean validateIdentityCode() {
		String identCode = (String) session.getAttribute("identCode");
		if (identCode == null) {
			return true;
		} else {
			String inputIdentCode = request.getParameter("identCode");
			return inputIdentCode != null && inputIdentCode.equals(identCode);
		}
	}

	public void saveUserByInput() throws SQLException {
		session.setAttribute("user", getInputUser());
	}

	public void saveUserByAutoLogin() throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare(
					"SELECT user_id FROM u_auto_login WHERE remote_addr=? AND cookie_value=?")
					.setString(1, getRemoteAddr())
					.setString(2, getCookieValue()).execute();
			if (cnn.next()) {
				User user = UserDao.getUser(cnn.getInt("user_id"));
				session.setAttribute("user", user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void setAutoLogin(boolean bool) throws SQLException {
		if (bool) {
			User user = getInputUser();
			if (inputUser != null) {
				String value = MyMath.getRandomStr(50);
				setCookie(value, 3600 * 24 * 90);
				saveCookieToDB(getRemoteAddr(), value, user.getId());
			}
		} else {
			String cookieValue = getCookieValue();
			setCookie(null, 0);
			deleteCookieFromDB(cookieValue, getRemoteAddr());
		}
	}

	public boolean isLogined() throws SQLException {
		String cookieValue = getCookieValue();
		if (cookieValue == null) {
			return false;
		}
		try (MyConn cnn = new MyConn()) {
			cnn.prepare(
					"SELECT COUNT(*) FROM u_auto_login WHERE remote_addr=? AND cookie_value=?")
					.setString(1, getRemoteAddr()).setString(2, cookieValue)
					.execute();
			cnn.next();
			return cnn.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private String getCookieValue() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(getCookieKey())) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	private void setCookie(String value, int seconds) throws SQLException {
		Cookie cookie = new Cookie(getCookieKey(), value);
		cookie.setMaxAge(seconds);
		cookie.setPath("/hrmis/");
		response.addCookie(cookie);
	}

	private String getRemoteAddr() {
		return request.getRemoteAddr();
	}

	private String getInputUsername() {
		return request.getParameter("hrmisUsername");
	}

	private String getInputPassword() {
		return request.getParameter("hrmisPassword");
	}

	private User inputUser;

	private User getInputUser() throws SQLException {
		if (inputUser == null) {
			inputUser = UserDao.getUser(getInputUsername(), getInputPassword());
		}
		return inputUser;
	}

	private static void deleteCookieFromDB(String remoteAddr, String cookieValue)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare(
					"DELETE FROM u_auto_login WHERE remote_addr=? AND cookie_value=?")
					.setString(1, remoteAddr).setString(2, cookieValue)
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static void saveCookieToDB(String remoteAddr, String value,
			int userId) throws SQLException {
		deleteCookieFromDB(remoteAddr, value);
		try (MyConn cnn = new MyConn()) {
			String query = "INSERT INTO u_auto_login(remote_addr,cookie_value,user_id)"
					+ " VALUES(?,?,?)";
			cnn.prepare(query).setString(1, remoteAddr).setString(2, value)
					.setInt(3, userId).execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
