package qinyuan.lib.web.html;

import java.util.ArrayList;
import java.util.List;

public class Tree extends BodyElement {

	private List<Tree> trees = new ArrayList<Tree>();
	private List<Leaf> leaves = new ArrayList<Leaf>();
	private String text;

	public Tree add(String id, String text) {
		leaves.add(new Leaf().setId(id).setText(text));
		return this;
	}

	public Tree add(Tree tree) {
		if (tree != this)
			trees.add(tree);
		return this;
	}

	public Tree setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public Tree setAttr(String name, Object value) {
		return (Tree) super.setAttr(name, value);
	}

	@Override
	public Tree setClass(String style) {
		return (Tree) super.setClass(style);
	}

	@Override
	public Tree setId(String id) {
		return (Tree) super.setId(id);
	}

	@Override
	public String getBody() {
		StringBuilder o = new StringBuilder();
		if (text != null) {
			o.append("<div></div><span>" + text + "</span>");
		}
		for (Tree tree : trees) {
			o.append("<li>" + tree + "</li>");
		}
		for (Leaf leaf : leaves) {
			o.append(leaf);
		}
		return o.toString();
	}

	@Override
	public String getTagName() {
		return "ul";
	}

	private class Leaf extends BodyElement {

		private String text;

		@Override
		public String getTagName() {
			return "li";
		}

		public Leaf setText(String text) {
			this.text = text;
			return this;
		}

		@Override
		public String getBody() {
			return "<span>"+text+"</span>";
		}

		@Override
		public Leaf setId(String id) {
			return (Leaf) super.setId(id);
		}
	}

	public static void main(String[] args) {
		Tree tree = new Tree();
		tree.setId("L1");
		tree.setText("1");
		tree.add("L11", "11");
		tree.add("L12", "12");

		Tree tree2 = new Tree();
		tree2.add("L121", "121");
		tree2.add("L122", "122");
		tree.add(tree2);

		String str = tree.toString().replace("</ul>", "\n</ul>\n")
				.replace("<l", "\n<l");
		System.out.println(str);
	}
}
