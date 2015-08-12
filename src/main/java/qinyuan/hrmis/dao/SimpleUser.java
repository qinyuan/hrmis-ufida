package qinyuan.hrmis.dao;

public class SimpleUser {

	private int id;
	private String name;
	private String password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return getName();
	}

	public Integer getSuperior() {
		return superior;
	}

	public void setSuperior(Integer superior) {
		this.superior = superior;
	}
}
