package cn.edu.nju.luckers.database_server.impl.favorites;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_server.impl.login.UserSearcher;
import cn.edu.nju.luckers.database_server.po.Favorite;
import cn.edu.nju.luckers.database_server.util.HibernateUtil;

@SuppressWarnings("unchecked")


public class FavoriteController extends UnicastRemoteObject implements FavoriteService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7319056999259340366L;
	
	private static final ArrayList<String> default_stock_list = new ArrayList<String>();
	static{
		default_stock_list.add("sz300182");
		default_stock_list.add("sh600588");
		default_stock_list.add("sz000839");
		default_stock_list.add("sh603000");
		default_stock_list.add("sz300104");
//		default_stock_list.add("sh600000");
//		default_stock_list.add("sh600005");
//		default_stock_list.add("sh600006");
//		default_stock_list.add("sh600010");
//		default_stock_list.add("sh600016");
//		default_stock_list.add("sh600017");
//		default_stock_list.add("sh600018");
//		default_stock_list.add("sh600019");
//		default_stock_list.add("sh600021");
//		default_stock_list.add("sh600022");
//		default_stock_list.add("sz300024");
//		default_stock_list.add("sz300251");
//		default_stock_list.add("sz300431");
	}

	public FavoriteController() throws RemoteException {
		super();
	}

	@Override
	public boolean deleteStock(UserIdentity user, String stockCode) throws RemoteException {
		Long userid = UserSearcher.getID(user);
		if(!thereHasBeenARecord(userid, stockCode)){
			return false;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		String deleteStr = "delete Favorite f where f.userid = :userid and f.favoriteStock = :stockCode";
		session.createQuery(deleteStr)
		.setLong("userid", userid)
		.setString("stockCode", stockCode)
		.executeUpdate();
		
		tx.commit();
		session.close();
		
		return true;
	}

	@Override
	public boolean addStock(UserIdentity user, String stockCode) throws RemoteException {
		Long userid = UserSearcher.getID(user);
		if(thereHasBeenARecord(userid, stockCode)){
			return false;
		}
		
        Favorite theEvent = new Favorite(userid, stockCode);//创建bean对象
        HibernateUtil.saveObject(theEvent);

		return true;
	}

	@Override
	public ArrayList<String> getUserFavorites(UserIdentity user) throws RemoteException {
		
		Long userid = UserSearcher.getID(user);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Favorite as f where f.userid = :userid");
        query.setLong("userid", userid);
		List<Favorite> result = query.list();
		
		tx.commit();
		session.close();
		
		if(result == null || result.size() < 1)
			return null;
		
		ArrayList<String> stockCodeList = new ArrayList<>();
		for(Favorite f : result){
			stockCodeList.add(f.getFavoriteStock());
		}
		
		return stockCodeList;
	}
	

	public static void initialUser(Long userid){
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();//开始事务
        
        for(String s : default_stock_list){
	        Favorite theEvent = new Favorite(userid, s);//创建bean对象
	        session.save(theEvent);//保存对象
        }

        tx.commit();//提交事务
        session.close();
	}
	
	public static void deleteUser(Long userid){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		String deleteStr = "delete Favorite f where f.userid = :userid";
		session.createQuery(deleteStr)
		.setLong("userid", userid)
		.executeUpdate();
		
		tx.commit();
		session.close();
	}
	
	
	private boolean thereHasBeenARecord(Long userid, String stockCode){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Favorite as f where f.userid = :userid and f.favoriteStock = :fS");
        query.setLong("userid", userid);
        query.setString("fS", stockCode);
		List<Favorite> result = query.list();
		
        tx.commit();
        session.close();
        
        if(result == null || result.size() < 1){
        	return false;
        }
        else {
        	return true;
        }
	}

	@Override
	public List<String> getAllStocks() throws RemoteException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("select distinct f.favoriteStock from Favorite f");
		List<String> result = query.list();
		
		tx.commit();
		session.close();
		
		if(result == null || result.size() < 1)
			return null;
		
		return result;
	}

}
