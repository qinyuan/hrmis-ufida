package qinyuan.lib.web.html;

import java.util.LinkedList;

public class TableRow extends BodyElement {

	private LinkedList<TableData> list = new LinkedList<TableData>();
	private boolean isHead = false;

	public TableRow() {
		this(false);
	}

	public TableRow(boolean isHead) {
		this.isHead = isHead;
	}

	public TableRow add(TableData td) {
		if (td == null)
			add("");

		list.add(td);
		return this;
	}

	public TableRow add(TableRow tableRow) {
		if (tableRow == null)
			add("");

		list.addAll(tableRow.list);
		return this;
	}

	public TableRow add(Object... objs) {
		for (Object obj : objs) {
			if (obj == null) {
				add(new TableData(isHead).setText(""));
			} else {
				add(new TableData(isHead).setText(obj.toString()));
			}
		}
		return this;
	}

	public TableRow append(Object... objs) {
		for (int i = objs.length - 1; i >= 0; i--) {
			if (objs[i] != null) {
				append(new TableData(isHead).setText(objs[i].toString()));
			}
		}
		return this;
	}

	public TableRow append(TableData td) {
		if (td == null)
			add("");

		list.addFirst(td);
		return this;
	}

	@Override
	public String getBody() {
		StringBuilder o = new StringBuilder();
		for (TableData tableData : list) {
			o.append(tableData);
		}
		return o.toString();
	}

	@Override
	public String getTagName() {
		return "tr";
	}

	public int getTdCount() {
		return list.size();
	}

	@Override
	public TableRow setAttr(String name, Object value) {
		return (TableRow) super.setAttr(name, value);
	}

	@Override
	public TableRow setClass(String style) {
		return (TableRow) super.setClass(style);
	}

	@Override
	public TableRow setId(String id) {
		return (TableRow) super.setId(id);
	}
}
