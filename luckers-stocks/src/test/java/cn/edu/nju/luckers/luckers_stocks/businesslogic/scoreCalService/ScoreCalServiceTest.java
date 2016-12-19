//package cn.edu.nju.luckers.luckers_stocks.businesslogic.scoreCalService;
//
//import static org.junit.Assert.*;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import org.junit.*;
//
//import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DetailSeriveImpl;
//import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ScoreCalServiceImpl.StockScoreServiceImpl;
//import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
//import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ScoreCalService.StockScoreService;
//import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
//import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
//
//public class ScoreCalServiceTest {
//
//	StockScoreService scoreService;
//	StockDataGetter dataGetter;
//	StockDetailService y;
//	
//	
//	
//	@Before
//	public void initialize(){
//		dataGetter = new StockBufferReader();
//		y = new DetailSeriveImpl(dataGetter);
//		scoreService = new StockScoreServiceImpl(y);
//	}
//	s
//	@Test
//	public void testScore() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//		Method[] methodList = StockScoreService.class.getMethods();
//		
//		for(Method scoreMethod : methodList){
//			System.out.println(System.currentTimeMillis());
//			System.out.println(scoreMethod.getName());
//			if(scoreMethod.getName() == "calConsistency"){
//				continue;
//			}
//			scoreMethod.invoke(scoreService, "sh600588", "2015-03-21", "2015-05-04");
//		}
//	}
//}
