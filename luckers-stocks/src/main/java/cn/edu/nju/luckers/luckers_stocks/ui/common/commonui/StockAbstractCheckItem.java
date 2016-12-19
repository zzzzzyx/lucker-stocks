package cn.edu.nju.luckers.luckers_stocks.ui.common.commonui;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.IndustryService.IndustryInformService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.AbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.VPos;

public class StockAbstractCheckItem extends AbstractCheckItem<StockInformVO> {

	private IndustryInformService industryInformService = LogicController.getInstance().getIndustryServer();
	
	 public StockAbstractCheckItem(StockInformVO vo){
        this.vo = new SimpleObjectProperty<>(vo);
        this.selected = new SimpleBooleanProperty(false);
        selected.addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println(vo.getID() + " change to " + newValue.booleanValue());
                }
        );
    }

    @Override
    public String toString() {
    	StockInformVO vo = this.vo.getValue();
    	
        return vo.getID()+vo.getName()+vo.getOpen()+vo.getHigh()+vo.getLow()+vo.getClose()
           +vo.getAdjprice()+vo.getVolume()+vo.getTurnover()+vo.getPe()+vo.getPb();
    }
    
    public String getIndustry(){
    	
    	String industry = industryInformService.getIndustryByStock(vo.get().getID());
    	return industry;
    	}
    
}
