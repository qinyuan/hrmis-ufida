package qinyuan.lib.web.html;

public class ImageUtil {

	public final static String IMAGE_BASE = "/hrmis/lib/img/";
	public final static String RIGHT_ARROW_IMAGE_PATH = IMAGE_BASE
			+ "arrow-right.gif";
	public final static String LEFT_ARROW_IMAGE_PATH = IMAGE_BASE
			+ "arrow-left.gif";
	public final static String UP_ARROW_IMAGE_PATH = IMAGE_BASE
			+ "arrow-up.gif";
	public final static String DOWN_ARROW_IMAGE_PATH = IMAGE_BASE
			+ "arrow-down.gif";
	public final static String COMMENT_IMAGE_PATH = IMAGE_BASE + "comment.gif";
	public final static String DEL_IMAGE_PATH = IMAGE_BASE + "delete-page.gif";
	public final static String EDIT_IMAGE_PATH = IMAGE_BASE + "edit-page.gif";
	public final static String PRI_IMAGE_PATH = IMAGE_BASE + "privilege.gif";

	public static String getDelImage(int id) {
		return getImage("del" + id, DEL_IMAGE_PATH, "删除") + " ";
	}

	public static String getMdfImage(int id) {
		return getImage("mdf" + id, EDIT_IMAGE_PATH, "修改") + " ";
	}

	public static String getPriImage(int id) {
		return getImage("pri" + id, PRI_IMAGE_PATH, "权限设置") + " ";
	}

	public static String getCommentImage(String id) {
		return getImage(id, COMMENT_IMAGE_PATH, "查看备注");
	}

	public static String getRightArrowImage(String id, String alt) {
		return getImage(id, RIGHT_ARROW_IMAGE_PATH, alt);
	}

	public static String getLeftArrowImage(String id, String alt) {
		return getImage(id, LEFT_ARROW_IMAGE_PATH, alt);
	}

	public static String getUpArrowImage(String id, String alt) {
		return getUpArrowImage(id, alt, null);
	}

	public static String getUpArrowImage(String id, String alt,
			String dataOptions) {
		return getImage(id, UP_ARROW_IMAGE_PATH, alt, dataOptions);
	}

	public static String getDownArrowImage(String id, String alt) {
		return getDownArrowImage(id, alt, null);
	}

	public static String getDownArrowImage(String id, String alt,
			String dataOptions) {
		return getImage(id, DOWN_ARROW_IMAGE_PATH, alt, dataOptions);
	}

	protected static String getImage(String id, String src, String alt) {
		return getImage(id, src, alt, null);
	}

	protected static String getImage(String id, String src, String alt,
			String dataOptions) {
		StringBuilder s = new StringBuilder();
		s.append("<img");
		if (id != null)
			s.append(" id='" + id + "'");
		if (src != null)
			s.append(" src='" + src + "'");
		if (alt != null)
			s.append(" alt='" + alt + "' title='" + alt + "'");
		if (dataOptions != null)
			s.append(" data-options='" + dataOptions + "'");
		s.append(" />");
		return s.toString();
	}
}
