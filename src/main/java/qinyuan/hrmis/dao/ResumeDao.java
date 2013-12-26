package qinyuan.hrmis.dao;

import java.sql.SQLException;
import java.util.List;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.db.HConn;

public class ResumeDao {

	public static String getApplicantById(int resumeId) throws SQLException {
		return getFieldById(resumeId, "applicant");
	}

	private static String getFieldById(int resumeId, String fieldName)
			throws SQLException {
		return HRMIS.getString("rec_resume", "resume_id", resumeId, fieldName);
	}

	public static String getContentById(int resumeId) throws SQLException {
		return getFieldById(resumeId, "content");
	}

	public static int getStatusIdById(int resumeId) throws SQLException {
		String query = "SELECT MAX(status_id) FROM "
				+ "rec_rec_step AS s INNER JOIN rec_recommend AS r "
				+ "ON r.recommend_id=s.recommend_id AND resume_id=" + resumeId;
		return HRMIS.getInt(query);
	}

	private static Resume[] getInstancesByWhereClause(String whereClause) {
		String query = "FROM Resume";
		if (whereClause.length() > 0) {
			query += " WHERE " + whereClause;
		}
		List<Resume> list = HConn.getOneList(query, Resume.class);
		Resume[] rs = new Resume[list.size()];
		list.toArray(rs);
		return rs;
	}

	public static Resume getInstance(int resumeId) {
		return HConn.getOne(Resume.class, resumeId);
	}

	public static Resume[] getInstances(String startDate, String endDate,
			int recruiterId) {
		StringBuilder s = new StringBuilder();
		if (MyDate.isDate(startDate)) {
			s.append("DATE(addTime)>='" + startDate + "'");
		}

		if (MyDate.isDate(endDate)) {
			if (s.length() > 0) {
				s.append(" AND ");
			}
			s.append("DATE(addTime)<='" + endDate + "'");
		}

		if (recruiterId > 0) {
			if (s.length() > 0) {
				s.append(" AND ");
			}
			s.append("creator.id=" + recruiterId);
		}
		return getInstancesByWhereClause(s.toString());
	}

	public static void add(int userId, String applicant, String company,
			int postId, String tel, String email, String qq, String resumeNo,
			String resumeLink, int sourceId, String intention,
			double experience, String expectSalary, String jhReason,
			String education, String skill, String prevJob, String prevProj,
			String other, String content, boolean intentionRed,
			int targetPlaceId, boolean downloaded, int genderId)
			throws SQLException {
		Resume r = new Resume();

		r.setApplicant(applicant);

		String currentTime = new MyDateTime().toString();
		r.setAddTime(currentTime);
		r.setMdfTime(currentTime);

		SimpleUser su = SimpleUserDao.getInstance(userId);
		r.setCreator(su);
		r.setTracer(su);

		r.setCompany(company);
		r.setPost(PostDao.getInstance(postId));
		r.setTel(tel);
		r.setEmail(email);
		r.setQq(qq);
		r.setSource(SimpleSourceDao.getInstance(sourceId));
		r.setResumeNo(resumeNo);
		r.setResumeLink(resumeLink);
		r.setExperience(experience);
		r.setExpectSalary(expectSalary);
		r.setIntention(intention);
		r.setJhReason(jhReason);
		r.setEducation(education);
		r.setSkill(skill);
		r.setPrevJob(prevJob);
		r.setPrevProj(prevProj);
		r.setOther(other);
		r.setContent(content);
		r.setIntentionRed(intentionRed);
		r.setTargetPlace(TargetPlaceDao.getInstance(targetPlaceId));
		r.setDownloaded(downloaded);
		r.setGender(GenderDao.getInstance(genderId));

		HConn.saveOne(r);
	}

	public static void mdfContent(int resumeId, String content)
			throws SQLException {
		try (MyConn cnn = new MyConn()) {
			String query = "UPDATE rec_resume SET content=?,mdf_time=NOW() WHERE resume_id="
					+ resumeId;
			cnn.prepare(query).setString(1, content).execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void mdf(int resumeId, String addTime, String applicant,
			String company, int postId, String tel, String email, String qq,
			String resumeNo, String resumeLink, int sourceId, String intention,
			double experience, String expectSalary, String jhReason,
			String education, String skill, String prevJob, String prevProj,
			String other, boolean intentionRed, int targetPlaceId,
			boolean downloaded, int genderId) throws SQLException {
		Resume r = ResumeDao.getInstance(resumeId);

		r.setMdfTime(new MyDateTime().toString());
		r.setApplicant(applicant);
		r.setCompany(company);
		r.setPost(PostDao.getInstance(postId));
		r.setTel(tel);
		r.setEmail(email);
		r.setQq(qq);
		r.setResumeNo(resumeNo);
		r.setResumeLink(resumeLink);
		r.setSource(SimpleSourceDao.getInstance(sourceId));
		r.setIntention(intention);
		r.setExperience(experience);
		r.setExpectSalary(expectSalary);
		r.setJhReason(jhReason);
		r.setEducation(education);
		r.setSkill(skill);
		r.setPrevJob(prevJob);
		r.setPrevProj(prevProj);
		r.setOther(other);
		r.setIntentionRed(intentionRed);
		r.setAddTime(addTime);
		r.setIntentionRed(intentionRed);
		r.setTargetPlace(TargetPlaceDao.getInstance(targetPlaceId));
		r.setDownloaded(downloaded);
		r.setGender(GenderDao.getInstance(genderId));

		HConn.updateOne(r);
	}

	public static boolean isRecommended(int resumeId) throws SQLException {
		return HRMIS.exists("FROM rec_recommend WHERE resume_id=" + resumeId);
	}

	public static void del(int resumeId) throws Exception {
		if (!isRecommended(resumeId))
			HRMIS.execute("DELETE FROM rec_resume WHERE resume_id=" + resumeId);
	}

	public static boolean existsTel(String tel) throws SQLException {
		return HRMIS.exists("FROM rec_resume WHERE tel='" + tel + "'");
	}

	public static void recommend(int resumeId, int demandId) throws Exception {
		try (MyConn cnn = new MyConn()) {
			cnn.execute("SELECT MAX(recommend_id) FROM rec_recommend");
			cnn.next();
			int recommendId = cnn.getInt(1) + 1;
			cnn.setAutoCommit(false);
			cnn.prepare(
					"INSERT INTO rec_recommend(recommend_id,resume_id,demand_id) "
							+ "VALUES(?,?,?)").setInt(1, recommendId)
					.setInt(2, resumeId).setInt(3, demandId).execute();
			cnn.prepare(
					"INSERT INTO rec_rec_step(recommend_id,status_id,deal_time) "
							+ "VALUES(?,1,?)").setInt(1, recommendId)
					.setString(2, new MyDateTime().toString()).execute();
			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
