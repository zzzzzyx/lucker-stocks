package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.FavouriteServiceImpl.FavouriteServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class FavouriteTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        FavouriteService service = LogicController.getInstance().getFavouriteServer();
        
        Iterator<StockInformVO> voList= service.GetContent();
        System.out.println(voList.next().getName());
        System.out.println(voList.next().getName());
        System.out.println(voList.next().getName());
	}

}
