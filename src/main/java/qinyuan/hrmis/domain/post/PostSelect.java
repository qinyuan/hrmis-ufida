package qinyuan.hrmis.domain.post;

import qinyuan.hrmis.dao.Post;
import qinyuan.hrmis.dao.PostDao;
import qinyuan.lib.web.html.Select;

public class PostSelect {

	private int postId;
	private boolean total;

	public PostSelect() {
		this(-1);
	}

	public PostSelect(int postId) {
		this.postId = postId;
	}

	public PostSelect setTotal(boolean bool) {
		this.total = bool;
		return this;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}

	public Select toSelect() {
		Select select = new Select().setId("postId").select(postId);
		if (total) {
			select.addOption(0, "（全部）");
		}
		for (Post post : PostDao.getInstances()) {
			select.addOption(post.getId(), post.getName());
		}
		return select;
	}
}
