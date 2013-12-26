package qinyuan.lib.db;

import java.lang.reflect.Array;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HConn implements AutoCloseable {

	private Session sess;
	private Transaction trans;

	public HConn() {
		sess = HibernateUtil.getSession();
		trans = sess.beginTransaction();
	}

	public void delete(Class<?> type, int id) {
		Object object = get(type, id);
		sess.delete(object);
	}

	public boolean exists(Class<?> type, int id) {
		return get(type, id) != null;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type, int id) {
		return (T) sess.get(type, id);
	}

	public <T> List<T> getList(String query, Class<T> type) {
		Query q = sess.createQuery(query);
		@SuppressWarnings("unchecked")
		List<T> list = q.list();
		return list;
	}

	public <T> T[] getArray(String query, Class<T> type) {
		List<T> list = getList(query, type);
		@SuppressWarnings("unchecked")
		T[] t = (T[]) Array.newInstance(type, list.size());
		list.toArray(t);
		return t;
	}

	public static void main(String[] args) {
	}

	public void save(Object object) {
		sess.save(object);
	}

	public void update(Object object) {
		sess.update(object);
	}

	@Override
	public void close() {
		trans.commit();
		sess.close();
	}

	public static void deleteOne(Class<?> type, int id) {
		HConn cnn = new HConn();
		cnn.delete(type, id);
		cnn.close();
	}

	public static boolean existsOne(Class<?> type, int id) {
		HConn cnn = new HConn();
		boolean bool = cnn.exists(type, id);
		cnn.close();
		return bool;
	}

	public static <T> T getOne(Class<T> type, int id) {
		HConn cnn = new HConn();
		T t = (T) cnn.get(type, id);
		cnn.close();
		return t;
	}

	public static <T> List<T> getOneList(String query, Class<T> type) {
		HConn cnn = new HConn();
		List<T> list = cnn.getList(query, type);
		cnn.close();
		return list;
	}

	public static <T> T[] getOneArray(String query, Class<T> type) {
		HConn cnn = new HConn();
		T[] arr = cnn.getArray(query, type);
		cnn.close();
		return arr;
	}

	public static void saveOne(Object object) {
		HConn cnn = new HConn();
		cnn.save(object);
		cnn.close();
	}

	public static void updateOne(Object object) {
		HConn cnn = new HConn();
		cnn.update(object);
		cnn.close();
	}
}