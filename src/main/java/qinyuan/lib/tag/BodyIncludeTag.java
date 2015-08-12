package qinyuan.lib.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class BodyIncludeTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private JspWriter out;
	private String title;
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitile() {
		return title;
	}

	protected abstract void start();

	protected abstract void end();

	@Override
	public int doStartTag() throws JspException {
		start();
		return EVAL_BODY_INCLUDE;
	}

	protected void print(String str) {
		try {
			out = pageContext.getOut();
			out.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int doEndTag() throws JspException {
		end();
		return EVAL_PAGE;
	}
}
