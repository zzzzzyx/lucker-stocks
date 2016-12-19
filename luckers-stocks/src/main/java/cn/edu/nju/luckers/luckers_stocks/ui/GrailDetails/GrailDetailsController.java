package cn.edu.nju.luckers.luckers_stocks.ui.GrailDetails;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketDetailService;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.Market;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class GrailDetailsController {

	public TableView<Market> grailDetails_Tableview;
	public TableColumn<Market, String> volumn_TableColumn;
	public TableColumn<Market, String> high_TableColumn;
	public TableColumn<Market, String> adj_TableColumn;
	public TableColumn<Market, String> low_TableColumn;
	public TableColumn<Market, String> date_TableColumn;
	public TableColumn<Market, String> open_TableColumn;
	public TableColumn<Market, String> close_TableColumn;
	public LineChart Kobe_LineChart;
	public Label remind;

	public DatePicker startDate;
	public DatePicker endDate;
	public Button search;
	private String startStr = MyCalendar.getDayByNum(30);
	private String endStr = MyCalendar.getToday();

	private MarketDetailService marketDetailService = LogicController.getInstance().getMarketDetailServer();
	private ObservableList<Market> marketList = FXCollections.observableArrayList();

	private ArrayList<String> dateList = new ArrayList<>();
	private ArrayList<String> OpenList = new ArrayList<>();
	private ArrayList<String> HighList = new ArrayList<>();
	private ArrayList<String> LowList = new ArrayList<>();
	private ArrayList<String> CloseList = new ArrayList<>();
	private ArrayList<String> VolumeList = new ArrayList<>();
	private ArrayList<String> AdjList = new ArrayList<>();
	int length;
	XYChart.Series<String, Number> series1;
	XYChart.Series<String, Number> series2;
	public NumberAxis yAxis;
	private static boolean able;
	
	public static Parent launch(boolean able) throws IOException {
		FXMLLoader loader = new FXMLLoader(GrailDetailsController.class.getResource("GrailDetails.fxml"));
		GrailDetailsController grailDetailsController = loader.getController();
		grailDetailsController.able=able;
		Pane pane = loader.load();
		
		return pane;
	}

	public void initialize() throws IOException {
		if(!able){
			disable();
		}else{
		String[] start_split = startStr.split("-");
		startDate.setValue(LocalDate.of(Integer.parseInt(start_split[0]), Integer.parseInt(start_split[1]),
				Integer.parseInt(start_split[2])));
		String[] end_splite = endStr.split("-");
		endDate.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
				Integer.parseInt(end_splite[2])));

		series1 = new XYChart.Series<String, Number>();
		series2 = new XYChart.Series<String, Number>();
		series1.setName("收盘点数");
		series2.setName("开盘点数");
		Kobe_LineChart.setCreateSymbols(false);

		getList(MyCalendar.getDayByNum(30), MyCalendar.getToday());

		Kobe_LineChart.getData().addAll(series1, series2);

		}
	}

	private void getList(String start, String end) {

		dateList = marketDetailService.getDetails("hs300", start, end).getDateList();
		OpenList = marketDetailService.getDetails("hs300", start, end).getOpenList();
		HighList = marketDetailService.getDetails("hs300", start, end).getHighList();
		LowList = marketDetailService.getDetails("hs300", start, end).getLowList();
		CloseList = marketDetailService.getDetails("hs300", start, end).getCloseList();
		VolumeList = marketDetailService.getDetails("hs300", start, end).getVolumeList();
		AdjList = marketDetailService.getDetails("hs300", start, end).getAdjList();

		length = dateList.size();
		marketList.clear();
		for (int i = 0; i < length; i++) {
			marketList.addAll(new Market(VolumeList.get(i), HighList.get(i), AdjList.get(i), LowList.get(i),
					dateList.get(i), OpenList.get(i), CloseList.get(i)));
		}

		volumn_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("volumn"));
		open_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("open"));
		high_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("high"));
		low_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("low"));
		close_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("close"));
		adj_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("adj"));
		date_TableColumn.setCellValueFactory(new PropertyValueFactory<Market, String>("date"));

		grailDetails_Tableview.setItems(marketList);

		// 图表数据
		series1.getData().clear();
		series2.getData().clear();

		ArrayList<Double> high = new ArrayList<Double>();
		ArrayList<Double> low = new ArrayList<Double>();

		for (int i = 0; i < length; i++) {
			try {
				series1.getData().add(new XYChart.Data<String, Number>(dateList.get(i),
						NumberFormat.getInstance().parse(CloseList.get(i))));
				series2.getData().add(new XYChart.Data<String, Number>(dateList.get(i),
						NumberFormat.getInstance().parse(OpenList.get(i))));

				high.add(Double.parseDouble(HighList.get(i)));
				low.add(Double.parseDouble(LowList.get(i)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// compute the lowest data and the highest data
		double lowest = LogicController.getMin(low);
		double highest = LogicController.getMax(high);

		DecimalFormat df = new DecimalFormat("#.00");
		yAxis.setLowerBound(Double.parseDouble(df.format(lowest)));
		yAxis.setUpperBound(Double.parseDouble(df.format(highest)));
		yAxis.setTickUnit((int) ((highest - lowest) / 10));

	}

	// 错误提示
	private void remindMessage(String message) {
		Timer timer = new Timer(false);

		remind.setText(message);
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

	public void getDataByDate() {

		startStr= startDate.getValue().toString();
		endStr = endDate.getValue().toString();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startDf = null;
		java.util.Date endDf = null;
		java.util.Date todayDf = null;
		
		try {
			startDf = df.parse(startStr);
			endDf = df.parse(endStr);
			todayDf = df.parse(MyCalendar.getToday());
		} catch (Exception e) {
             System.out.println("格式转化错误");
		}

		if (endDf.getTime() > todayDf.getTime()) {
			remindMessage("结束日期最晚为今天");
		} else if (startDf.getTime() > endDf.getTime()) {
			remindMessage("开始应早于结束日期");
		} else {
			getList(startStr, endStr);
		}

	}

	public void disable(){
		startDate.setDisable(true);
		endDate.setDisable(true);
		search.setDisable(true);
	}
	
}
