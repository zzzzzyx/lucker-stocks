package cn.edu.nju.luckers.luckers_stocks.ui.common.commonui;

import javafx.beans.property.SimpleStringProperty;

public class Stock {

	private SimpleStringProperty date;
	private SimpleStringProperty open;
	private SimpleStringProperty high;
	private SimpleStringProperty low;
	private SimpleStringProperty close;
	private SimpleStringProperty adj;
	private SimpleStringProperty volume;
	private SimpleStringProperty turnover;
	private SimpleStringProperty pe;
	private SimpleStringProperty pb;

	public Stock(String date, String open, String high, String low, String close, String adj, String volume,
			String turnover, String pe, String pb) {

		this.date= new SimpleStringProperty(date);
		this.open= new SimpleStringProperty(open);
		this.high= new SimpleStringProperty(high);
		this.low=  new SimpleStringProperty(low);
		this.close= new SimpleStringProperty(close);
		this.adj=new SimpleStringProperty(adj);
		this.volume= new SimpleStringProperty(volume);
		this.turnover = new SimpleStringProperty(turnover);
		this.pe= new SimpleStringProperty(pe);
		this.pb = new SimpleStringProperty(pb);
	}

	public String getDate() {
		return date.getValue();
	}

	public void setDate(SimpleStringProperty date) {
		this.date = date;
	}

	public String getOpen() {
		return open.getValue();
	}

	public void setOpen(SimpleStringProperty open) {
		this.open = open;
	}

	public String getHigh() {
		return high.getValue();
	}

	public void setHigh(SimpleStringProperty high) {
		this.high = high;
	}

	public String getLow() {
		return low.getValue();
	}

	public void setLow(SimpleStringProperty low) {
		this.low = low;
	}

	public String getClose() {
		return close.getValue();
	}

	public void setClose(SimpleStringProperty close) {
		this.close = close;
	}

	public String getAdj() {
		return adj.getValue();
	}

	public void setAdj(SimpleStringProperty adj) {
		this.adj = adj;
	}

	public String getVolume() {
		return volume.getValue();
	}

	public void setVolume(SimpleStringProperty volume) {
		this.volume = volume;
	}

	public String getTurnover() {
		return turnover.getValue();
	}

	public void setTurnover(SimpleStringProperty turnover) {
		this.turnover = turnover;
	}

	public String getPe() {
		return pe.getValue();
	}

	public void setPe(SimpleStringProperty pe) {
		this.pe = pe;
	}

	public String getPb() {
		return pb.getValue();
	}

	public void setPb(SimpleStringProperty pb) {
		this.pb = pb;
	}

	
	
}
