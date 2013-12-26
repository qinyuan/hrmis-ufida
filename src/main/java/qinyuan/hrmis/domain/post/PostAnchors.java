package qinyuan.hrmis.domain.post;

import qinyuan.hrmis.dao.Post;
import qinyuan.hrmis.dao.PostDao;

public class PostAnchors {

	private int postId;

	public PostAnchors() {
		this(-1);
	}

	public PostAnchors(int postId) {
		this.postId = postId;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Post post : PostDao.getInstances()) {
			if (postId == post.getId()) {
				s.append(post.getName());
			} else {
				s.append("<a href='#post" + post.getId() + "'>"
						+ post.getName() + "</a>");
			}
			s.append("&nbsp;&nbsp;");
		}
		return s.toString();
	}
}
