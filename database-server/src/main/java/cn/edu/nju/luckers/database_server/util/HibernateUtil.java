package cn.edu.nju.luckers.database_server.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	// 得到SessionFactory
	public static SessionFactory getSessionFactory() {
		return sessionFactory;// 返回SessionFactory的对象
	}
	
	public static void saveObject(Object o){
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();//开始事务
        
        session.save(o);//保存对象

        tx.commit();//提交事务
        session.close();
	}
	public static void deleteObject(Object o){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		session.delete(o);
		
		tx.commit();
		session.close();
	}
}
