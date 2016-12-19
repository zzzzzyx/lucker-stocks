package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ScoreCalServiceImpl.StockScoreServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class SrockScoreTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       StockScoreServiceImpl service = new StockScoreServiceImpl(LogicController.getInstance().getDetailServer());
       
       StockDataGetter dataGetter = LogicController.getInstance().getDataGetter();
       
       ArrayList<String> nameList = null;
       
       StockDetailService detail = LogicController.getInstance().getDetailServer();

//       int a = service.calProspect("sz000959");
       
//       System.out.println(a);
       
       
       
//      下面的代码是进行换手率统计计算用的
//       int pes[] = new int[5]; //市盈率统计
//       int pbs[] = new int[101]; //市净率统计
       
       int answer[] = new int[101];
       
       
       try {
		nameList = dataGetter.getStockIDList(null, null);
	} catch (IOException | ConnectionFailure e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
       
       
       for(int i =0;i<nameList.size();i++){
    	   int temp = service.calConsistency(nameList.get(i), MyCalendar.getDayByNum(40),MyCalendar.getToday());
    	   if(temp<0){
    		   answer[100]++;
    	   }else{
    		   answer[temp]++;
    	   }
    	   
       }
       int sum =0;
       for(int i =0;i<answer.length;i++){
    	   sum+=answer[i];
       }
       
       for(int i =0;i<answer.length;i++){
    	   System.out.println("第"+i+"档的数量是"+answer[i]+" 占据比例有:"+ (double)answer[i]/sum);
       }
       
       
//       for(int i=0;i<nameList.size();i++){
//    	   StockInformVO vo=detail.getOneDay(nameList.get(i), "2016-04-08");
//    	   if(vo != null){
//    		   System.out.println("市盈率："+vo.getPe()+"市净率："+ vo.getPb());
//    		   double data =Double.valueOf(vo.getPe());
//    		   if(data>28){
//    			   pes[3]++;
//    		   }else if(data > 21){
//    			   pes[2]++;
//    		   }else if(data > 14){
//    			   pes[1]++;
//    		   }else if(data > 0){
//    			   pes[0]++;
//    		   }else{
//    			   pes[4]++;
//    		   }
//    		   int index = (int)(data * 10);
//    		   if(index >= 101){
//    			   num[100] ++;
//    		   }else{
//    			   num[index]++;
//    		   }
//    		  
//    	   }
//    	    
//       }
       
//       int sum = 0;
//       
//       for(int i=0;i<5;i++){
//    	   System.out.println("换手率范围在第"+i+"档之间有 "+pes[i]);
//       }
//       System.out.println(nameList.size());
      
       
       
	}

}
