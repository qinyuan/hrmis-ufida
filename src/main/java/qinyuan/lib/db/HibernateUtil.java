package qinyuan.lib.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static Configuration config = new Configuration().configure();
	private static SessionFactory sessionFactory = getSessionFactory();

	private static SessionFactory getSessionFactory() {
		ServiceRegistryBuilder regbulider = new ServiceRegistryBuilder()
				.applySettings(config.getProperties());
		ServiceRegistry reg = regbulider.buildServiceRegistry();
		sessionFactory = config.buildSessionFactory(reg);
		return sessionFactory;
	}

	static Session getSession() {
		return sessionFactory.openSession();
	}
	
	static String getUsername(){
		return config.getProperty("connection.username");
	}
	
	static String getPassword(){
		return config.getProperty("connection.password");
	}
	
	static String getUrl(){
		return config.getProperty("connection.url");
	}

	public static void main(String[] args) {
		System.out.println(getUsername());
		System.out.println(getPassword());
		System.out.println(getUrl());
	}
}