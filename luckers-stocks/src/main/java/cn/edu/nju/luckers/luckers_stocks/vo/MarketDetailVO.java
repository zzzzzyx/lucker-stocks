package cn.edu.nju.luckers.luckers_stocks.vo;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
/**
 * @description 包含大盘指数详细信息的VO对象
 * @author rabook
 * @date 2016/3/8
 */
public class MarketDetailVO {

	//所有的信息列表
	private ArrayList<String> dateList;
	private ArrayList<String> OpenList;
	private ArrayList<String> HighList;
	private ArrayList<String> LowList;
	private ArrayList<String> CloseList;
	private ArrayList<String> VolumeList;
	private ArrayList<String> AdjList;

	
	public MarketDetailVO(StockPO po){
		//构造器待完成
		ArrayList<StockDataPO> datas = po.getDayList();
		
		dateList=new ArrayList<>();
		OpenList=new ArrayList<>();
		HighList=new ArrayList<>();
		LowList=new ArrayList<>();
		CloseList=new ArrayList<>();
		AdjList=new ArrayList<>();
		VolumeList=new ArrayList<>();
		
		for(int i=0;i<datas.size();i++){
			StockDataPO datapo= datas.get(i);
			Map<String, String> dataMap=datapo.getDataMap();
			dateList.add(datapo.getDate());
			OpenList.add((String)dataMap.get("open"));
			HighList.add((String)dataMap.get("high"));
			LowList.add((String)dataMap.get("low"));
			CloseList.add((String)dataMap.get("close"));
			AdjList.add((String)dataMap.get("adj_price"));
			VolumeList.add((String)dataMap.get("volume"));
		}
	}

	// Getters
	public ArrayList<String> getOpenList() {
		return OpenList;
	}

	public ArrayList<String> getHighList() {
		return HighList;
	}

	public ArrayList<String> getLowList() {
		return LowList;
	}

	public ArrayList<String> getCloseList() {
		return CloseList;
	}

	public ArrayList<String> getAdjList() {
		return AdjList;
	}

	public ArrayList<String> getVolumeList() {
		return VolumeList;
	}
	
	public ArrayList<String> getDateList(){
		return dateList;
	}
}
