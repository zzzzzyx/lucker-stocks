package cn.edu.nju.luckers.database_server.impl.login;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cn.edu.nju.luckers.database_getter_interface.service.login.RegisterAndLoginService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserRegisterInfo;
import cn.edu.nju.luckers.database_server.impl.favorites.FavoriteController;
import cn.edu.nju.luckers.database_server.po.User;
import cn.edu.nju.luckers.database_server.util.HibernateUtil;

public class LoginController extends UnicastRemoteObject implements RegisterAndLoginService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7319056999259340366L;

	public LoginController() throws RemoteException {
		super();
	}

	@Override
	public boolean register(UserRegisterInfo userInfo) throws RemoteException{
		
		if(isUsernameOrEmailOccupied(userInfo.getEmail()) || isUsernameOrEmailOccupied(userInfo.getUserName())){
			return false;
		}
		
        User theEvent = new User(userInfo.getUserName(), userInfo.getEmail(), userInfo.getPassword());//创建bean对象

        HibernateUtil.saveObject(theEvent);
        
        Long id = UserSearcher.getID(new UserIdentity(userInfo.getEmail(), userInfo.getPassword()));
        FavoriteController.initialUser(id);

		return true;
	}

	@Override
	public boolean verify(UserIdentity userInfo) throws RemoteException {
		User user = UserSearcher.searchByName(userInfo.getUserName());
        if(user == null || !user.getEncryptedPassword().equals(userInfo.getPassword())){
        	return false;
        }
		return true;
	}

	@Override
	public boolean deleteUser(UserIdentity userInfo) throws RemoteException {
		if(!verify(userInfo)){
			return false;
		}
		
		User user = UserSearcher.searchByName(userInfo.getUserName());
		HibernateUtil.deleteObject(user);
		
		Long id = user.getId();
        FavoriteController.deleteUser(id);
		
		return true;
	}

	@Override
	public boolean isUsernameOrEmailOccupied(String name) throws RemoteException {

        User u = UserSearcher.searchByName(name);
        if(u == null)
        	return false;
        else 
        	return true;
        
	}

}
