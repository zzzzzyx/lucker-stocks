package cn.edu.nju.luckers.database_server.impl.login;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_server.po.User;
import cn.edu.nju.luckers.database_server.util.HibernateUtil;

@SuppressWarnings("rawtypes")
public class UserSearcher {

	public static Long getID(UserIdentity userIdentity){
		User user = searchByName(userIdentity.getUserName());
		Long l = new Long(user.getId());
		return l;
	}
	
	public static User searchByName(String name){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from User as u where u." + getType(name) + " = :name");
        query.setString("name", name);
		List result = query.list();
        tx.commit();
        session.close();
        if(result == null || result.size() < 1){
        	return null;
        }
        else {
        	return (User) result.get(0);
        }
	}
	
	public static String getType(String name){
		if(name.contains("@"))
			return "email";
		else
			return "username";
	}
}
