package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ListServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ListService.ListDelayService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * 
 * @author Mzc
 * @date 2016/3/4
 */
public class ListDelayServiceImpl implements ListDelayService{

	StockDataGetter dataGetter;//网络服务接口
	int StockCounter;//股票计数器
	ArrayList<String> idList;//股票编号列表
	ArrayList<String> nameList;//股票名字列表
	int testNum;//能获得数据的最接近的天数
	
	//构造器
	public ListDelayServiceImpl(String exchanger,StockDataGetter dataGetter){
		this.dataGetter = dataGetter;//接口的实现
		idList = new ArrayList<String>();
		StockCounter=0;
		try {
			idList=dataGetter.getStockIDList(MyCalendar.getYear(), exchanger);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			// 断线异常处理
			e.printStackTrace();
		}
		//根据Id获得名字
		try {
			nameList = dataGetter.getStockName(idList);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ConnectionFailure e) {
			// 断线异常处理
			e.printStackTrace();
		}
		//先测试最近一天能获得哪一日的数据
		testNum=1;
		while(true){
			try {
				StockPO poTest;
				poTest = dataGetter.getStockByID(idList.get(0), MyCalendar.getDayByNum(testNum), MyCalendar.getToday(), null);
				if(poTest.getDayList().size()==0){
					testNum++;        
				}else{
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//测试完成
              catch (ConnectionFailure e) {
	          // 断线异常处理
				e.printStackTrace();
			}
		}
		
	}
	
    /**
     * @param num 一次拿的数据量
     * @return 返回一个vo的迭代器
     */
	public Iterator<StockInformVO> getItems(String exchanger,int num)  {
		// 一次获得num个股票的信息
		
		//返回的vo对象数组
		ArrayList<StockInformVO> voList = new ArrayList<StockInformVO>();
		int counter=0;
		while(counter<num){
			if(StockCounter==idList.size()){
				break;
			}
			String id=idList.get(StockCounter);
			StockPO po=null;
			try {
				po = dataGetter.getStockByID(id, MyCalendar.getDayByNum(testNum), MyCalendar.getToday(), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectionFailure e) {
				// 断线异常处理
				e.printStackTrace();
			}
			if(po==null) continue;
			StockDataPO data=po.getDayList().get(0);
			StockInformVO vo=new StockInformVO(id, nameList.get(StockCounter), data);
			voList.add(vo);
		    counter++;
		    StockCounter++;
		}
		
		
		return voList.iterator();
	}

	@Override
	public Iterator<String> getIdList(String exchanger) {
		// TODO Auto-generated method stub
		
		return idList.iterator();
	}

	
}
