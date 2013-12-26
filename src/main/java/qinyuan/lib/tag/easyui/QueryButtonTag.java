package qinyuan.lib.tag.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import qinyuan.lib.tag.BodyEleTag;

public class QueryButtonTag extends BodyEleTag {

	public String text = "查询";

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void doTag() throws JspException, IOException {
		print("<a href='#' class='easyui-linkbutton' ");
		print(getBaseAttr());
		print(" data-options='iconCls:\"icon-search\"'>");
		print(text);
		print("</a>");
	}
}
