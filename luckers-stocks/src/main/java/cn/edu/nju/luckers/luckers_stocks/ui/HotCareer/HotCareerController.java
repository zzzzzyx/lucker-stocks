package cn.edu.nju.luckers.luckers_stocks.ui.HotCareer;

import java.awt.Color;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;


import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.IndustryService.IndustryInformService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryScoreVO;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryVO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class HotCareerController {

	public Label hotCareer;
	public ChoiceBox<String> career1;
	public ChoiceBox<String> career2;
	public DatePicker start;
	public DatePicker end;

	private String hotCareerStr;
	private String career1Str;
	private String career2Str;
	private ArrayList<String> listOfCareers;
	private String startStr = MyCalendar.getDayByNum(7);
	private String endStr = MyCalendar.getToday();

	public StackPane stackPane;
	public CategoryAxis xAxis;
	public NumberAxis yAxis;
	public BarChart<String, Number> bc;
	public Label remind;
	public Button confirm;
	private static boolean able;
	private double score = -1;
	private IndustryInformService industryinformService;
	XYChart.Series[] seriesList;
	final SwingNode swingNode = new SwingNode();

	public void initialize() throws IOException {
		if(!able){
			disable();
		}else{
		industryinformService = LogicController.getInstance().getIndustryServer();

		// 日期选择默认为近一个星期
		String[] start_split = startStr.split("-");
		start.setValue(LocalDate.of(Integer.parseInt(start_split[0]), Integer.parseInt(start_split[1]),
				Integer.parseInt(start_split[2])));
		String[] end_splite = endStr.split("-");
		end.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
				Integer.parseInt(end_splite[2])));

		// 获取当前自选股的行业信息，并设置默认的行业
		Iterator<IndustryVO> iterator = industryinformService.getIndustryList();
		listOfCareers = new ArrayList<>();
		while (iterator.hasNext()) {
			IndustryVO vo = iterator.next();
			listOfCareers.add(vo.getName());
		}

		career1.setItems(FXCollections.observableArrayList(listOfCareers));
		career2.setItems(FXCollections.observableArrayList(listOfCareers));
		career1Str = listOfCareers.get(0);
		career2Str = listOfCareers.get(1);
		career1.setValue(career1Str);
		career2.setValue(career2Str);

		// swing和javafx的集成
		createAndSetSwingContent(swingNode);
		stackPane.getChildren().add(swingNode);

		// 柱状图
		bc.setTitle("Luckers 评分");
		xAxis.setLabel("分值");
		yAxis.setLabel("评分");

		// 读取行业列表
		seriesList = new XYChart.Series[listOfCareers.size()];
		for (int i = 0; i < listOfCareers.size(); i++) {
			seriesList[i] = new XYChart.Series();
			seriesList[i].setName(listOfCareers.get(i));
			IndustryScoreVO vo = industryinformService.getIndustryScore(listOfCareers.get(i), startStr, endStr);
			seriesList[i].getData().add(new XYChart.Data(listOfCareers.get(i), vo.getFinalScore()));

			if (vo.getFinalScore() > score) {
				score = vo.getFinalScore();
				hotCareerStr = listOfCareers.get(i);
			}

			bc.getData().add(seriesList[i]);
		}

		// 热门行业
		hotCareer.setText(hotCareerStr);
		
		
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void confirm() {
		score = -1;
		startStr = start.getValue().toString();
		endStr = end.getValue().toString();

		career1Str = career1.getValue();
		career2Str = career2.getValue();

		// 若结束日期选择在今天后面，将其设为今天
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date startDf = df.parse(startStr);
			java.util.Date endDf = df.parse(endStr);
			java.util.Date todayDf = df.parse(MyCalendar.getToday());
			if (endDf.getTime() > todayDf.getTime()) {

				endStr = MyCalendar.getToday();
				String[] end_splite = endStr.split("-");
				end.setValue(LocalDate.of(Integer.parseInt(end_splite[0]), Integer.parseInt(end_splite[1]),
						Integer.parseInt(end_splite[2])));

				// 计时器和javafx线程实现错误提示
				Timer timer = new Timer(false);

				remindMessage("结束日期最晚为今天");

			} else if (startDf.getTime() > endDf.getTime()) {
				remindMessage("开始应早于结束日期");
			} else {
 
				if (career1Str.equals(career2Str)) {
					remindMessage("比较行业一样");
				} else {
					// 雷达图调整
					stackPane.getChildren().clear();
					createAndSetSwingContent(swingNode);
					stackPane.getChildren().add(swingNode);
					// 柱状图数据处理
					for (int i = 0; i < listOfCareers.size(); i++) {
						IndustryScoreVO vo = industryinformService.getIndustryScore(listOfCareers.get(i), startStr,
								endStr);
						seriesList[i].getData().add(new XYChart.Data(listOfCareers.get(i), vo.getFinalScore()));
						if (vo.getFinalScore() > score) {
							score = vo.getFinalScore();
							hotCareerStr = listOfCareers.get(i);
							hotCareer.setText(hotCareerStr);
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
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

	private void createAndSetSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(erstelleSpinnenDiagramm(computeSplotData()));
			}
		});
	}

	private DefaultCategoryDataset computeSplotData() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// 调用逻辑层接口，获取特定时间段下的雷达图数据
		IndustryScoreVO vo1 = industryinformService.getIndustryScore(career1Str, startStr, endStr);
		IndustryScoreVO vo2 = industryinformService.getIndustryScore(career2Str, startStr, endStr);

		dataset.addValue(vo1.getRiskScore(), career1Str, "安全性");
		dataset.addValue(vo1.getBenScore(), career1Str, "盈利性");
		dataset.addValue(vo1.getMarketScore(), career1Str, "市场性");
		dataset.addValue(vo1.getProScore(), career1Str, "价值性");
		dataset.addValue(vo1.getConScore(), career1Str, "稳定性");

		dataset.addValue(vo2.getRiskScore(), career2Str, "安全性");
		dataset.addValue(vo2.getBenScore(), career2Str, "盈利性");
		dataset.addValue(vo2.getMarketScore(), career2Str, "市场性");
		dataset.addValue(vo2.getProScore(), career2Str, "价值性");
		dataset.addValue(vo2.getConScore(), career2Str, "稳定性");

		return dataset;

	}

	public JPanel erstelleSpinnenDiagramm(DefaultCategoryDataset dataset) {

		MySpiderWebPlot spiderwebplot = new MySpiderWebPlot(dataset);

		JFreeChart jfreechart = new JFreeChart(career1Str + "和" + career2Str + "的雷达图对比", TextTitle.DEFAULT_FONT,
				spiderwebplot, false);
		LegendTitle legendtitle = new LegendTitle(spiderwebplot);
		legendtitle.setPosition(RectangleEdge.BOTTOM);
		jfreechart.addSubtitle(legendtitle);

		ChartPanel chartpanel = new ChartPanel(jfreechart, 380, 380, 380, 380, 380, 380, false, false, false, false,
				false, false);
		chartpanel.setOpaque(false);
		chartpanel.setBackground(Color.yellow);

		return chartpanel;
	}
	
	public void disable(){
		career1.setDisable(true);
		career2.setDisable(true);
		start.setDisable(true);
		end.setDisable(true);
		confirm.setDisable(true);
	}

	public static Parent launch(boolean able) throws IOException {
		FXMLLoader loader = new FXMLLoader(HotCareerController.class.getResource("HotCareer.fxml"));
		HotCareerController hotCareerController =loader.getController();
		hotCareerController.able=able;
		Pane pane = loader.load();
		
		return pane;
	}

}
