package cn.edu.nju.luckers.database_server.impl.strategy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import cn.edu.nju.luckers.database_server.impl.login.UserSearcher;
import cn.edu.nju.luckers.database_server.util.HibernateUtil;

public class StrategyController extends UnicastRemoteObject implements StrategyService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6224958254482461319L;

	public StrategyController() throws RemoteException {
		super();
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean createStrategy(UserIdentity user, Strategy s) throws RemoteException{
		Long userid = UserSearcher.getID(user);
		
        s.setUserid(userid);
        HibernateUtil.saveObject(s);

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Strategy> getStrategy(UserIdentity user) throws RemoteException {

		Long userid = UserSearcher.getID(user);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Strategy as s where s.userid = :userid");
        query.setLong("userid", userid);
		List<Strategy> result = query.list();
		
		tx.commit();
		session.close();
		
		if(result == null || result.size() < 1)
			return null;
		
		return result;
	}

	@Override
	public boolean deleteStrategy(UserIdentity user, Long strategyid) throws RemoteException {
		Long userid = UserSearcher.getID(user);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		String deleteStr = "delete Strategy f where f.userid = :userid and f.id = :id";
		session.createQuery(deleteStr)
		.setLong("userid", userid)
		.setLong("id", strategyid)
		.executeUpdate();
		
		tx.commit();
		session.close();
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Strategy getOneStrategy(UserIdentity user, Long strategyid) throws RemoteException {
		Long userid = UserSearcher.getID(user);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Strategy as s where s.userid = :userid and "
				+ "s.id = :id");
        query.setLong("userid", userid);
        query.setLong("id", strategyid);
		List<Strategy> result = query.list();
		
		tx.commit();
		session.close();
		
		if(result == null || result.size() < 1)
			return null;
		
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Strategy> getAllStrategy() throws RemoteException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Strategy");
		List<Strategy> result = query.list();
		
		tx.commit();
		session.close();
		
		if(result == null || result.size() < 1)
			return null;
		
		return result;
	}

}
