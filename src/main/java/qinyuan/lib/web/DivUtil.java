package qinyuan.lib.web;

public class DivUtil {

	public static String getFixedWidthDiv(Object text, int width) {
		if (text == null) {
			text = "";
		}

		return "<div style='width:" + width + "px;word-wrap:break-word;'>"
				+ text + "</div>";
	}
}
