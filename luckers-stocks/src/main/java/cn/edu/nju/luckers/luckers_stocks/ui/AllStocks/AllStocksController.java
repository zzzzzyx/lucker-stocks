package cn.edu.nju.luckers.luckers_stocks.ui.AllStocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ListService.ListDelayService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.main.Main;
import cn.edu.nju.luckers.luckers_stocks.ui.Favorites.FavoritesController;
import cn.edu.nju.luckers.luckers_stocks.ui.StockDetails.StockDetailsController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.mainPaneController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.StockAbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class AllStocksController {
	public TableView<StockAbstractCheckItem> stocks_TableView;
	public TableColumn<StockAbstractCheckItem, StockAbstractCheckItem> check_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> ID_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> name_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> open_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> high_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> low_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> close_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> adjprice_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> volume_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> turnover_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> pe_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> pb_TableColumn;

	private int num = 0;
	private String stockId;
	private String name;
	private ListDelayService listDelayService = LogicController.getInstance().getListServer(null);
	private FavouriteService favouriteService = LogicController.getInstance().getFavouriteServer();

	mainPaneController MainPaneController = mainPaneController.getInstance();
	private List<StockAbstractCheckItem> stocks = new ArrayList<StockAbstractCheckItem>();

	private List<String> stocksWithoutName = new ArrayList<String>();
	StockAbstractCheckItem stockVO = new StockAbstractCheckItem(null);

	public static Parent launch() throws IOException {
		FXMLLoader loader = new FXMLLoader(AllStocksController.class.getResource("AllStocks.fxml"));
		Pane pane = loader.load();
		return pane;
	}

	public void initialize() throws IOException {
		num = 20;
		Iterator<StockInformVO> voList = listDelayService.getItems(null, num);
		Iterator<String> voListWithName = listDelayService.getIdList(null);
		while (voList.hasNext()) {
			StockInformVO vo = voList.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			stocks.add(stock);
		}
		while (voListWithName.hasNext()) {
			String vo = voListWithName.next();
			stocksWithoutName.add(vo);
		}

		stocks_TableView.setItems(FXCollections.observableArrayList(stocks));
		ID_TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getID()));
		name_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getName()));
		open_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getOpen()));
		high_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getHigh()));
		low_TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getLow()));
		close_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getClose()));
		adjprice_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getAdjprice()));
		volume_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getVolume()));
		turnover_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getTurnover()));
		pb_TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getPb()));
		pe_TableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getPe()));

		check_TableColumn.setCellFactory(o -> new MyTableCell());
		check_TableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

		stocks_TableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				System.out.println("selected empty");
			} else {
				stockId = newValue.getVo().getID();
				name = newValue.getVo().getName();
				System.out.println("selected " + stockId);

			}

		}

		);

		stocks_TableView.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					MainPaneController.getInstance().content_Pane.getChildren().clear();
					try {

						StockDetailsController.id = stockId;
						StockDetailsController.name = name;
						MainPaneController.getInstance().content_Pane.getChildren()
								.add((new StockDetailsController()).launch(true));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	@FXML
	public void add(ActionEvent actionEvent) {

		System.out.println("add is beginning!");

		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).getSelected()) {
				// stocks.remove(stocks.get(i));
				ObservableList<StockAbstractCheckItem> list = stocks_TableView.getItems();
				System.out.println(list.get(i).getVo().getID() + "to add");
				favouriteService.add(list.get(i).getVo().getID());
				list.add(list.get(i));
				System.out.println(list.add(list.get(i)));

				// --i;
				FavoritesController.getInstance().delete(null);
			}
		}
	}

	private class MyTableCell extends TableCell<StockAbstractCheckItem, StockAbstractCheckItem> {
		@Override
		protected void updateItem(StockAbstractCheckItem item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
				setGraphic(null);
				return;
			}
			CheckBox checkBox = new CheckBox();
			checkBox.selectedProperty().bindBidirectional(item.selectedProperty());
			checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
				int row = stocks_TableView.getItems().indexOf(item);
			});
			setGraphic(checkBox);
		}
	}

}
