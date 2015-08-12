package qinyuan.lib.tag.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import qinyuan.lib.tag.BodyEleTag;

public class TextTag extends BodyEleTag {

	private String required;

	public void setRequired(String required) {
		this.required = required;
	}

	@Override
	public void doTag() throws JspException, IOException {
		print("<input class='easyui-validatebox' type='text'");
		print(getBaseAttr());
		if (required != null && !required.isEmpty()) {
			print("data-options='required:true'");
		}
		print(" />");
	}
}
