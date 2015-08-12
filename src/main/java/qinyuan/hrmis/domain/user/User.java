package qinyuan.hrmis.domain.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import qinyuan.lib.web.html.Table;

public class User {

	private static final Map<Integer, Privilege> priMap = Privilege.priMap;
	private int id;
	private String name;
	private String password;
	private int[] priIds;
	private Set<Integer> subUserIds;

	User() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPriIds(int[] priIds) {
		this.priIds = priIds;
	}

	public void setSubUserIds(Set<Integer> subUserIds) {
		this.subUserIds = subUserIds;
	}

	public int getId() {
		return id;
	}

	public String getNavi() {
		StringBuilder s = new StringBuilder();
		s.append("<ul>");
		s.append("<li><span><a href='/hrmis/login.jsp'>主页</a><span></li>");
		for (int priId : priIds) {
			s.append("<li>");
			s.append(getNaviByPriId(priId));
			s.append(getNaviDetailsByPriId(priId));
			s.append("</li>");
		}
		s.append("</ul>");
		return s.toString();
	}

	public Table getNaviBoxes() {
		String[] strs = new String[priIds.length];
		for (int i = 0; i < priIds.length; i++) {
			strs[i] = getNaviBox(priIds[i]);
		}
		final int colNum = 5;
		return Table.createTable(strs, colNum);
	}

	public String getNaviDetails() {
		StringBuilder s = new StringBuilder();

		for (int priId : priIds) {
			s.append(getNaviDetailsByPriId(priId));
		}

		return s.toString();
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public boolean hasPri(int priId) {
		for (int id : priIds) {
			if (id == priId) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSubUser(Integer userId) {
		return subUserIds.contains(userId);
	}

	/**
	 * This method is called by Global filter
	 * 
	 * @return
	 */
	public static Map<String, Integer> getHrefPattern() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Entry<Integer, Privilege> entry : priMap.entrySet()) {
			map.put(entry.getValue().getPattern(), entry.getKey());
		}
		return map;
	}

	private static String getNaviByPriId(int id) {
		return "<span>" + priMap.get(id).getTitle() + "</span>";
	}

	private static String getNaviDetailsByPriId(int id) {
		StringBuilder s = new StringBuilder("<div>");
		Privilege pri = priMap.get(id);
		for (String anchor : pri.getAnchors()) {
			s.append(anchor);
		}
		s.append("</div>");
		return s.toString();
	}

	private static String getNaviBox(int id) {
		StringBuilder s = new StringBuilder();
		Privilege pri = priMap.get(id);
		s.append("<div class='naviBox'>");
		s.append("<h3>" + pri.getTitle() + "</h3>");
		s.append("<ul>");
		for (String anchor : pri.getAnchors()) {
			s.append("<li>" + anchor + "</li>");
		}
		s.append("</ul>");
		s.append("</div>");
		return s.toString();
	}

	public String getFirstPage() {
		if (hasPri(6)) {
			return "recruiter/resumeList.jsp";
		} else if (hasPri(5)) {
			return "rec-manager/statistics.jsp";
		} else {
			return "";
		}
	}
}
