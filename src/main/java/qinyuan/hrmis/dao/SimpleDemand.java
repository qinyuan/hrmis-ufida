package qinyuan.hrmis.dao;

public class SimpleDemand {

	private int id;
	private String name;
	private Post post;
	private Customer customer;
	private Boolean active;
	private TargetPlace targetPlace;

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return customer.getFullName() + "/" + name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TargetPlace getTargetPlace() {
		return targetPlace;
	}

	public void setTargetPlace(TargetPlace targetPlace) {
		this.targetPlace = targetPlace;
	}
}