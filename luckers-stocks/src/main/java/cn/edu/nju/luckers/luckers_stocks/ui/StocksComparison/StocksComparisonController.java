package cn.edu.nju.luckers.luckers_stocks.ui.StocksComparison;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.ui.common.commonui.StockAbstractCheckItem;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;

public class StocksComparisonController implements Runnable{

	private FavouriteService favouriteService = LogicController.getInstance().getFavouriteServer();
	private StockDetailService stockDetailService = LogicController.getInstance().getDetailServer();

	public ChoiceBox<String> stock1_Box;
	public ChoiceBox<String> stock2_Box;

	public CheckBox open_Box;
	public CheckBox high_Box;
	public CheckBox low_Box;
	public CheckBox close_Box;
	public CheckBox adj_Box;
	public CheckBox volumn_Box;
	public CheckBox turnover_Box;
	public CheckBox pb_Box;
	public CheckBox pe_Box;
	public Label remind;
	public ProgressIndicator radar_Progress;
	public Button sure;
	
	public ArrayList<CheckBox> box_List = new ArrayList<>();

	public DatePicker start_DatePicker;
	public DatePicker end_DatePicker;

	public LineChart comparison_LineChart;

	XYChart.Series<String, Number> series1;
	XYChart.Series<String, Number> series2;
	XYChart.Series<String, Number> series3;
	XYChart.Series<String, Number> series4;
	XYChart.Series<String, Number> series5;
	XYChart.Series<String, Number> series6;

	private List<StockAbstractCheckItem> stocks = new ArrayList<StockAbstractCheckItem>();
	private List<StockAbstractCheckItem> comparingStocks1 = new ArrayList<StockAbstractCheckItem>();
	private List<StockAbstractCheckItem> comparingStocks2 = new ArrayList<StockAbstractCheckItem>();

	private String select;
	private String startStr = MyCalendar.getDayByNum(30);
	private String endStr = MyCalendar.getToday();
	private double volume = 0;
	private ArrayList<String> names = new ArrayList<>();
	private int count = 0;
	private int tag = 0;
	private ArrayList<String> temp1 = new ArrayList<>();
	private ArrayList<String> temp2 = new ArrayList<>();
	// private ArrayList<String> data = new ArrayList<>();
	// private ArrayList<String> dataCount = new ArrayList<>();
	private int[] data = new int[10];
	private static boolean able;
	
	public static Parent launch(boolean able)  throws IOException {
	
		FXMLLoader loader = new FXMLLoader(StocksComparisonController.class.getResource("StocksComparison.fxml"));
		StocksComparisonController stocksComparisonController = loader.getController();
		stocksComparisonController.able=able;
		Pane pane = loader.load();
		
//		new Thread(stocksComparisonController).start();
		return pane;
	}

	public void initialize() throws IOException {
		if(!able){
			disable();
		}else{
		Iterator<StockInformVO> voList = favouriteService.GetContent();
		while (voList.hasNext()) {
			StockInformVO vo = voList.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			stocks.add(stock);
		}
		for (StockAbstractCheckItem temp : stocks) {
			names.add(temp.getVo().getID() + " " + temp.getVo().getName());
		}

		temp1 = names;
		temp2 = names;

		stock1_Box.setItems(FXCollections.observableArrayList(names));
		stock1_Box.setValue(stock1_Box.getItems().get(0));

		stock1_Box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// if (oldValue.equals(newValue)) {
			// System.out.println("the value isn't changed");
			// } else {
			// temp1 = names;
			// System.out.println("here we will refresh Box Two");
			//
			// refreshBoxOne();
			// }
			
			rePaint(null);
			
		});

		stock2_Box.setItems(FXCollections.observableArrayList(names));
		stock2_Box.setValue(stock2_Box.getItems().get(1));

