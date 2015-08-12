package qinyuan.lib.web.html;

public class TransparentTextArea {

	private int rows;
	private int cols;
	private String tagId;
	private String text = "";

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("<textarea");
		if (rows > 0) {
			s.append(" rows='" + rows + "'");
		}
		if (cols > 0) {
			s.append(" cols='" + cols + "'");
		}
		if (tagId != null) {
			s.append(" id='" + tagId + "' name='" + tagId + "'");
		}
		s.append(" readonly='readonly'");
		s.append(" style='background-color:transparent;'>");
		s.append(text);
		s.append("</textarea>");
		return s.toString();
	}
}
