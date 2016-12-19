package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.Observable;
import java.util.Observer;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;

public class ExceptionTest {
	
	public static void main(String[] args){
		ExceptionReporter ex = ExceptionReporter.getInstance();
		ExOb ob = new ExOb();
		ex.addObserver(ob);
		
	
		ex.setException("hhhhhh");
		
	}

}

class ExOb implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		
		System.out.println("接收到的异常是："+((ExceptionReporter) o).getExceptionInform());
	}
	
}