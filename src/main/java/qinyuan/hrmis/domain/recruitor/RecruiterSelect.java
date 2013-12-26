package qinyuan.hrmis.domain.recruitor;

import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.web.html.Select;

public class RecruiterSelect {

	private int recruiterId = -1;
	private boolean withTotal = true;

	public RecruiterSelect() {
		this(-1);
	}

	public RecruiterSelect(int recruiterId) {
		this.recruiterId = recruiterId;
	}

	public RecruiterSelect setTotal(boolean withTotal) {
		withTotal = true;
		return this;
	}

	@Override
	public String toString() {
		return toSelect().toString();
	}

	public Select toSelect() {
		try (MyConn cnn = new MyConn()) {
			Select select = new Select().setId("recruiterId");
			if (withTotal) {
				select.addOption(0, "（全部）");
			}
			String query = "SELECT * FROM u_user WHERE user_id IN "
					+ "(SELECT user_id FROM u_privilege WHERE pri_id=5 OR pri_id=6)";
			cnn.execute(query);
			while (cnn.next()) {
				select.addOption(cnn.getInt("user_id"), cnn.getString("name"));
			}
			select.select(recruiterId);
			return select;
		} catch (Exception e) {
			e.printStackTrace();
			return new Select();
		}
	}

	public static void main(String[] args) {
		System.out.println(new RecruiterSelect());
		System.out.println(new RecruiterSelect(1));
	}
}
