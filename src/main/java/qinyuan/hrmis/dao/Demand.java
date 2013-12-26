package qinyuan.hrmis.dao;

public class Demand {

	private int id;
	private String name;
	private Post post;
	private Customer customer;
	private String duty;
	private String qualification;
	private String startDate;
	private String endDate;
	private boolean active;
	private String salary;
	private Integer postNumber;
	private TargetPlace targetPlace;
	private boolean pause;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

	public String getFullCustomerName() {
		return CustomerDao.getFullCusName(customer.getId());
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isActive() {
		return active;
	}

	public String getDemandStatus() {
		if (active) {
			if (pause) {
				return "暂停";
			} else {
				return "开启";
			}
		} else {
			return "关闭";
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Integer getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(Integer postNumber) {
		this.postNumber = postNumber;
	}

	public TargetPlace getTargetPlace() {
		return targetPlace;
	}

	public void setTargetPlace(TargetPlace targetPlace) {
		this.targetPlace = targetPlace;
	}

	public int getPostId() {
		return post == null ? 0 : post.getId();
	}

	public String getPostName() {
		return post == null ? null : post.getName();
	}

	public int getCustomerId() {
		return customer == null ? 0 : customer.getId();
	}

	public String getCustomerName() {
		return customer == null ? null : customer.getName();
	}

	public int getTargetPlaceId() {
		return targetPlace.getId();
	}

	public Object getTargetPlaceName() {
		return targetPlace.getName();
	}

	@Override
	public String toString() {
		return getName();
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

}