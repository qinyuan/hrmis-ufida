package qinyuan.lib.tag;

public class BodyEleTag extends MyTagSupport {

	private String id;
	private String style;
	private String value;
	private boolean disabled;

	public String getBaseAttr() {
		String o = "";
		if (id != null) {
			o += " id='" + id + "' name='" + id + "' ";
		}
		if (style != null) {
			o += " class='" + style + "' ";
		}
		if (value != null) {
			o += " value='" + value + "' ";
		}
		if (disabled) {
			o += " disabled='disabled' ";
		}
		return o;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public String getId() {
		return id;
	}

	public String getStyle() {
		return style;
	}

	public String getValue() {
		return value;
	}

	public void setDisable(String disabled) {
		if (disabled != null && !disabled.isEmpty())
			this.disabled = true;
		else
			this.disabled = false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
