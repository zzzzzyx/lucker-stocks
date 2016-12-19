package cn.edu.nju.luckers.calculate_center.vo;

public class StockImVO {

	private String time;
	private String price;
	private String average;
	private String volume;
	private String rf;
	
	public StockImVO(String time,String price,String average,String volume){
		this.time = time;
		this.price = price;
		this.average = average;
		this.volume = volume;
		this.rf = "0";
	}
	
	public void setRf(String rf){
		this.rf = rf;
	}

	public String getRf(){
		return rf;
	}
	
	public String getTime() {
		return time;
	}

	public String getPrice() {
		return price;
	}

	public String getAverage() {
		return average;
	}
	
	public String getVolume(){
		return volume;
	}
}
