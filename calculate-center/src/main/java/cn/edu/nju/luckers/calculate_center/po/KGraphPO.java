package cn.edu.nju.luckers.calculate_center.po;

public class KGraphPO {
	private String date;
	private String open;
	private String high;
	private String low;
	private String close;
	private String volume;
		
	public KGraphPO(String date,String open,String close,String high,String low,String volume){
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.volume = volume;
	}

	public String getDate() {
		return date;
	}

	public String getOpen() {
		return open;
	}

	public String getHigh() {
		return high;
	}

	public String getLow() {
		return low;
	}

	public String getClose() {
		return close;
	}

	public String getVolume() {
		return volume;
	}

}
