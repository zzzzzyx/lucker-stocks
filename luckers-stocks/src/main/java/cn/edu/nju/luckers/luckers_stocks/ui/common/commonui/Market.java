package cn.edu.nju.luckers.luckers_stocks.ui.common.commonui;

import javafx.beans.property.SimpleStringProperty;

public class Market {

	private SimpleStringProperty volumn;
	private SimpleStringProperty high;
	private SimpleStringProperty adj;
	private SimpleStringProperty low;
	private SimpleStringProperty date;
	private SimpleStringProperty open;
	private SimpleStringProperty close;

	public Market(String volumn, String high, String adj, String low, String date, String open, String close) {

		this.adj = new SimpleStringProperty(adj);
		this.date = new SimpleStringProperty(date);
		this.close = new SimpleStringProperty(close);
		this.high = new SimpleStringProperty(high);
		this.low = new SimpleStringProperty(low);
		this.open = new SimpleStringProperty(open);
		this.volumn = new SimpleStringProperty(volumn);
	}

	@Override
	public String toString() {
		return this.date.getValue() + this.volumn.getValue() + this.open.getValue() + this.close.getValue()
				+ this.high.getValue() + this.low.getValue() + this.adj.getValue() ;
	}

	public String getVolumn() {
		return volumn.getValue();
	}

	public void setVolumn(SimpleStringProperty volumn) {
		this.volumn = volumn;
	}

	public String getHigh() {
		return high.getValue();
	}

	public void setHigh(SimpleStringProperty high) {
		this.high = high;
	}

	public String getAdj() {
		return adj.getValue();
	}

	public void setAdj(SimpleStringProperty adj) {
		this.adj = adj;
	}

	public String getLow() {
		return low.getValue();
	}

	public void setLow(SimpleStringProperty low) {
		this.low = low;
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

	public String getClose() {
		return close.getValue();
	}

	public void setClose(SimpleStringProperty close) {
		this.close = close;
	}

}
