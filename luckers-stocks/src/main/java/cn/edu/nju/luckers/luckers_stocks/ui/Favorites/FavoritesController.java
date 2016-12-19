package cn.edu.nju.luckers.luckers_stocks.ui.Favorites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.ui.CandleStickChart.CandleStickChartController;
import cn.edu.nju.luckers.luckers_stocks.ui.StockDetails.StockDetailsController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.mainPaneController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.StockAbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FavoritesController {

	/**
	 * 列表以及表的各个列的声明
	 */

	private static FavoritesController instance;
	public TableView<StockAbstractCheckItem> stocks_TableView;
	public TableColumn<StockAbstractCheckItem, StockAbstractCheckItem> check_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> ID_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> name_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> career_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> open_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> high_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> low_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> close_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> adjprice_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> volume_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> turnover_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> pe_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> pb_TableColumn;
	public Button delete_Btn;
	public Button add_Btn;

	private List<String> numbers = new ArrayList<String>();
	// 收藏夹逻辑层接口
	private FavouriteService favouriteService = LogicController.getInstance().getFavouriteServer();
	// 股票的列表
	private List<StockAbstractCheckItem> stocks = new ArrayList<StockAbstractCheckItem>();
	StockAbstractCheckItem stockVO = new StockAbstractCheckItem(null);

	private mainPaneController MainPaneController = mainPaneController.getInstance();
	public TextField id_TextField;
	public Label remind;

	private String stockID;
	private String name;

	private static boolean able = false;

	public static Parent launch(boolean able) throws IOException {
		FXMLLoader loader = new FXMLLoader(FavoritesController.class.getResource("Favorites.fxml"));
		FavoritesController controller = loader.getController();

		controller.able = able;
		// try{
		// controller.able = able;
		// }catch(NullPointerException e){
		// System.out.println(" "+controller == null);
		// able = true;
		// }

		Pane pane = loader.load();

		instance = controller;
		// new Thread(controller).start();

		return pane;
	}

	// 错误异常提示
	private void remindMessage(String message) {
		Timer timer = new Timer(false);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				remind.setText(message);
			}
		});

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						remind.setText("");
						timer.cancel();
					}
				});
			}
		}, 1000);
	}

	// 初始化时读取收藏夹列表
	public void initialize() throws IOException {
		setFavorite();
		System.out.println("able is " + able);
		if (!able) {
			disable();
		} else {
			Iterator<StockInformVO> voList = favouriteService.GetContent();
			while (voList.hasNext()) {
				StockInformVO vo = voList.next();
				StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
				stocks.add(stock);
			}
			stocks_TableView.setItems(FXCollections.observableArrayList(stocks));

			ID_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getID()));
			name_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getName()));
			career_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndustry()));
			open_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getOpen()));
			high_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getHigh()));
			low_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getLow()));
			close_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getClose()));
			adjprice_TableColumn.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().getVo().getItem(DataKey.valueOf("rf"))));
			volume_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getVolume()));
			turnover_TableColumn.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().getVo().getTurnover()));
			pb_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getPb()));
			pe_TableColumn
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getPe()));

			check_TableColumn.setCellFactory(o -> new MyTableCell());
			check_TableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

			stocks_TableView.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> {
						if (newValue == null) {
							System.out.println("selected empty");
						} else {
							System.out.println("selected " + stockID);
							stockID = newValue.getVo().getID();
							name = newValue.getVo().getName();
						}
					});
			stocks_TableView.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						mainPaneController.getInstance().content_Pane.getChildren().clear();
						try {
							System.out.println("stockId is " + stockID);

							StockDetailsController.id = stockID;
							StockDetailsController.name = name;

							mainPaneController.getInstance().content_Pane.getChildren()
									.add(StockDetailsController.launch(true));

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}

	}

	public void setFavorite(){
		MainPaneController.setFavorite(1);
	}
	
	
	
	@FXML
	public void add(ActionEvent actionEvent) {

		boolean isCorrectId = favouriteService.add(id_TextField.getText());
		if (isCorrectId) {
			refreshItems(favouriteService.GetContent());
		} else {
			remindMessage("股票代码错误");
			id_TextField.setText("");
		}

	}

	@FXML
	public void delete(ActionEvent actionEvent) {
		// System.out.println("delete is beginning!");
		Iterator<StockInformVO> voList = favouriteService.GetContent();
		while (voList.hasNext()) {
			StockInformVO vo = voList.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			stocks.add(stock);
		}
		ObservableList<StockAbstractCheckItem> list = stocks_TableView.getItems();
		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).getSelected()) {
				// stocks.remove(stocks.get(i));

				System.out.println(list.get(i).getVo().getID() + "to delete");
				favouriteService.remove(list.get(i).getVo().getID());
				list.remove(list.get(i));
				// --i;
			}
		}
		refreshItems(favouriteService.GetContent());
	}

	private void refreshItems(Iterator<StockInformVO> stockInformVOs) {
		stocks.clear();
		while (stockInformVOs.hasNext()) {
			StockInformVO vo = stockInformVOs.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			stocks.add(stock);
		}
		stocks_TableView.getItems().clear();
		stocks_TableView.getItems().addAll(stocks);
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

	public static FavoritesController getInstance() {
		return instance;
	}

	public void disable() {
		delete_Btn.setDisable(true);
		add_Btn.setDisable(true);
	}

}
