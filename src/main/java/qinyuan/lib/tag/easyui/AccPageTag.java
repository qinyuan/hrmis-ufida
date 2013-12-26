package qinyuan.lib.tag.easyui;

import qinyuan.lib.tag.BodyIncludeTag;

public class AccPageTag extends BodyIncludeTag {

	private static final long serialVersionUID = 1L;

	private boolean selected;

	public void setSelected(String selected) {
		if (selected != null && !selected.isEmpty()) {
			this.selected = true;
		}
	}

	@Override
	protected void start() {
		print("<div title='" + getTitile() + "'");
		if (selected)
			print(" data-options='selected:true'");
		print(" style='overflow:auto;'>");
	}

	@Override
	protected void end() {
		print("</div>");
	}
}
