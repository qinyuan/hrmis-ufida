package qinyuan.hrmis.domain.demand;

import java.sql.SQLException;
import qinyuan.hrmis.dao.SimpleDemand;
import qinyuan.hrmis.dao.SimpleDemandFactory;
import qinyuan.hrmis.lib.db.MyConn;

public class DemandListOfResume {

	private int postId;
	private int targetPlaceId;

	public DemandListOfResume(int resumeId) {
		try (MyConn cnn = new MyConn()) {
			cnn.execute("SELECT post_id,target_place_id FROM rec_resume WHERE resume_id="
					+ resumeId);
			cnn.next();
			postId = cnn.getInt("post_id");
			targetPlaceId = cnn.getInt("target_place_id");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		SimpleDemandFactory sdf = new SimpleDemandFactory();
		sdf.setActive(true);
		sdf.setPostId(postId);
		if (targetPlaceId > 1) {
			sdf.setTargetPlaceId(targetPlaceId);
		}

		s.append("<ul>");
		for (SimpleDemand sd : sdf.getInstances()) {
			s.append("<li><a href='#cus" + sd.getId() + "'>" + sd.getFullName()
					+ "</a></li>");
		}
		s.append("</ul>");
		return s.toString();
	}

	public static void main(String[] args) {
		System.out.println(new DemandListOfResume(2));
	}
}
