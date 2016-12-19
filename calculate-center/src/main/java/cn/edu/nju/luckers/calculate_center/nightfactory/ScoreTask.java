package cn.edu.nju.luckers.calculate_center.nightfactory;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.Vector;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.StockScoreServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockScoreService;
import cn.edu.nju.luckers.calculate_center.global.exception.HaltException;
import cn.edu.nju.luckers.calculate_center.vo.StockScoreVO;
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;

public class ScoreTask  extends TimerTask {

	private StockScoreService stockService;
	private FavoriteService favoriteService;
	private Vector<StockScoreVO> allScore;
	private List<String> listOfId;
	private static ScoreTask scoreTask = null;

	private ScoreTask() {
		super();
		stockService = new StockScoreServer();
		try {
			favoriteService = (FavoriteService) RMIService.getInstance(FavoriteService.class);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addOneStock(String id){
		try {
			allScore.add(stockService.getScore(id));
		} catch (HaltException e) { 
			 StockScoreVO voNull=new StockScoreVO();
			 allScore.add(voNull);
		}
	}

	public static ScoreTask getInstance() {
		if (scoreTask == null) {
			scoreTask = new ScoreTask();
		}
		return scoreTask;
	}

	@Override
	public void run() {
 
		try {
			listOfId = favoriteService.getAllStocks();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		allScore = new Vector<StockScoreVO>(listOfId.size());
		for (int i = 0; i < listOfId.size(); i++) {
				addOneStock(listOfId.get(i));
		}
	}

	
	public StockScoreVO getTargetStockVO(String target){
		StockScoreVO vo=null;

		 for(int i=0;i<listOfId.size();i++){
			 if(listOfId.get(i).equals(target)){
				 vo=allScore.get(i);
			 }
		 }
		 return vo;
	}
}
