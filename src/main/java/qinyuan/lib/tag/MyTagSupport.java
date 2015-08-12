package qinyuan.lib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import qinyuan.lib.file.PropertyUtil;

public class MyTagSupport extends SimpleTagSupport {

	protected static String WEB_BASE;
	private JspWriter out;

	static {
		WEB_BASE = "/" + PropertyUtil.parse("page").get("proj");
	}

	protected void print(Object str) throws IOException {
		if (out == null) {
			out = getJspContext().getOut();
		}
		out.print(str.toString());
	}
}
