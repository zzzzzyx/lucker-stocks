package cn.edu.nju.luckers.database_getter_interface.service.strategy.po;

import java.io.Serializable;

public class Strategy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1990815042449845295L;
	
	/**
	 * 用于数据库存储的id，无需手动设置,用于 【唯一】标识一条数据，即primary key
	 */
	private Long id;
	
	/**
	 * 用户的id，在数据库端动态添加
	 */
	private Long userid; // 关联到某个用户的标志
	
	private String stockName;
	
	private String strategyName;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getStrategyName() {
		return strategyName;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	private String make_date; // 制定的日期
	private String apply_start; // 应用的开始时间
	private String apply_end; // 应用的结束时间
	private double benefit; // 收益
	private String stockId;

	

	@Override
	public String toString() {
		return "Strategy stockName=" + stockName + ", strategyName="
				+ strategyName + ", make_date=" + make_date + ", apply_start=" + apply_start + ", apply_end="
				+ apply_end + ", benefit=" + benefit + ", stockId=" + stockId + ", price_in_low=" + price_in_low
				+ ", price_in_high=" + price_in_high + ", volume_in_low=" + volume_in_low + ", volume_in_high="
				+ volume_in_high + ", turnover_in_low=" + turnover_in_low + ", turnover_in_high=" + turnover_in_high
				+ ", pe_in_low=" + pe_in_low + ", pe_in_high=" + pe_in_high + ", pb_in_low=" + pb_in_low
				+ ", pb_in_high=" + pb_in_high + ", price_out_low=" + price_out_low + ", price_out_high="
				+ price_out_high + ", volume_out_low=" + volume_out_low + ", volume_out_high=" + volume_out_high
				+ ", turnover_out_low=" + turnover_out_low + ", turnover_out_high=" + turnover_out_high
				+ ", pe_out_low=" + pe_out_low + ", pe_out_high=" + pe_out_high + ", pb_out_low=" + pb_out_low
				+ ", pb_out_high=" + pb_out_high + "]";
	}

	// 属性条件的过滤分为买入和卖出
	private double price_in_low;
	private double price_in_high;
	private double volume_in_low;
	private double volume_in_high;
	private double turnover_in_low;
	private double turnover_in_high;
	private double pe_in_low;
	private double pe_in_high;
	private double pb_in_low;
	private double pb_in_high;
	private double price_out_low;
	private double price_out_high;
	private double volume_out_low;
	private double volume_out_high;
	private double turnover_out_low;
	private double turnover_out_high;
	private double pe_out_low;
	private double pe_out_high;
	private double pb_out_low;
	private double pb_out_high;
	
	public Strategy(String stockId,String make_date, String apply_start, String apply_end, double benefit,
			double price_in_low, double price_in_high, double volume_in_low, double volume_in_high, double turnover_in_low,
			double turnover_in_high, double pe_in_low, double pe_in_high, double pb_in_low, double pb_in_high,
			double price_out_low, double price_out_high, double volume_out_low, double volume_out_high,
			double turnover_out_low, double turnover_out_high, double pe_out_low, double pe_out_high, double pb_out_low,
			double pb_out_high, String strategyName, String stockName, String userName) {
		super();
		this.userName = userName;
		this.strategyName = strategyName;
		this.stockName = stockName;
		this.stockId=stockId;
		this.make_date = make_date;
		this.apply_start = apply_start;
		this.apply_end = apply_end;
		this.benefit = benefit;
		this.price_in_low = price_in_low;
		this.price_in_high = price_in_high;
		this.volume_in_low = volume_in_low;
		this.volume_in_high = volume_in_high;
		this.turnover_in_low = turnover_in_low;
		this.turnover_in_high = turnover_in_high;
		this.pe_in_low = pe_in_low;
		this.pe_in_high = pe_in_high;
		this.pb_in_low = pb_in_low;
		this.pb_in_high = pb_in_high;
		this.price_out_low = price_out_low;
		this.price_out_high = price_out_high;
		this.volume_out_low = volume_out_low;
		this.volume_out_high = volume_out_high;
		this.turnover_out_low = turnover_out_low;
		this.turnover_out_high = turnover_out_high;
		this.pe_out_low = pe_out_low;
		this.pe_out_high = pe_out_high;
		this.pb_out_low = pb_out_low;
		this.pb_out_high = pb_out_high;
	}
	public Long getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getMake_date() {
		return make_date;
	}
	public void setMake_date(String make_date) {
		this.make_date = make_date;
	}
	public String getApply_start() {
		return apply_start;
	}
	public void setApply_start(String apply_start) {
		this.apply_start = apply_start;
	}
	public String getApply_end() {
		return apply_end;
	}
	public void setApply_end(String apply_end) {
		this.apply_end = apply_end;
	}
	public double getBenefit() {
		return benefit;
	}
	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}
	public double getPrice_in_low() {
		return price_in_low;
	}
	public void setPrice_in_low(double price_in_low) {
		this.price_in_low = price_in_low;
	}
	public double getPrice_in_high() {
		return price_in_high;
	}
	public void setPrice_in_high(double price_in_high) {
		this.price_in_high = price_in_high;
	}
	public double getVolume_in_low() {
		return volume_in_low;
	}
	public void setVolume_in_low(double volume_in_low) {
		this.volume_in_low = volume_in_low;
	}
	public double getVolume_in_high() {
		return volume_in_high;
	}
	public void setVolume_in_high(double volume_in_high) {
		this.volume_in_high = volume_in_high;
	}
	public double getTurnover_in_low() {
		return turnover_in_low;
	}
	public void setTurnover_in_low(double turnover_in_low) {
		this.turnover_in_low = turnover_in_low;
	}
	public double getTurnover_in_high() {
		return turnover_in_high;
	}
	public void setTurnover_in_high(double turnover_in_high) {
		this.turnover_in_high = turnover_in_high;
	}
	public double getPe_in_low() {
		return pe_in_low;
	}
	public void setPe_in_low(double pe_in_low) {
		this.pe_in_low = pe_in_low;
	}
	public double getPe_in_high() {
		return pe_in_high;
	}
	public void setPe_in_high(double pe_in_high) {
		this.pe_in_high = pe_in_high;
	}
	public double getPb_in_low() {
		return pb_in_low;
	}
	public void setPb_in_low(double pb_in_low) {
		this.pb_in_low = pb_in_low;
	}
	public double getPb_in_high() {
		return pb_in_high;
	}
	public void setPb_in_high(double pb_in_high) {
		this.pb_in_high = pb_in_high;
	}
	public double getPrice_out_low() {
		return price_out_low;
	}
	public void setPrice_out_low(double price_out_low) {
		this.price_out_low = price_out_low;
	}
	public double getPrice_out_high() {
		return price_out_high;
	}
	public void setPrice_out_high(double price_out_high) {
		this.price_out_high = price_out_high;
	}
	public double getVolume_out_low() {
		return volume_out_low;
	}
	public void setVolume_out_low(double volume_out_low) {
		this.volume_out_low = volume_out_low;
	}
	public double getVolume_out_high() {
		return volume_out_high;
	}
	public void setVolume_out_high(double volume_out_high) {
		this.volume_out_high = volume_out_high;
	}
	public double getTurnover_out_low() {
		return turnover_out_low;
	}
	public void setTurnover_out_low(double turnover_out_low) {
		this.turnover_out_low = turnover_out_low;
	}
	public double getTurnover_out_high() {
		return turnover_out_high;
	}
	public void setTurnover_out_high(double turnover_out_high) {
		this.turnover_out_high = turnover_out_high;
	}
	public double getPe_out_low() {
		return pe_out_low;
	}
	public void setPe_out_low(double pe_out_low) {
		this.pe_out_low = pe_out_low;
	}
	public double getPe_out_high() {
		return pe_out_high;
	}
	public void setPe_out_high(double pe_out_high) {
		this.pe_out_high = pe_out_high;
	}
	public double getPb_out_low() {
		return pb_out_low;
	}
	public void setPb_out_low(double pb_out_low) {
		this.pb_out_low = pb_out_low;
	}
	public double getPb_out_high() {
		return pb_out_high;
	}
	public void setPb_out_high(double pb_out_high) {
		this.pb_out_high = pb_out_high;
	}

	public Strategy(){}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

}
