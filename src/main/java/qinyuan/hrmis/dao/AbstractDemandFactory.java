package qinyuan.hrmis.dao;

public abstract class AbstractDemandFactory {

	private int postId;
	private int customerId;
	private int targetPlaceId;
	private boolean activeFilter;
	private boolean active;

	public AbstractDemandFactory setPostId(int postId) {
		this.postId = postId;
		return this;
	}

	public AbstractDemandFactory setCustomerId(int customerId) {
		this.customerId = customerId;
		return this;
	}

	public AbstractDemandFactory setActive(boolean active) {
		activeFilter = true;
		this.active = active;
		return this;
	}

	public AbstractDemandFactory setTargetPlaceId(int targetPlaceId) {
		this.targetPlaceId = targetPlaceId;
		return this;
	}

	public abstract Object[] getInstances();

	protected String getWhereClause() {
		StringBuilder s = new StringBuilder();
		if (postId > 0) {
			updateWhereClause(s);
			s.append("post.id=" + postId);
		}
		if (customerId > 0) {
			updateWhereClause(s);
			s.append("customer.id=" + customerId);
		}
		if (targetPlaceId > 0) {
			updateWhereClause(s);
			s.append("targetPlace.id=" + targetPlaceId);
		}
		if (activeFilter) {
			updateWhereClause(s);
			s.append("active=" + (active ? "TRUE" : "FALSE"));
		}
		return s.toString();
	}

	private static void updateWhereClause(StringBuilder s) {
		if (s.length() == 0) {
			s.append(" WHERE ");
		} else {
			s.append(" AND ");
		}
	}
}