		stock2_Box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// if (oldValue.equals(newValue)) {
			// } else {
			// temp2 = names;
			// System.out.println("here we will refresh Box One");
			// refreshBoxTwo();
			// }
			rePaint(null);

		});
		String[] start_split = startStr.split("-");
		start_DatePicker.setValue(LocalDate.of(Integer.parseInt(start_split[0]), Integer.parseInt(start_split[1]),
				Integer.parseInt(start_split[2])));
		String[] end_splite = endStr.split("-");
		end_DatePicker.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
				Integer.parseInt(end_splite[2])));

		series1 = new XYChart.Series<String, Number>();
		series2 = new XYChart.Series<String, Number>();
		series3 = new XYChart.Series<String, Number>();
		series4 = new XYChart.Series<String, Number>();
		series5 = new XYChart.Series<String, Number>();
		series6 = new XYChart.Series<String, Number>();

		series1.setName("");
		series2.setName("");
		series3.setName("");
		series4.setName("");
		series5.setName("");
		series6.setName("");

		comparison_LineChart.setCreateSymbols(false);
		comparison_LineChart.getData().addAll(series1, series2, series3, series4, series5, series6);

		box_List.add(close_Box);
		box_List.add(open_Box);
		box_List.add(pe_Box);
		box_List.add(turnover_Box);
		box_List.add(adj_Box);
		box_List.add(high_Box);
		box_List.add(low_Box);
		box_List.add(pb_Box);
		box_List.add(volumn_Box);

		for (int i = 0; i < 9; i++) {
			data[i] = 0;
		}

		for (CheckBox temp : box_List) {
			temp.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {

					if (count == 4 && !new_val) {

					} else {
						System.out.println(new_val);

						// if (count == 3 && new_val == true) {
						//
						// temp.setSelected(false);
						//
						// System.out.println("haha");
						// System.out.println("count here is "+count);
						// } else {
						// System.out.println("countcountcount "+count);
						// System.out.println("laile");
						// if (count < 3) {
						select = temp.getText();
						int n = box_List.indexOf(temp);
						// System.out.println(new_val);
						repaintChart(new_val, select, n);
						System.out.println("count is " + count);

						if (count == 4 && new_val) {
							temp.setSelected(false);
							count--;
							System.out.println("after false count is " + count);
						}
						// }

						// else{
						// int n = box_List.indexOf(temp);
						// repaintChart(new_val, select, n);
						//
						// }
						// }
					}
				}
			});
		}
	}

	}

	public void changeDate(ActionEvent actionEvent) {
		int year_S = start_DatePicker.getValue().getYear();
		int year_E = end_DatePicker.getValue().getYear();
		int month_S = start_DatePicker.getValue().getMonthValue();
		int month_E = end_DatePicker.getValue().getMonthValue();
		int day_S = start_DatePicker.getValue().getDayOfMonth();
		int day_E = end_DatePicker.getValue().getDayOfMonth();
		if (year_S > year_E) {
			// start_DatePicker.setValue(end_DatePicker.getValue().);
		}
		if (year_S == year_E && month_S > month_E) {

		}
		if (year_S == year_E && month_S == month_E && day_S >= day_E) {

		}

	}

	public void refreshBoxOne() {
		String stock2 = stock2_Box.getValue();
		int index = names.indexOf(stock1_Box.getValue().toString());
		System.out.println(index);
		// System.out.println(temp1);

		temp1.remove(index);
		stock2_Box.setItems(FXCollections.observableArrayList(temp1));
		stock2_Box.setValue(stock2);

		temp1 = names;
		// System.out.println(temp1);

		// }
	}

	public void refreshBoxTwo() {
		// if(stock1_Box.getValue()==null){
		String stock1 = stock1_Box.getValue();

		int index = names.indexOf(stock2_Box.getValue().toString());
		// System.out.println(temp2);
		// System.out.println(index);
		temp2.remove(index);

		stock1_Box.setValue(stock1);

		stock1_Box.setItems(FXCollections.observableArrayList(temp2));

		// }

	}

	public void repaintChart(boolean box, String select, int n) {
		if (box && count == 3) {
			count++;
			System.out.println("add to 4");
		}
		if (box && count < 3) {
			add(select, n);
		}
		if (!box) {
			sub(select, n);
		}
	}

	public void initListFirst(String select, XYChart.Series<String, Number> series1,
			XYChart.Series<String, Number> series2) {
		startStr= start_DatePicker.getValue().toString();
		endStr = end_DatePicker.getValue().toString();
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
		
		series1.getData().clear();
		series2.getData().clear();
		comparingStocks1.clear();
		comparingStocks2.clear();

		Iterator<StockInformVO> voList1 = stockDetailService.getDetails(stock1_Box.getValue().substring(0, 8),
				start_DatePicker.getValue().toString(), end_DatePicker.getValue().toString());

		while (voList1.hasNext()) {
			StockInformVO vo = voList1.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			comparingStocks1.add(stock);
		}

		Iterator<StockInformVO> voList2 = stockDetailService.getDetails(stock2_Box.getValue().substring(0, 8),
				start_DatePicker.getValue().toString(), end_DatePicker.getValue().toString());
		while (voList2.hasNext()) {
			StockInformVO vo = voList2.next();
			StockAbstractCheckItem stock = new StockAbstractCheckItem(vo);
			comparingStocks2.add(stock);
		}

		String realKey = "";
		switch (select) {
		case "开盘价":
			realKey = "open";
			break;
		case "最高价":
			realKey = "high";
			break;
		case "最低价":
			realKey = "low";
			break;
		case "收盘价":
			realKey = "close";
			break;
		case "成交量(X1000K)":
			realKey = "volume";
			tag = 1;
			break;
		case "换手量":
			realKey = "turnover";
			break;
		case "市盈率(X10)":
			realKey = "pe_ttm";
			tag = 2;
			break;
		case "市净率":
			realKey = "pb";
			break;
		case "后复权价":
			realKey = "adj_price";
			break;
		}

		DataKey key = DataKey.valueOf(realKey);
		if (tag == 0) {
			for (StockAbstractCheckItem temp : comparingStocks1) {
				try {
					volume = Double.parseDouble(temp.getVo().getItem(key));
					if (volume == 0) {
						volume = 10;
					}
					series1.getData().add(new XYChart.Data<String, Number>(temp.getVo().getDate(),
							NumberFormat.getInstance().parse(volume + "")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			for (StockAbstractCheckItem temp : comparingStocks2) {
				try {
					volume = Double.parseDouble(temp.getVo().getItem(key));
					if (volume == 0) {
						volume = 10;
					}
					series2.getData().add(new XYChart.Data<String, Number>(temp.getVo().getDate(),
							NumberFormat.getInstance().parse(volume + "")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		if (tag == 1) { // 成交量
			for (StockAbstractCheckItem temp : comparingStocks1) {
				try {
					volume = Integer.parseInt(temp.getVo().getItem(key)) / 1000000;
					if (volume == 0) {
						volume = 10;
					}
					series1.getData().add(new XYChart.Data<String, Number>(temp.getVo().getDate(),
							NumberFormat.getInstance().parse(volume + "")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			for (StockAbstractCheckItem temp : comparingStocks2) {
				try {
					volume = Integer.parseInt(temp.getVo().getItem(key)) / 1000000;
					if (volume == 0) {
						volume = 10;
					}
					series2.getData().add(new XYChart.Data<String, Number>(temp.getVo().getDate(),
							NumberFormat.getInstance().parse(volume + "")));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
			tag = 0;
		}
		if (tag == 2) { // 市盈率
			for (StockAbstractCheckItem temp : comparingStocks1) {
				try {
					volume = Double.parseDouble(temp.getVo().getItem(key)) / 10;
					if (volume == 0) {
						volume = 10;
					}
					series1.getData().add(new XYChart.Data<String, Number>(temp.getVo().getDate(),
							NumberFormat.getInstance().parse(volume + "")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			for (StockAbstractCheckItem temp : comparingStocks2) {
				try {
					volume = Double.parseDouble(temp.getVo().getItem(key)) / 10;
					if (volume == 0) {
						volume = 10;
					}
					series2.getData().add(new XYChart.Data<String, Number>(temp.getVo().getDate(),
							NumberFormat.getInstance().parse(volume + "")));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
			tag = 0;
		}

		series1.setName(stock1_Box.getValue().toString() + "-" + select);
		series2.setName(stock2_Box.getValue().toString() + "-" + select);
		}
	}

	// 改变日期的时候可以重绘折线图
	public void rePaint(ActionEvent actionEvent) {
		int key = 1;
		for (CheckBox aChackBox : box_List) {
			if (aChackBox.selectedProperty().get()) {
				switch (key) {
				case 1:
					initListFirst(aChackBox.getText(), series1, series2);
					key++;
					break;
				case 2:
					initListFirst(aChackBox.getText(), series3, series4);
					key++;
					break;
				case 3:
					initListFirst(aChackBox.getText(), series5, series6);
					key++;
					break;
				}
			}
		}

	}

	public void add(String add, int n) {
		count++;
		switch (count) {
		case 1:
			data[n] = 1;
			series1.getNode().setVisible(true);
			series2.getNode().setVisible(true);

			
			initListFirst(add, series1, series2);

			// comparison_LineChart.getData().addAll(series1, series2);
			break;
		case 2:
			data[n] = 2;
			series3.getNode().setVisible(true);
			series4.getNode().setVisible(true);
			initListFirst(add, series3, series4);
			// comparison_LineChart.getData().addAll(series1, series2, series3,
			// series4);
			break;
		case 3:
			data[n] = 3;
			series5.getNode().setVisible(true);
			series6.getNode().setVisible(true);
			initListFirst(add, series5, series6);
			// comparison_LineChart.getData().addAll(series1, series2, series3,
			// series4, series5, series6);

			break;
		}
	}

	// To complete
	public void sub(String sub, int n) {
		System.out.println("This is sub!");
		count--;
		switch (data[n]) {
		case 1:
			series1.getNode().setVisible(false);
			series2.getNode().setVisible(false);

			series1.getData().clear();
			series2.getData().clear();

			// if (series1.getData().size() > MAX_DATA_POINTS) {
			// series1.getData().remove(0, series1.getData().size() -
			// MAX_DATA_POINTS);
			// }
			// if (series2.getData().size() > MAX_DATA_POINTS) {
			// series2.getData().remove(0, series2.getData().size() -
			// MAX_DATA_POINTS);
			// }

			series1.setName("");
			series2.setName("");
			break;
		case 2:
			series3.getNode().setVisible(false);
			series4.getNode().setVisible(false);

			series3.getData().clear();
			series4.getData().clear();
			series3.setName("");
			series4.setName("");
			break;
		case 3:
			series5.getNode().setVisible(false);
			series6.getNode().setVisible(false);

			series5.getData().clear();
			series6.getData().clear();
			series5.setName("");
			series6.setName("");
			break;
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
	
	
	public void run() {
		double a=0;
		while (a!=11) {
			try {
				radar_Progress.setProgress(a*10);
                a++;
                System.out.println("aaaa "+a);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
		   radar_Progress.setVisible(false);
		}
	
	
	public void disable(){
		start_DatePicker.setDisable(true);
		end_DatePicker.setDisable(true);
		stock1_Box.setDisable(true);
		stock2_Box.setDisable(true);
		sure.setDisable(true);
	}
	
	
	}
	
	

