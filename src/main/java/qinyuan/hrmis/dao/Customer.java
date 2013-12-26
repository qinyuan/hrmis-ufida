package qinyuan.hrmis.dao;

public class Customer {

	private int id;
	private String name;
	private Integer superior;

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

	public String getFullName() {
		return CustomerDao.getFullCusName(id);
	}

	public Integer getSuperior() {
		return superior;
	}

	public void setSuperior(Integer superior) {
		this.superior = superior;
	}

	@Override
	public String toString() {
		return getName();
	}
}
