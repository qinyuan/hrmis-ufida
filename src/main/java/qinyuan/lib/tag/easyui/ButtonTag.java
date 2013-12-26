package qinyuan.lib.tag.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import qinyuan.lib.tag.BodyEleTag;

public class ButtonTag extends BodyEleTag {

	public String text = "";

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void doTag() throws JspException, IOException {
		print("<a href='#' class='easyui-linkbutton' ");
		print(getBaseAttr());
		if (getDisabled())
			print(" data-options='disable:true'");
		print(">");
		print(text);
		print("</a>");
	}
}
