package cn.edu.nju.luckers.luckers_stocks.ui.CandleStickChart;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.KGraphService.KGraphService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.ui.Favorites.FavoritesController;
import cn.edu.nju.luckers.luckers_stocks.ui.StockDetails.StockDetailsController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.mainPaneController;
import cn.edu.nju.luckers.luckers_stocks.vo.KGraphVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class CandleStickChartController {

	public StackPane stackPane;
	public DatePicker startDatePicker;
	public DatePicker endDatePicker;
	public javafx.scene.control.TextArea titleTfd;
	public javafx.scene.control.TextArea contentTfd;
	public Button dayK;
	public Button weekK;
	public Button monthK;
	public Button confirm;
	public Label startRemind;
	public Label endRemind;
	public Label toNowRemind;
	public ChoiceBox<String> weekChoice;
	public ChoiceBox<String> monthChoice;
	public Label remind;

	public static String id;
	public static String name;
	private String extra = "日K图";

	StockDetailService stockDetailServic;
	KGraphService kGraphService;
	private mainPaneController MainPaneController = mainPaneController.getInstance();

	private String startStr = MyCalendar.getDayByNum(30);
	private String endStr = MyCalendar.getToday();
	private String weekDefault = "10周";
	private String monthDefault = "10月";

	private double lowPrice = Double.MAX_VALUE;
	private double highPrice = Double.MIN_VALUE;
	private double lowVol = Double.MAX_VALUE;
	private double highVol = Double.MIN_VALUE;

	private static boolean able;
	List ls = new ArrayList();// 定义一个用来保存数据的集合类List

	public void initialize() {
		setFavorite();
		if(!able){
			disable();
		}else{
		stockDetailServic = LogicController.getInstance().getDetailServer();
		kGraphService = LogicController.getInstance().getKGraphServer();

		swingNodeChanged();

		String[] start_split = startStr.split("-");
		startDatePicker.setValue(LocalDate.of(Integer.parseInt(start_split[0]), Integer.parseInt(start_split[1]),
				Integer.parseInt(start_split[2])));
		String[] end_splite = endStr.split("-");
		endDatePicker.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
				Integer.parseInt(end_splite[2])));

		titleTfd.setEditable(false);
		contentTfd.setEditable(false);

		startDatePicker.setVisible(true);
		endDatePicker.setVisible(true);
		startRemind.setVisible(true);
		endRemind.setVisible(true);
		toNowRemind.setVisible(false);
		weekChoice.setVisible(false);
		monthChoice.setVisible(false);

		String remind = "日期\r\n\r\n名称\r\nID\r\n开盘价\r\n收盘价\r\n" + "最高价\r\n最低价\r\n市净率\r\n市盈率\r\n" + "成交量\r\n后复权价";
		titleTfd.setText(remind);
		StockInformVO vo = stockDetailServic.getOneDay(id, MyCalendar.getNearestTradingDate(id));
		String content = vo.getDate() + "(最新数据日)\r\n\r\n" + name + "\r\n" + vo.getID() + "\r\n"
				+ vo.getItem(DataKey.open) + "元\r\n" + vo.getItem(DataKey.close) + "元\r\n" + vo.getItem(DataKey.high)
				+ "元\r\n" + vo.getItem(DataKey.low) + "元\r\n" + vo.getItem(DataKey.pb) + "%\r\n"
				+ vo.getItem(DataKey.pe_ttm) + "%\r\n" + vo.getItem(DataKey.volume) + "笔\r\n"
				+ vo.getItem(DataKey.adj_price) + "元\r\n";
		contentTfd.setText(content);
		contentTfd.setFont(Font.font("Verdana", 17));
		titleTfd.setFont(Font.font("Verdana", 17));

		weekChoice.setItems(FXCollections.observableArrayList("10周", "15周", "20周", "25周"));
		monthChoice.setItems(FXCollections.observableArrayList("10月", "15月", "20月", "25月"));
		}
	}
	public void setFavorite(){
		MainPaneController.setFavorite(3);
	}
	
	public static Parent launch(boolean able) throws IOException {
		FXMLLoader loader = new FXMLLoader(CandleStickChartController.class.getResource("CandleStickChart.fxml"));
		CandleStickChartController candleStickChartController = loader.getController();
		candleStickChartController.able = able;
		Pane pane = loader.load();
		

		return pane;
	}

	public JPanel createCombinedChart() {
		Map m = createDatasetMap();// 从数据对象里取出各种类型的对象，主要是用来表示均线的时间线(IntervalXYDataset)对象和用来表示阴阳线和成交量的蜡烛图对象(OHLCDataset)
		IntervalXYDataset avg_line5 = (IntervalXYDataset) m.get("avg_line5");
		IntervalXYDataset avg_line10 = (IntervalXYDataset) m.get("avg_line10");
		IntervalXYDataset avg_line20 = (IntervalXYDataset) m.get("avg_line20");
		OHLCDataset k_line = (OHLCDataset) m.get("k_line");
		OHLCDataset vol = (OHLCDataset) m.get("vol");

		String stock_name = (String) m.get("stock_name");

		// 设置若干个时间线的Render，目的是用来让几条均线显示不同的颜色，和为时间线加上鼠标提示

		XYLineAndShapeRenderer xyLineRender1 = new XYLineAndShapeRenderer(true, false);
		xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0.00")));
		xyLineRender1.setSeriesPaint(0, Color.BLACK);

		XYLineAndShapeRenderer xyLineRender2 = new XYLineAndShapeRenderer(true, false);
		xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0.00")));
		xyLineRender1.setSeriesPaint(0, Color.blue);

		XYLineAndShapeRenderer xyLineRender3 = new XYLineAndShapeRenderer(true, false);
		xyLineRender1.setBaseToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
				new SimpleDateFormat("yyyy-MM-dd"), new DecimalFormat("0.00")));
		xyLineRender1.setSeriesPaint(0, Color.darkGray);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

		final CandlestickRenderer candlestickRender = new CandlestickRenderer();// 设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
		candlestickRender.setUseOutlinePaint(true); // 设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
		candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);// 设置如何对K线图的宽度进行设定
		candlestickRender.setAutoWidthGap(0.001);// 设置各个K线图之间的间隔
		candlestickRender.setUpPaint(Color.RED);// 设置股票上涨的K线图颜色
		candlestickRender.setDownPaint(Color.GREEN);// 设置股票下跌的K线图颜色

		DateAxis x1Axis = new DateAxis();// 设置x轴，也就是时间轴
		x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 不显示周末
		NumberAxis y1Axis = new NumberAxis("K线图");// 设定y轴，就是数字轴
		y1Axis.setAutoRange(false);// 不使用自动设定范围
		y1Axis.setRange(lowPrice * 0.9, highPrice * 1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些
		XYPlot candlePlot = new XYPlot(k_line, x1Axis, y1Axis, candlestickRender);
		candlePlot.setDataset(1, avg_line5);
		candlePlot.setRenderer(1, xyLineRender1);
		candlePlot.setDataset(2, avg_line10);
		candlePlot.setRenderer(2, xyLineRender2);
		candlePlot.setDataset(3, avg_line20);
		candlePlot.setRenderer(3, xyLineRender3);

		// 定义K线图上半截显示的Plot
		DateAxis x2Axis = new DateAxis();// 设置x轴，也就是时间轴
		x2Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());// 不显示周末
		NumberAxis y2Axis = new NumberAxis("成交量");// 设定y轴，就是数字轴
		y2Axis.setAutoRange(false);// 不不使用自动设定范围
		y2Axis.setRange(lowVol * 0.9, highVol * 1.1);// 设定y轴值的范围，比最低值要低一些，比最大值要大一些
		XYPlot volPlot = new XYPlot(vol, x2Axis, y2Axis, candlestickRender);

		// 复合类型时间轴
		DateAxis dateaxis = new DateAxis("时间");
		dateaxis.setLowerMargin(0.0D);
		dateaxis.setUpperMargin(0.02D);
		dateaxis.setAutoRange(false);// 设置不采用自动设置时间范围
		if (extra.equals("日K图")) {
			try {
				dateaxis.setRange(dateFormat.parse(MyCalendar.getYesterday(startStr)), dateFormat.parse(endStr));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (extra.equals("周K图")) {
			int numOfWeek = Integer.parseInt(weekDefault.substring(0, 2));

			try {
				dateaxis.setRange(dateFormat.parse(MyCalendar.getDayByNum(numOfWeek * 7)),
						dateFormat.parse(MyCalendar.getToday()));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			int numOfMonth = Integer.parseInt(weekDefault.substring(0, 2));

			try {
				dateaxis.setRange(dateFormat.parse(MyCalendar.getDayByNum(numOfMonth * 30)),
						dateFormat.parse(MyCalendar.getToday()));// 设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		dateaxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
		dateaxis.setAutoTickUnitSelection(false);// 设置不采用自动选择刻度值
		dateaxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);// 设置标记的位置
		dateaxis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());// 设置标准的时间刻度单位
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));// 设置时间刻度的间隔，一般以周为单位

		// 定义一个复合类型的Plot，目的是为了把Chart的上半截和下半截结合起来，形成一张完整的K线图
		CombinedDomainXYPlot combineXY = new CombinedDomainXYPlot(dateaxis);
		// 把上下两个Plot都加到复合Plot里，并设置它们在图中所占的比重
		combineXY.add(candlePlot, 3);
		combineXY.add(volPlot, 1);
		combineXY.setGap(8D);
		combineXY.setDomainGridlinesVisible(true);
		JFreeChart jfreechart = new JFreeChart(stock_name + extra, JFreeChart.DEFAULT_TITLE_FONT, combineXY, false);
		jfreechart.setBackgroundPaint(Color.white);

		// 为Chart图添加一个图例，这里我们可以定义需要显示的一些信息，及图例放置的位置，我们选择的顶部
		LegendTitle legendtitle = new LegendTitle(candlePlot);
		BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
		blockcontainer.setFrame(new BlockBorder(0.10000000000000001D, 0.10000000000000001D, 0.10000000000000001D,
				0.10000000000000001D));
		BlockContainer blockcontainer1 = legendtitle.getItemContainer();
		blockcontainer1.setPadding(2D, 10D, 5D, 2D);
		blockcontainer.add(blockcontainer1);
		legendtitle.setWrapper(blockcontainer);
		legendtitle.setPosition(RectangleEdge.TOP);
		legendtitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		jfreechart.addSubtitle(legendtitle);
		ChartPanel chartpanel = new ChartPanel(jfreechart, 600, 400, 600, 400, 600, 400, false, false, false, false,
				false, false);

		return chartpanel;
	}

	private void getData() {

		// 根据不同的标记，调用日K，周K，月K的接口
		if (extra.equals("日K图")) {

			startStr = startDatePicker.getValue().toString();
			endStr = endDatePicker.getValue().toString();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDf = null;
			java.util.Date endDf = null;
			java.util.Date todayDf = null;

			try {
				startDf = df.parse(startStr);
				endDf = df.parse(endStr);
				todayDf = df.parse(MyCalendar.getToday());
			} catch (Exception e) {
				System.out.println("格式信息有误");
			}

			if (endDf.getTime() > todayDf.getTime()) {
				remindMessage("结束日期最晚为今天");
				endStr=MyCalendar.getToday();
				String[] end_splite = endStr.split("-");
				endDatePicker.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
						Integer.parseInt(end_splite[2])));
			} else if (startDf.getTime() > endDf.getTime()) {
				remindMessage("开始应早于结束日期");
			} else {
				ls.clear();
				Iterator<StockInformVO> iterator = null;
				Iterator<KGraphVO> iteratorK = null;

				iterator = stockDetailServic.getDetails(id, startStr, endStr);

				StockInformVO vo;

				while (iterator.hasNext()) {
					Map map = new HashMap();
					vo = iterator.next();

					map.put("stock_name", name);
					map.put("issue_date", vo.getDate());
					map.put("open_value", vo.getItem(DataKey.open));
					map.put("high_value", vo.getItem(DataKey.high));
					if (Double.parseDouble(vo.getItem(DataKey.high)) > highPrice) {
						highPrice = Double.parseDouble(vo.getItem(DataKey.high));
					}
					map.put("low_value", vo.getItem(DataKey.low));
					if (Double.parseDouble(vo.getItem(DataKey.low)) < lowPrice) {
						lowPrice = Double.parseDouble(vo.getItem(DataKey.low));
					}
					map.put("close_value", vo.getItem(DataKey.close));
					map.put("avg5", vo.getItem(DataKey.average_5));
					map.put("avg10", vo.getItem(DataKey.average_10));
					map.put("avg20", vo.getItem(DataKey.average_20));
					map.put("volume_value", vo.getItem(DataKey.volume));
					if (Double.parseDouble(vo.getItem(DataKey.volume)) > highVol) {
						highVol = Double.parseDouble(vo.getItem(DataKey.volume));
					}
					if (Double.parseDouble(vo.getItem(DataKey.volume)) < lowVol) {
						lowVol = Double.parseDouble(vo.getItem(DataKey.volume));
					}

					ls.add(map);
				}
			}

		} else if (extra.equals("周K图")) {
			ls.clear();
			Iterator<StockInformVO> iterator = null;
			Iterator<KGraphVO> iteratorK = null;

			weekDefault = weekChoice.getValue().toString();
			int number = Integer.parseInt(weekDefault.substring(0, 2));
			iteratorK = kGraphService.getWeekK(id, number);

			KGraphVO vo;

			while (iteratorK.hasNext()) {
				Map map = new HashMap();
				vo = iteratorK.next();

				System.out.println(
						vo.getDate() + " " + vo.getAverage_5() + " " + vo.getAverage_10() + " " + vo.getClose());

				map.put("stock_name", name);
				map.put("issue_date", vo.getDate());
				map.put("open_value", vo.getOpen());
				map.put("high_value", vo.getHigh());
				if (vo.getHigh() > highPrice) {
					highPrice = vo.getHigh();
				}
				map.put("low_value", vo.getLow());
				if (vo.getLow() < lowPrice) {
					lowPrice = vo.getLow();
				}
				map.put("close_value", vo.getClose());
				map.put("avg5", vo.getAverage_5());
				// 桩
				map.put("avg10", vo.getAverage_10());
				map.put("avg20", vo.getAverage_20());
				map.put("volume_value", vo.getVolume());
				if (vo.getVolume() > highVol) {
					highVol = vo.getVolume();
				}
				if (vo.getVolume() < lowVol) {
					lowVol = vo.getVolume();
				}

				ls.add(map);
			}

		} else if (extra.equals("月K图")) {
			ls.clear();
			Iterator<StockInformVO> iterator = null;
			Iterator<KGraphVO> iteratorK = null;

			monthDefault = monthChoice.getValue().toString();
			int number = Integer.parseInt(monthDefault.substring(0, 2));
			iteratorK = kGraphService.getMonthK(id, number);

			KGraphVO vo;

			while (iteratorK.hasNext()) {
				Map map = new HashMap();
				vo = iteratorK.next();

				map.put("stock_name", name);
				map.put("issue_date", vo.getDate());
				map.put("open_value", vo.getOpen());
				map.put("high_value", vo.getHigh());
				if (vo.getHigh() > highPrice) {
					highPrice = vo.getHigh();
				}
				map.put("low_value", vo.getLow());
				if (vo.getLow() < lowPrice) {
					lowPrice = vo.getLow();
				}
				map.put("close_value", vo.getClose());
				map.put("avg5", vo.getAverage_5());
				// 桩
				map.put("avg10", vo.getAverage_10());
				map.put("avg20", vo.getAverage_20());
				map.put("volume_value", vo.getVolume());
				if (vo.getVolume() > highVol) {
					highVol = vo.getVolume();
				}
				if (vo.getVolume() < lowVol) {
					lowVol = vo.getVolume();
				}

				ls.add(map);
			}
		}

	}

	// 错误提示
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

	private Map createDatasetMap() {

		getData();

		// 从每一行记录里取出特定值，用来开成各种类型的均线和阴阳线图
		Map m = new HashMap();
		TimeSeries avg_lin5 = new TimeSeries("5日均线", org.jfree.data.time.Day.class);
		TimeSeries avg_lin10 = new TimeSeries("10日均线", org.jfree.data.time.Day.class);
		TimeSeries avg_lin20 = new TimeSeries("20日均线", org.jfree.data.time.Day.class);

		int count = ls.size();
		Date adate[] = new Date[count];
		double high[] = new double[count];
		double low[] = new double[count];
		double close[] = new double[count];
		double open[] = new double[count];
		double volume[] = new double[count];
		Date adate1[] = new Date[count];
		double high1[] = new double[count];
		double low1[] = new double[count];
		double close1[] = new double[count];
		double open1[] = new double[count];
		double volume1[] = new double[count];

		String stock_name = null;
		Calendar cal = Calendar.getInstance();

		for (int j = 0; j < ls.size(); j++) {
			Map vMap = (Map) ls.get(j);

			stock_name = (String) vMap.get("stock_name");

			String splite[] = (vMap.get("issue_date").toString()).split("-");
			Date issue_date = new Date(Integer.parseInt(splite[0]) - 1900, Integer.parseInt(splite[1]) - 1,
					Integer.parseInt(splite[2]));

			double open_value = Double.parseDouble(vMap.get("open_value").toString());
			double high_value = Double.parseDouble(vMap.get("high_value").toString());
			double low_value = Double.parseDouble(vMap.get("low_value").toString());
			double close_value = Double.parseDouble(vMap.get("close_value").toString());
			double avg5 = Double.parseDouble(vMap.get("avg5").toString());
			double avg10 = Double.parseDouble(vMap.get("avg10").toString());
			double avg20 = Double.parseDouble(vMap.get("avg20").toString());
			// double avg60 = Double.parseDouble(vMap.get("avg60").toString());
			double volume_value = Double.parseDouble(vMap.get("volume_value").toString());

			cal.setTime(issue_date);

			if (avg5 > 0.0D)
				avg_lin5.addOrUpdate(new Day(cal.get(5), cal.get(2) + 1, cal.get(1)), avg5);
			if (avg10 > 0.0D)
				avg_lin10.addOrUpdate(new Day(cal.get(5), cal.get(2) + 1, cal.get(1)), avg10);
			if (avg20 > 0.0D)
				avg_lin20.addOrUpdate(new Day(cal.get(5), cal.get(2) + 1, cal.get(1)), avg20);

			adate[j] = issue_date;
			high[j] = high_value;
			low[j] = low_value;
			close[j] = close_value;
			open[j] = open_value;
			volume[j] = 0.0D;
			adate1[j] = issue_date;
			high1[j] = 0.0D;
			low1[j] = 0.0D;
			close1[j] = 0.0D;
			open1[j] = 0.0D;

			// 这里是我们用蜡烛图来构造与阴阳线对应的成交量图，我们主要是通过判断开盘价与收盘价相比较的值来决定到底是在表示成交量的蜡烛图的开盘价设置值还是收盘价设置值，设置之前我们把它们全部都设置为0
			if (open_value < close_value)
				close1[j] = volume_value;
			else
				open1[j] = volume_value;
			volume1[j] = 0.0D;
		}

		DefaultHighLowDataset k_line = new DefaultHighLowDataset("", adate, high, low, close, open, volume);
		DefaultHighLowDataset vol = new DefaultHighLowDataset("", adate1, high1, low1, close1, open1, volume1);
		// 把各种类型的图表对象放到Map里，以为其它方法提供使用
		m.put("k_line", k_line);
		m.put("vol", vol);
		m.put("stock_name", stock_name);
		m.put("avg_line5", new TimeSeriesCollection(avg_lin5));
		m.put("avg_line10", new TimeSeriesCollection(avg_lin10));
		m.put("avg_line20", new TimeSeriesCollection(avg_lin20));

		return m;
	}

	private void common() {
		lowPrice = Double.MAX_VALUE;
		highPrice = Double.MIN_VALUE;
		lowVol = Double.MAX_VALUE;
		highVol = Double.MIN_VALUE;

		startStr = MyCalendar.getDayByNum(30);
		endStr = MyCalendar.getToday();
		weekDefault = "10周";
		monthDefault = "10月";
		String[] start_split = startStr.split("-");
		startDatePicker.setValue(LocalDate.of(Integer.parseInt(start_split[0]), Integer.parseInt(start_split[1]),
				Integer.parseInt(start_split[2])));
		String[] end_splite = endStr.split("-");
		endDatePicker.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
				Integer.parseInt(end_splite[2])));
		weekChoice.setValue(weekDefault);
		monthChoice.setValue(monthDefault);
	}

	public void dayKPlot() {
		common();

		startDatePicker.setVisible(true);
		endDatePicker.setVisible(true);
		startRemind.setVisible(true);
		endRemind.setVisible(true);
		toNowRemind.setVisible(false);
		weekChoice.setVisible(false);
		monthChoice.setVisible(false);

		extra = "日K图";
		swingNodeChanged();
	}

	public void weekKPlot() {
		common();

		startDatePicker.setVisible(false);
		endDatePicker.setVisible(false);
		startRemind.setVisible(false);
		endRemind.setVisible(false);
		toNowRemind.setVisible(true);
		weekChoice.setVisible(true);
		monthChoice.setVisible(false);

		extra = "周K图";
		swingNodeChanged();
	}

	public void monthKPlot() {
		System.out.println("month");
		common();

		startDatePicker.setVisible(false);
		endDatePicker.setVisible(false);
		startRemind.setVisible(false);
		endRemind.setVisible(false);
		toNowRemind.setVisible(true);
		weekChoice.setVisible(false);
		monthChoice.setVisible(true);

		extra = "月K图";
		swingNodeChanged();
	}

	public void swingNodeChanged() {
		stackPane.getChildren().clear();

		SwingNode swingNode = new SwingNode();
		createAndSetSwingContent(swingNode);
		stackPane.getChildren().add(swingNode);
	}

	private void createAndSetSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// swingNode.setContent(initKPlot());
				swingNode.setContent(createCombinedChart());
			}
		});
	}

	public void jumpBack(ActionEvent actionEvent) {
		try {
			mainPaneController.getInstance().content_Pane.getChildren().clear();
			mainPaneController.getInstance().content_Pane.getChildren().add(FavoritesController.launch(true));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jumpToDetails(ActionEvent actionEvent) {
		mainPaneController.getInstance().content_Pane.getChildren().clear();

		try {
			mainPaneController.getInstance().content_Pane.getChildren().add(StockDetailsController.launch(true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disable(){
		
	}

}
