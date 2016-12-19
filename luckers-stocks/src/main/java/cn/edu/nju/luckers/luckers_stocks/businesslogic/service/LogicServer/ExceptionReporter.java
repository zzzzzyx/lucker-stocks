package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer;

import java.io.IOException;
import java.util.Observable;

import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

/**
 * 当发生异常时，这个类会发生改变
 * @author NjuMzc
 *
 */
public class ExceptionReporter extends Observable {
	String ExceptionInform;//异常信息
	boolean isOK;
	public static ExceptionReporter Singlation = null;
	
	private ExceptionReporter(){
		this.ExceptionInform = "";
		this.isOK = true;
	}
	
	public static ExceptionReporter getInstance(){
		if(Singlation == null){
			Singlation = new ExceptionReporter();
		}
		
		return Singlation;
	}
	

	
	public String getExceptionInform(){
		
		return ExceptionInform;
	}
	
	public boolean isOk(){
		return isOK;
	}
	
	//调用这个方法就说明发生了异常
	public void setException(String inform){
		System.out.println("Exception Happen!");
		if(isOK == false){
			//do nothing
			return;
		}else{
			ExceptionInform = inform;
			isOK = false;
			this.setChanged();
			this.notifyObservers();
		}
		
	}

	/**
	 * 此方法检查网络连接是否修复
	 */
	public boolean InternetRepairTest(){
		StockDataGetter service =LogicController.getInstance().getDataGetter();
		
		try {
			service.getIndustryName("sz002084");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			// 没修复好
			return false;
			
		}
		isOK = true;
		return true;
	}

	
}
