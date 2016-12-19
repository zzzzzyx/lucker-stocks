package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer;

import java.io.Serializable;

/**
 * @description 股票信息数据项目的枚举类
 * @author Mzc
 * @date 2016/03/11
 */
public enum DataKey implements Serializable ,EnumObservable<DataKey> {

	date("日期"),
	open("开盘价"),
	high("最高值"),
	low("最低值"),
	close("收盘价"),
	adj_price("后复权价"),
	volume("交易量"),
	turnover("换手率"),
	pe_ttm("市盈率"),
	pb("市净率"),
	rf("涨跌幅"),
	average_5("5日均值"),
	average_10("10日均值"),
	average_20("20日均值"),
	average_60("60日均值");
	

	private String chinese;
	
	DataKey  (String c){
		this.chinese=c;
	}
	
	@Override
	public String getChinese() {
		return chinese;
	}
	
}
