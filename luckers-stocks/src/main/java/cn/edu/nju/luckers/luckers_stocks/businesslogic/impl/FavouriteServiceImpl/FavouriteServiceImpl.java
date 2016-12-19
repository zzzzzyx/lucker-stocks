package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.FavouriteServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.impl.*;
import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.service.*;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * @description 收藏夹功能接口的实现
 * @author Mzc
 * @date 2016/3/7
 */
public class FavouriteServiceImpl implements FavouriteService {

	StringStorageService dataService;// 本地储存的服务接口
	ArrayList<String> favourList;// 本地储存的收藏名单
	StockDetailService detailService;

	public FavouriteServiceImpl(StockDetailService detailService) {
		// 初始化服务接口
		try {
			dataService = new StringStorageManager(StringStorageService.FAVORITES_FILE_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			this.favourList = dataService.getContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.detailService = detailService;
	}

	public ArrayList<String> getList() {
		try {
			this.favourList = dataService.getContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return favourList;
	}

	@Override
	public Iterator<StockInformVO> GetContent() {
		ArrayList<StockInformVO> voList = new ArrayList<>();

		try {
			favourList = dataService.getContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 测试最近日期
		int testNum = 1;
		while (true) {
				Iterator<StockInformVO> vos = detailService.getDetails(favourList.get(0), MyCalendar.getDayByNum(testNum),
						MyCalendar.getToday());
				
				if(vos == null||!vos.hasNext()){
					testNum++;
				}else {
					break;
				}
		}

		
		for (int i = 0; i < favourList.size(); i++) {
			StockInformVO vo = null;

			Iterator<StockInformVO> vos = detailService.getDetails(favourList.get(i), MyCalendar.getDayByNum(testNum),
					MyCalendar.getToday());

			if(vos == null)
				continue;
			
			if (vos.hasNext()) {
				vo = vos.next();
			}

			voList.add(vo);

		}
		return voList.iterator();
	}

	@Override
	public boolean add(String Id) {
		// 检查Id是否已经存在于收藏夹中
		try {
			favourList = dataService.getContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (favourList.contains(Id)) {
			return false;
		} // 如果已经存在于收藏夹中，返回false;
		
		//检查该股票是否正确
		Iterator<StockInformVO> vos = detailService.getDetails(Id,MyCalendar.getDayByNum(5),
				MyCalendar.getToday());
		
		if(vos ==null){
			//股票代号错误
			return false;
		}

		try {
			dataService.addString(Id);
		} catch (IOException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean remove(String Id) {
		// TODO Auto-generated method stub
		try {
			dataService.deleteString(Id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
