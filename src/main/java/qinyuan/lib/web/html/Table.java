package qinyuan.lib.web.html;

import java.util.ArrayList;
import java.util.List;

public class Table extends BodyElement {

	private List<TableRow> trs = new ArrayList<TableRow>();
	private List<TableRow> headTrs = new ArrayList<TableRow>();

	public Table addHead(TableRow tr) {
		headTrs.add(tr);
		return this;
	}

	public Table add(TableRow tableRow) {
		trs.add(tableRow);
		return this;
	}

	public Table add(Table table) {
		headTrs.addAll(table.headTrs);
		trs.addAll(table.trs);
		return this;
	}

	@Override
	public Table setAttr(String name, Object value) {
		return (Table) super.setAttr(name, value);
	}

	@Override
	public Table setClass(String style) {
		return (Table) super.setClass(style);
	}

	@Override
	public Table setId(String id) {
		return (Table) super.setId(id);
	}

	@Override
	public String getBody() {
		StringBuilder o = new StringBuilder();
		o.append("<thead>");
		for (TableRow tr : headTrs) {
			o.append(tr);
		}
		o.append("</thead><tbody>");
		for (TableRow tableRow : trs) {
			o.append(tableRow);
		}
		o.append("</tbody>");
		return o.toString();
	}

	@Override
	public String getTagName() {
		return "table";
	}

	public static Table createTable(String[] strs, int colNum) {
		Table t = new Table();
		TableRow tr = null;
		int i = 0;
		for (String str : strs) {
			if (i == 0) {
				tr = new TableRow();
				t.add(tr);
			}
			tr.add(str);
			if ((++i) == colNum) {
				i = 0;
			}
		}
		return t;
	}
}
