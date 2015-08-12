package qinyuan.hrmis.dao;

import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.lib.db.HConn;

public class PostDao {

	public static Post[] getInstances() {
		return HConn.getOneArray("FROM Post", Post.class);
	}

	public static Post getInstance(int postId) {
		return HConn.getOne(Post.class, postId);
	}

	public static void add(String postName) {
		Post post = new Post();
		post.setName(postName);
		HConn.saveOne(post);
	}

	public static void delete(int id) {
		if (!isUsed(id)) {
			HConn.deleteOne(Post.class, id);
		}
	}

	public static boolean exist(int postId) {
		return HConn.existsOne(Post.class, postId);
	}

	public static boolean exist(String postName) {
		return !HRMIS.empty("SELECT * FROM rec_post WHERE name='" + postName
				+ "'");
	}

	public static boolean isUsed(int id) {
		return !HRMIS.empty("SELECT * FROM rec_demand WHERE post_id=" + id);
	}

	public static void mdf(int id, String newName) {
		HConn cnn = new HConn();
		Post post = cnn.get(Post.class, id);
		post.setName(newName);
		cnn.update(post);
		cnn.close();
	}

	public static void main(String[] args) {
		for (Post p : getInstances()) {
			System.out.println(p.getId() + " " + p.getName());
		}
		System.out.println(exist(3));
		System.out.println(exist(5));
	}
}
