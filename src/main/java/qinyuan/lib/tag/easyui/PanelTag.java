package qinyuan.lib.tag.easyui;

import qinyuan.lib.tag.BodyIncludeTag;

public class PanelTag extends BodyIncludeTag {

	private static final long serialVersionUID = 1L;
	private boolean collapsed = false;
	private boolean collapsible = true;

	public void setCollapsed(String collapsed) {
		if (collapsed != null && !collapsed.isEmpty())
			this.collapsed = true;
	}

	public void setCollapsible(String collapsible) {
		if (collapsible == null || collapsible.isEmpty()
				|| collapsible.equals("false")) {
			this.collapsible = false;
		} else {
			this.collapsible = true;
		}
	}

	@Override
	protected void start() {
		print("<div class='easyui-panel' ");
		if (getId() != null)
			print("id='" + getId() + "' ");
		print("title='" + getTitile() + "' ");
		print("style='background: #fafafa;'");
		if (collapsible) {
			print(" data-options='collapsible:true");
			if (collapsed)
				print(",collapsed:true");
			print("'");
		}
		print(">");
	}

	@Override
	protected void end() {
		print("</div>");
	}
}
