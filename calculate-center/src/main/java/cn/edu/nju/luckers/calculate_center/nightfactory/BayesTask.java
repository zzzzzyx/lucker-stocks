package cn.edu.nju.luckers.calculate_center.nightfactory;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Vector;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.global.DataKey;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.calculate_center.ml.Bayes;
import cn.edu.nju.luckers.calculate_center.vo.StockInformVO;
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;

public class BayesTask extends TimerTask {

	private FavoriteService favoriteService;
	private StockInformService stockInformService;
	private Vector<String> forecastResult = new Vector<String>();
	private List<String> listOfId;
	private Bayes bayes;
	private static BayesTask bayesTask;

	private BayesTask() {
		super();
		stockInformService = LogicController.getInformService();
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

	public static BayesTask getInstance() {
		if (bayesTask == null) {
			bayesTask = new BayesTask();
		}
		return bayesTask;
	}

	public void oneForecast(String id) {
		ArrayList<StockInformVO> stockInformVO = stockInformService.getInform(id, MyCalendar.getDayByNum(100),
				MyCalendar.getToday());

		try {

			// 训练样本
			double data[][] = new double[stockInformVO.size() - 1][10];
			for (int i = 0; i < stockInformVO.size() - 1; i++) {
				data[i][0] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.open));// 开盘价
				data[i][1] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.close));// 收盘价
				data[i][2] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.high));// 最高价
				data[i][3] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.low));// 最低价
				data[i][4] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.volume));// 成交量
				data[i][5] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.turnover));// 换手率
				data[i][6] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.pb));// 市盈率
				data[i][7] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.pe_ttm));// 市净率
				data[i][8] = Double.parseDouble(stockInformVO.get(i).getItem(DataKey.rf));// 涨跌幅

				if (Double.parseDouble(stockInformVO.get(i + 1).getItem(DataKey.rf)) > 0) {
					data[i][9] = 1;// 类别
				} else {
					data[i][9] = 0;
				}
			}
			// 预测数据
			double testData[] = new double[9];
			int len = stockInformVO.size();
			testData[0] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.open));
			testData[1] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.close));
			testData[2] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.high));
			testData[3] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.low));
			testData[4] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.volume));
			testData[5] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.turnover));
			testData[6] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.pb));
			testData[7] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.pe_ttm));
			testData[8] = Double.parseDouble(stockInformVO.get(len - 1).getItem(DataKey.rf));

			// 跑预测算法
			bayes = new Bayes(data, testData);
			double testResult = bayes.BayesClassify();
			if (testResult > 0) {
				forecastResult.add("看涨");
			} else {
				forecastResult.add("看跌");
			}

		} catch (NegativeArraySizeException e) {
			forecastResult.add("观望");
		}

	}

	@Override
	public void run() {
		try {
			listOfId = favoriteService.getAllStocks();
		} catch (Exception e) {

		}

		for (int j = 0; j < listOfId.size(); j++) {
			oneForecast(listOfId.get(j));
		}
	}

	public String getForecast(String target) {
		String forecast = null;
		int i;
		for ( i = 0; i < listOfId.size(); i++) {
			if (target.equals(listOfId.get(i))) {
				forecast = forecastResult.get(i);
				break;
			}
		}

		return forecast;
	}

}
