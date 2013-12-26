package qinyuan.lib.tag.easyui;

import qinyuan.lib.tag.BodyIncludeTag;

public class AccordionTag extends BodyIncludeTag {

	private static final long serialVersionUID = 1L;

	@Override
	protected void start() {
		print("<div class='easyui-accordion'>");
	}

	@Override
	protected void end() {
		print("</div>");
	}
}
