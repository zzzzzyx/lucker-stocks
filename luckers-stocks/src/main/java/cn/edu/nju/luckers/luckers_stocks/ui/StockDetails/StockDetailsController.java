package cn.edu.nju.luckers.luckers_stocks.ui.StockDetails;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.ls.LSInput;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.DataScreenService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.ui.CandleStickChart.CandleStickChartController;
import cn.edu.nju.luckers.luckers_stocks.ui.Favorites.FavoritesController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.mainPaneController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.StockAbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockScreenVO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class StockDetailsController {

	public TableView<StockAbstractCheckItem> stocks_TableView;
	public TableColumn<StockAbstractCheckItem, String> date_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> open_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> high_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> low_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> close_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> adjprice_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> volume_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> turnover_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> pe_TableColumn;
	public TableColumn<StockAbstractCheckItem, String> pb_TableColumn;

	public Button back_Button;
	public Button filt;
	public Button K_Button;
	public Label idAndName;
	public TextField open_start;
	public TextField open_end;
	public TextField high_start;
	public TextField high_end;
	public TextField low_start;
	public TextField low_end;
	public TextField close_start;
	public TextField close_end;
	public TextField adj_start;
	public TextField adj_end;
	public TextField volumn_start;
	public TextField volumn_end;
	public TextField turnover_start;
	public TextField turnover_end;
	public TextField pe_start;
	public TextField pe_end;
	public TextField pb_start;
	public TextField pb_end;
	public Label remind;

	public DatePicker start_DatePicker;
	public DatePicker end_DatePicker;
	private String startStr;
	private String endStr;

	private DataScreenService dataScreenService = LogicController.getInstance().getDataScreenServer();
	private StockDetailService stockDetailService = LogicController.getInstance().getDetailServer();

	public static String name = "力合股份";
	public static String id = "sz000532";
	private List<StockAbstractCheckItem> stocks = new ArrayList<StockAbstractCheckItem>();
	private KeyCode[] listOfKey;
	private static boolean able;
	private mainPaneController MainPaneController = mainPaneController.getInstance();

	
	public static Parent launch(boolean able) throws IOException {
		FXMLLoader loader = new FXMLLoader(StockDetailsController.class.getResource("Details.fxml"));
		StockDetailsController stockDetailsController = loader.getController();
		stockDetailsController.able = able;
		Pane pane = loader.load();
		
		return pane;
	}

	public void initialize() {
		setFavorite();
		if(!able){
			disable();
		}else{
		Iterator<StockInformVO> voList = stockDetailService.getDetails(id);
		while (voList.hasNext()) {
			StockInformVO vo = voList.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			stocks.add(stock);
		}
 

		stocks_TableView.setItems(FXCollections.observableArrayList(stocks));
		date_TableColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVo().getDate()));
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
		
		
		startStr=MyCalendar.getDayByNum(30);
		endStr=MyCalendar.getToday();
		String[] splite=startStr.split("-");
			
		start_DatePicker.setValue(LocalDate.of(Integer.parseInt(splite[0]),Integer.parseInt(splite[1]),Integer.parseInt(splite[2])));
		end_DatePicker.setValue(LocalDate.now());
		idAndName.setText(name);
		}
	}
	public void setFavorite(){
		MainPaneController.setFavorite(2);
	}
	
	public void filter(ActionEvent actionEvent) {

		startStr = start_DatePicker.getValue().toString();
		endStr = end_DatePicker.getValue().toString();

		String open_start_string = open_start.getText();
		String open_end_string = open_end.getText();
		String high_start_string = high_start.getText();
		String high_end_string = high_end.getText();
		String low_start_string = low_start.getText();
		String low_end_string = low_end.getText();
		String close_start_string = close_start.getText();
		String close_end_string = close_end.getText();
		String adj_start_string = adj_start.getText();
		String adj_end_string = adj_end.getText();
		String volumn_start_string = volumn_start.getText();
		String volumn_end_string = volumn_end.getText();
		String turnover_start_string = turnover_start.getText();
		String turnover_end_string = turnover_end.getText();
		String pe_start_string = pe_start.getText();
		String pe_end_string = pe_end.getText();
		String pb_start_string = pb_start.getText();
		String pb_end_string = pb_end.getText();

		Iterator<StockInformVO> voList = null;
		StockScreenVO voOpen = new StockScreenVO(DataKey.open, open_start_string, open_end_string);
		StockScreenVO voClose = new StockScreenVO(DataKey.close, close_start_string, close_end_string);
		StockScreenVO voHigh = new StockScreenVO(DataKey.high, high_start_string, high_end_string);
		StockScreenVO voLow = new StockScreenVO(DataKey.low, low_start_string, low_end_string);
		StockScreenVO voadj = new StockScreenVO(DataKey.adj_price, adj_start_string, adj_end_string);
		StockScreenVO voVolumn = new StockScreenVO(DataKey.volume, volumn_start_string, volumn_end_string);
		StockScreenVO voTurnover = new StockScreenVO(DataKey.turnover, turnover_start_string, turnover_end_string);
		StockScreenVO voPe = new StockScreenVO(DataKey.pe_ttm, pe_start_string, pe_end_string);
		StockScreenVO voPb = new StockScreenVO(DataKey.pb, pb_start_string, pb_end_string);
		ArrayList<StockScreenVO> stockScreenVO = new ArrayList<>();
		stockScreenVO.add(voPe);
		stockScreenVO.add(voTurnover);
		stockScreenVO.add(voClose);
		stockScreenVO.add(voOpen);
		stockScreenVO.add(voPb);
		stockScreenVO.add(voVolumn);
		stockScreenVO.add(voadj);
		stockScreenVO.add(voLow);
		stockScreenVO.add(voHigh);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDf = null;
		Date endDf = null;
		Date todayDf = null;
		try {
			startDf = df.parse(startStr);
			endDf = df.parse(endStr);
			todayDf = df.parse(MyCalendar.getToday());
		} catch (Exception e) {

		}

		if (endDf.getTime() > todayDf.getTime()) {
			remindMessage("结束日期最晚为今天");
		} else if (startDf.getTime() > endDf.getTime()) {
			remindMessage("开始应早于结束日期");
		} else {
			
			try {
				voList = dataScreenService.getChosen(id, startStr, endStr, stockScreenVO);

				stocks.clear();
				while (voList.hasNext()) {
					StockInformVO vo = voList.next();
					StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
					stocks.add(stock);
				}
				stocks_TableView.setItems(FXCollections.observableArrayList(stocks));
			} catch (NumberFormatException e) {
			  remindMessage("输入的格式有误");
			}
			 
		}

	}

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

	public void jumpBack(ActionEvent actionEvent) {
		try {
			mainPaneController.getInstance().content_Pane.getChildren().clear();
			mainPaneController.getInstance().content_Pane.getChildren().add((new FavoritesController()).launch(true));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jumpToK(ActionEvent actionEvent) {
		mainPaneController.getInstance().content_Pane.getChildren().clear();

		try {

			CandleStickChartController.id = id;
			CandleStickChartController.name = name;

			mainPaneController.getInstance().content_Pane.getChildren().add(CandleStickChartController.launch(true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disable() {

		start_DatePicker.setDisable(true);
		end_DatePicker.setDisable(true);
		filt.setDisable(true);
		back_Button.setDisable(true);
		K_Button.setDisable(true);

	}
}
