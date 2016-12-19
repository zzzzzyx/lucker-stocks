package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.DataScreenService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockScreenVO;
/**
 * @description 筛选功能接口的实现
 * @author Mzc
 * @date 2016/03/11
 */
public class DataScreenServiceImpl implements DataScreenService {
	
	StockDetailService detailService;
	
	public DataScreenServiceImpl(StockDetailService detailService) {
		this.detailService = detailService;
	}
	

	@Override
	public Iterator<StockInformVO> getChosen(String id, String startDate, String endDate,ArrayList<StockScreenVO> voList ) {

		//datas 包含了不同日期的数据条目
	    Iterator<StockInformVO> vos = detailService.getDetails(id, startDate, endDate);
	    ArrayList<StockInformVO> datas = new ArrayList<>();
	    while(vos.hasNext()){
	    	StockInformVO vo = vos.next();
	    	datas.add(vo);
	    }

	    //返回的链表
	    ArrayList<StockInformVO> results = new ArrayList<>();
	    
	    boolean mark = true;
	    
	    for(int i=0;i<datas.size();i++){
	    	mark = true;
	    	StockInformVO inform =datas.get(i);
	    	for(int j=0;j<voList.size();j++){
	    		StockScreenVO screener = voList.get(j);
	    		double number = Double.valueOf(inform.getItem(screener.getKey()));
	    		if(screener.getMin().equals("")&&screener.getMax().equals("")){
	    			continue;
	    		}else if(screener.getMax().equals("")){
	    			if(number>=Double.valueOf(screener.getMin())){
	    				
	    			}else{
	    				mark = false;
	    				break;
	    			}
	    		}else if(screener.getMin().equals("")){
	    			if(number<=Double.valueOf(screener.getMax())){
	    				mark = true;
	    			}else{
	    				mark = false;
	    				break;
	    			}
	    		}else{
	    			if(number>=Double.valueOf(screener.getMin()) && number<=Double.valueOf(screener.getMax())){
			    	}else{
			    		mark = false;
			    		break;
			    	}
	    		}
		    	
	    	}
	    	if(mark==true){
	    		results.add(inform);
	    	}
	    }
	    
		return results.iterator();
	}

}
