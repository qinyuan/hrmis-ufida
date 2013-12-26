package qinyuan.hrmis.domain.resume.source;

import java.sql.SQLException;

import qinyuan.hrmis.dao.Source;
import qinyuan.hrmis.dao.SourceDao;
import qinyuan.hrmis.domain.data.HRMIS;
import qinyuan.hrmis.lib.db.MyConn;

public class SourceMapDao {

	public static SourceMap[] getInstances() {
		Source[] sources = SourceDao.getInstances();
		SourceMap[] sms = new SourceMap[sources.length];
		for (int i = 0; i < sources.length; i++) {
			sms[i] = getInstance(sources[i].getId());
		}
		return sms;
	}

	public static SourceMap getInstance(int sourceId) {
		SourceMap sm = new SourceMap(sourceId);
		try (MyConn cnn = new MyConn()) {
			cnn.prepare("SELECT * FROM rec_source_map WHERE source_id=?")
					.setInt(1, sourceId).execute();
			while (cnn.next()) {
				sm.add(cnn.getString("source_selector"),
						cnn.getString("target_selector"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sm;
	}

	public static void delete(int sourceMapId) throws SQLException {
		HRMIS.execute("DELETE FROM rec_source_map WHERE source_map_id=" + sourceMapId);
	}

	public static void add(int sourceId, String sourceSelector,
			String tagetSelector, String remark) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare("INSERT INTO rec_source_map"
					+ "(source_id,source_selector,target_selector,remark) "
					+ "VALUES(?,?,?,?)");
			cnn.setInt(1, sourceId).setString(2, sourceSelector)
					.setString(3, tagetSelector).setString(4, remark).execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void edit(int id, int sourceId, String sourceSelector,
			String targetSelector, String remark) throws SQLException {
		try (MyConn cnn = new MyConn()) {
			cnn.prepare("UPDATE rec_source_map SET "
					+ "source_id=?,source_selector=?,target_selector=?,remark=? "
					+ "WHERE source_map_id=?");
			cnn.setInt(1, sourceId).setString(2, sourceSelector)
					.setString(3, targetSelector).setString(4, remark)
					.setInt(5, id).execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) {
		System.out.print(getInstance(2).toJsObject());
	}
}
