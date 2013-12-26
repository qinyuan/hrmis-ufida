package qinyuan.lib.web.html.pagination;

import java.util.ArrayList;
import java.util.List;

public class TableHead {

	private List<String> valueList;
	private List<Integer> widthList;

	TableHead() {
		valueList = new ArrayList<String>();
		widthList = new ArrayList<Integer>();
	}

	public void add() {
		add("");
	}

	public void add(String value) {
		add(value, 0);
	}

	public void remove() {
		int index = valueList.size() - 1;
		valueList.remove(index);
		widthList.remove(index);
	}

	public int getColumnCount() {
		return valueList.size();
	}

	public void add(String value, int width) {
		valueList.add(value);
		widthList.add(width);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("<thead><tr>");
		for (int i = 0; i < valueList.size(); i++) {
			s.append("<td");

			int headWidth = widthList.get(i);
			if (headWidth > 0) {
				s.append(" style='width:" + headWidth + "px'");
			}

			s.append("><b>" + valueList.get(i) + "</b></td>");
		}
		s.append("</tr></thead>");
		return s.toString();
	}
}
