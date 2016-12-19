package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

/**
 * @description 该类提供股票的额外信息的计算
 * @author mzc19
 * @date 2016/03/23
 */
public class StockCalculator {

	String id;
	String date;
	StockDataGetter stockGetter;
	StockPO po;

	public StockCalculator(String id, String date) {
		this.id = id;
		this.date = date;
		stockGetter = LogicController.getInstance().getDataGetter();
		try {
			this.po = stockGetter.getStockByID(id, MyCalendar.getDayByNum(date, 7), MyCalendar.getTomorrow(date), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			ExceptionReporter exr = ExceptionReporter.getInstance();
			exr.setException("Internet Connect Failure");
			System.out.println(exr.getExceptionInform());
			
			e.printStackTrace();
		}
	}

	private StockDataPO getOneDay(String id, String date) {
		StockDataPO data = null;
		ArrayList<StockDataPO> datas = po.getDayList();
		if (datas.size() == 0) {
			return null;
		}
		for(int i =0;i<datas.size();i++){
			data = datas.get(i);
			if(data.getDate().equals(date)){
				return data;
			}else{
				continue;
			}
		}
		return data;
	}

	/**
	 * 输入股票代号和日期，返回当天的涨跌幅
	 * 
	 * @param id
	 * @return 保留到小数点后四位
	 */
	public String calculateRf() {

		StockDataPO thatDay = getOneDay(id, date);
		if (thatDay == null) {
			return null;
		}
		int counter = 1;
		StockDataPO yesDay = null;

		do {
			yesDay = getOneDay(id, MyCalendar.getDayByNum(date, counter));
			counter++;
		} while (yesDay == null);

		double rf = (Double.valueOf(thatDay.getDataMap().get("close"))
				- Double.valueOf(yesDay.getDataMap().get("close"))) / Double.valueOf(yesDay.getDataMap().get("close"));
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		rf *= 100;

		return df.format(rf) + "%";
	}

	/**
	 * 计算股票均值的方法 计算公式公式是取该股票从选定日期往前的选定天数收盘价之和除以天数 注意！！！！！！！！！
	 * 如果输入的日期当天没有数据，该方法不会检查； 请调用者先行检查输入日期是否有数据，否则会获得错误的数据
	 * 
	 * @param id
	 *            股票代号
	 * @param date
	 *            目标日期
	 * @param days
	 *            均值包含的日期天数
	 * @return 该股票当天的均值
	 * @exception 如果id，date，days任意一个输入错误，则返回null
	 */
	public String calculateAverage(int days) {

		double sum = 0;
		int counter = 0;
		int dayNum = 0;
		while (counter < days) {
			StockDataPO po = getOneDay(id, MyCalendar.getDayByNum(date, dayNum));
			if (po == null) {
				dayNum++;
				continue;
			} else {
				sum += Double.valueOf(po.getDataMap().get("close"));
				counter++;
				dayNum++;
			}
		}
		double answer = sum / days;
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

		return df.format(answer);
	}
}
