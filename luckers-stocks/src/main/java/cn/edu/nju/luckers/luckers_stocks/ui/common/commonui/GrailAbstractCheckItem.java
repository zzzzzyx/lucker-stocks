package cn.edu.nju.luckers.luckers_stocks.ui.common.commonui;

import cn.edu.nju.luckers.luckers_stocks.ui.common.AbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketDetailVO;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketInformVO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class GrailAbstractCheckItem extends AbstractCheckItem<MarketInformVO> {

	public GrailAbstractCheckItem(MarketInformVO vo) {
		this.vo = new SimpleObjectProperty<>(vo);
		this.selected = new SimpleBooleanProperty(false);
		selected.addListener((observable, oldValue, newValue) -> {
			System.out.println("...");
		});
	}

	@Override
	public String toString() {
		MarketInformVO vo = this.vo.getValue();

		return vo.getAdj() + vo.getClose() + vo.getDate() + vo.getHigh() + vo.getLow() + vo.getOpen() + vo.getVolume();
	}

}
