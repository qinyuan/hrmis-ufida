package qinyuan.lib.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	private static SessionFactory getSessionFactory() {
		Configuration con = new Configuration().configure();
		ServiceRegistryBuilder regbulider = new ServiceRegistryBuilder()
				.applySettings(con.getProperties());
		ServiceRegistry reg = regbulider.buildServiceRegistry();
		sessionFactory = con.buildSessionFactory(reg);
		return sessionFactory;
	}

	static Session getSession() {
		if (sessionFactory == null) {
			sessionFactory = getSessionFactory();
		}
		return sessionFactory.openSession();
	}
}