package qinyuan.hrmis.action.de.admin;

import qinyuan.lib.web.MyAction;
import static qinyuan.hrmis.dao.PostDao.*;

public class PostAction extends MyAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() {
		try {
			addPost();
			mdfPost();
			delPost();
		} catch (Exception e) {
			e.printStackTrace();
			printDBError();
		}
		return BLANK_AJAX_INFO;
	}

	private void delPost() throws Exception {
		int delId = getInt("delId");
		if (delId > 0) {
			if (isUsed(delId)) {
				setResult("不能删除已存在需求的职位类型");
			} else {
				delete(delId);
			}
		}
	}

	private void addPost() throws Exception {
		String postName = getString("postName");
		if (!empty(postName)) {
			if (exist(postName)) {
				setResult("职位“" + postName + "”已存在");
			} else {
				add(postName);
			}
		}
	}

	private void mdfPost() throws Exception {
		int mdfId = getInt("mdfId");
		String newName = getString("newName");
		if (mdfId > 0 && !empty(newName)) {
			if (exist(newName)) {
				setResult("职位名“" + newName + "”已存在");
			} else {
				mdf(mdfId, newName);
			}
		}
	}
}
