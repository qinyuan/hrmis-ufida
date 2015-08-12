package qinyuan.hrmis.dao;

import qinyuan.lib.db.HConn;

public class SourceDao {

	public static Source getInstance(int sourceId) {
		return HConn.getOne(Source.class, sourceId);
	}

	public static void main(String[] args) {
		for (Source source : getInstances()) {
			System.out.println(source.getName());
		}
	}

	public static Source[] getInstances() {
		return HConn.getOneArray("FROM Source", Source.class);
	}

	public static void delete(int id) {
		HConn.deleteOne(Source.class, id);
	}

	public static void add(String sourceName, String idPattern,
			String hrefPrefix, String hrefSuffix, String sign,
			boolean downloaded) {
		Source s = new Source();
		s.setName(sourceName);
		s.setIdPattern(idPattern);
		s.setHrefPrefix(hrefPrefix);
		s.setHrefSuffix(hrefSuffix);
		s.setSign(sign);
		s.setDownloaded(downloaded);
		s.setDeletable(true);
		HConn.saveOne(s);
	}

	public static void mdf(int id, String name, String idPattern,
			String hrefPrefix, String hrefSuffix, String sign,
			boolean downloaded) {
		HConn cnn = new HConn();
		Source s = cnn.get(Source.class, id);
		s.setName(name);
		s.setIdPattern(idPattern);
		s.setHrefPrefix(hrefPrefix);
		s.setHrefSuffix(hrefSuffix);
		s.setSign(sign);
		s.setDownloaded(downloaded);
		cnn.update(s);
		cnn.close();
	}
}
