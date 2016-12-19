package cn.edu.nju.luckers.calculate_center.vo;

public class MarketCompareVO {

	 private String date;
	 private String market;
	 private String stock;
	
	public MarketCompareVO(String date,String market,String stock){
		this.date = date;
		this.market = market;
		this.stock = stock;
	}

	public String getDate() {
		return date;
	}

	public String getMarket() {
		return market;
	}

	public String getStock() {
		return stock;
	}
	
}
