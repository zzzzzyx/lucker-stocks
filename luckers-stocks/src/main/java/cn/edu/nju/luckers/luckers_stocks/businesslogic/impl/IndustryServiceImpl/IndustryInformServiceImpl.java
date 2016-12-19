package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.IndustryServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ScoreCalServiceImpl.IndustryScoreCalculator;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.IndustryService.IndustryInformService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryScoreVO;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryVO;
/**
 * 
 * @author NjuMzc
 *
 */
public class IndustryInformServiceImpl implements IndustryInformService {

	private IndustryManager manager;//行业信息记录类
	private FavouriteService favour;//收藏夹列表
	private StockDataGetter dataGetter;//网络数据接口
	private StockDetailService dataService;
	
	public IndustryInformServiceImpl(FavouriteService favour,StockDataGetter dataGetter,StockDetailService dataService) {
		manager = IndustryManager.getInstence();
		this.favour = favour;
		this.dataGetter = dataGetter;
		this.dataService = dataService;
		init();
	}
	
	/**
	 * 用本地收藏夹列表初始化行业信息记录
	 */
	private void init(){
		ArrayList<String> idList = favour.getList();
		
		for(int i=0;i<idList.size();i++){
			String id = idList.get(i);
			String indname = null;//行业名
			try {
				indname = dataGetter.getIndustryName(id);
			} catch (IOException | ConnectionFailure e) {
		
				e.printStackTrace();
			}
			
			//如果行业已经存在
			if(manager.contains(indname)){
				IndustryVO ind = manager.getIndustry(indname);
				ind.addStock(id);
				manager.updateIndustry(ind);
			}else{
				//不存在则新建行业,并且添加第一个股票名
				IndustryVO ind = new IndustryVO(indname);
				ind.addStock(id);
				manager.addIndustry(ind);
			}
		}
		
	}
	
	@Override
	public Iterator<IndustryVO> getIndustryList() {
		ArrayList<IndustryVO> voList = new ArrayList<>();
		Map<String,IndustryVO> map = manager.getIndustryList();
		
		Iterator it = map.entrySet().iterator();
		
		while(it.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			
			IndustryVO vo = (IndustryVO) entry.getValue();
			
			voList.add(vo);
			
		}
		
		return voList.iterator();
	}

	@Override
	public IndustryScoreVO getIndustryScore(String name, String start, String end) {
		IndustryScoreCalculator cal = new IndustryScoreCalculator(dataService);
		IndustryVO vo = manager.getIndustry(name);
		
		return cal.calculate(vo, start, end);
	}

	@Override
	public String getIndustryByStock(String id) {
		String name = null;
		try {
			name = dataGetter.getIndustryName(id);
		} catch (IOException | ConnectionFailure e) {
		
			ExceptionReporter exListener= ExceptionReporter.getInstance();
			exListener.setException("Internet Connect Failure");
			e.printStackTrace();
			return null;
		}
		
		return name;
	}

}
