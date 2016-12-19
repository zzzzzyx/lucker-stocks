package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;

public class EnumTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DataKey key = DataKey.valueOf("volume");
		
		System.out.println(key.getChinese());
	}

}
