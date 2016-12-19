package cn.edu.nju.luckers.luckers_stocks.ui.Grails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ListService.ListDelayService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketInformService;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.GrailAbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.StockAbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketInformVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class GrailController {

	public TableView<GrailAbstractCheckItem> grail_TableView;
	public TableColumn<GrailAbstractCheckItem, String> name_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> volumn_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> high_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> low_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> adj_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> open_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> close_TableColumn;
	public TableColumn<GrailAbstractCheckItem, String> date_TableColumn;

	private ListDelayService listDelayService = LogicController.getInstance().getListServer(null);
	private MarketInformService marketInformService = LogicController.getInstance().getMarketInformServer();

	private List<GrailAbstractCheckItem> grail = new ArrayList<GrailAbstractCheckItem>();
	GrailAbstractCheckItem grailVo = new GrailAbstractCheckItem(null);

	public  Parent launch() throws IOException {
		FXMLLoader loader = new FXMLLoader(GrailController.class.getResource("Grail.fxml"));
		Pane pane = loader.load();

		return pane;
	}

	public void initialize() throws IOException {

		MarketInformVO vo = marketInformService.getItems("hs300");
		GrailAbstractCheckItem g = new GrailAbstractCheckItem(vo);
		grail.add(g);

		grail_TableView.setItems(FXCollections.observableArrayList(grail));

		date_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getDate()));
		name_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getName()));
		open_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getOpen()));
		high_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getHigh()));
		low_TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getLow()));
		close_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getClose()));
		adj_TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getAdj()));
		volumn_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getVolume()));

		grail_TableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				System.out.println("selected empty");
			} else {
				System.out.println("here");
			}
		});

	}
}
