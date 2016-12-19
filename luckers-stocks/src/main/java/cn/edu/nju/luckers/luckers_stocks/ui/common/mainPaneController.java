package cn.edu.nju.luckers.luckers_stocks.ui.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.main.Main;
import cn.edu.nju.luckers.luckers_stocks.ui.CandleStickChart.CandleStickChartController;
import cn.edu.nju.luckers.luckers_stocks.ui.Favorites.FavoritesController;
import cn.edu.nju.luckers.luckers_stocks.ui.GrailDetails.GrailDetailsController;
import cn.edu.nju.luckers.luckers_stocks.ui.HotCareer.HotCareerController;
import cn.edu.nju.luckers.luckers_stocks.ui.StockDetails.StockDetailsController;
import cn.edu.nju.luckers.luckers_stocks.ui.StocksComparison.StocksComparisonController;
import cn.edu.nju.luckers.luckers_stocks.ui.tool.R;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class mainPaneController implements Observer {

	private static mainPaneController instance;
	public AnchorPane title_Pane;
	public AnchorPane content_Pane;
	public AnchorPane outerPane;

	public ToggleButton Favorite_ToggleBtn;
	public ToggleButton Grail_ToggleBtn;
	public ToggleButton StocksComparison_ToggleBtn;
	public ToggleButton Hot_ToggleBtn;
	public Button refresh;

	public Label wifi;
	public Label reason;

	public ArrayList<ToggleButton> buttons = new ArrayList();
	private List<ToggleButton> toggleTabs = new LinkedList<>();
	private int ifTheFirstTime = 1;

	private int thePane = 1; // 记录当前界面是哪个 从左至右依次为 1 2 3 4 ...
	private int theFavorite = 1;
	private boolean ifSelected = false;
	private boolean isOK = true;

	public static mainPaneController launch() throws IOException {
		FXMLLoader loader = new FXMLLoader(mainPaneController.class.getResource("mainPane.fxml"));
		AnchorPane pane = loader.load();
		mainPaneController controller = loader.getController();
		instance = controller;

		// resize setting
		controller.content_Pane.prefWidthProperty().bind(Main.primaryStage.widthProperty().subtract(0));
		controller.content_Pane.prefHeightProperty()
				.bind(Main.primaryStage.heightProperty().subtract(R.ui.TitleHeight + R.ui.TabsHeight));
		controller.content_Pane.getChildren().add((new FavoritesController()).launch(true));
		// controller.top_TabsPane.prefHeightProperty()
		// .bind(Main.primaryStage.heightProperty().subtract(R.ui.TitleHeight));
		controller.title_Pane.prefWidthProperty().bind(Main.primaryStage.widthProperty());

		controller.outerPane = pane;
		// new Thread(controller).start();

		controller.Favorite_ToggleBtn.setSelected(true);
		controller.Favorite_ToggleBtn.setPrefWidth(140);
		controller.Favorite_ToggleBtn.setLayoutX(controller.Favorite_ToggleBtn.getLayoutX() - 30);

		return controller;

	}

	// 这个initialize主要是为了给按钮添加效果orz javafx好像不支持transition效果我也是醉了。。
	public void initialize() throws IOException {

		buttons.add(Favorite_ToggleBtn);
		buttons.add(Grail_ToggleBtn);
		buttons.add(StocksComparison_ToggleBtn);
		buttons.add(Hot_ToggleBtn);

		for (ToggleButton aButton : buttons) {

			aButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(aButton.opacityProperty(), .1)),
							new KeyFrame(Duration.seconds(1), new KeyValue(aButton.opacityProperty(), 1))).play();
					ifSelected = aButton.selectedProperty().get();
					if (ifSelected) {
						// System.out.println("nothing happened");
					} else {
						aButton.setLayoutX(aButton.getLayoutX() - 30);
						aButton.setPrefWidth(140);
					}
				}
			});

			aButton.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {

					if (aButton.selectedProperty().get()) {

					}
					if (!aButton.selectedProperty().get()) {
						aButton.setEffect(null);
						aButton.setLayoutX(aButton.getLayoutX() + 30);
						aButton.setPrefWidth(80);
					}

				}
			});
		}

		ExceptionReporter.getInstance().addObserver(this);
	}

	
	
	
	public void jumpToFavorite(ActionEvent actionEvent) {

		// if (aButton == Favorite_ToggleBtn && ifTheFirstTime == 1) {
		// aButton.setLayoutX(aButton.getLayoutX() + 30);
		// ifTheFirstTime = 0;
		// }

		for (ToggleButton aButton : buttons) {

			if (aButton.selectedProperty().get()) {
				aButton.setLayoutX(aButton.getLayoutX() + 30);
				aButton.setPrefWidth(80);
				aButton.setSelected(false);
			}

		}
		// Grail_ToggleBtn.setSelected(false);
		// StocksComparison_ToggleBtn.setSelected(false);
		// Hot_ToggleBtn.setSelected(false);
		Favorite_ToggleBtn.setSelected(true);
		Favorite_ToggleBtn.setPrefWidth(140);
		Favorite_ToggleBtn.setLayoutX(Favorite_ToggleBtn.getLayoutX() - 30);

		content_Pane.getChildren().clear();
		try {
			content_Pane.getChildren().add(FavoritesController.launch(isOK));

			thePane = 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void jumpToGrails(ActionEvent actionEvent) {
		// System.out.println("grails is "+ Grail_ToggleBtn);
		for (ToggleButton aButton : buttons) {
			if (aButton.selectedProperty().get()) {
				aButton.setLayoutX(aButton.getLayoutX() + 30);
				aButton.setPrefWidth(80);
				aButton.setSelected(false);
			}

		}
		// Favorite_ToggleBtn.setSelected(false);
		// StocksComparison_ToggleBtn.setSelected(false);
		// Hot_ToggleBtn.setSelected(false);
		Grail_ToggleBtn.setSelected(true);
		Grail_ToggleBtn.setPrefWidth(140);
		Grail_ToggleBtn.setLayoutX(Grail_ToggleBtn.getLayoutX() - 30);
		// for (ToggleButton aButton : buttons) {
		// if (!aButton.selectedProperty().get()) {
		// aButton.setPrefWidth(80);
		// }
		// if (aButton == Favorite_ToggleBtn && ifTheFirstTime == 1) {
		// aButton.setLayoutX(aButton.getLayoutX() + 30);
		// ifTheFirstTime = 0;
		// }
		// }
		content_Pane.getChildren().clear();
		try {
			content_Pane.getChildren().add(GrailDetailsController.launch(isOK));
			thePane = 2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void jumpToComparison(ActionEvent actionEvent) {
		for (ToggleButton aButton : buttons) {
			if (aButton.selectedProperty().get()) {
				aButton.setLayoutX(aButton.getLayoutX() + 30);
				aButton.setPrefWidth(80);
				aButton.setSelected(false);
			}

		}
		// Favorite_ToggleBtn.setSelected(false);
		// Grail_ToggleBtn.setSelected(false);
		// Hot_ToggleBtn.setSelected(false);
		StocksComparison_ToggleBtn.setSelected(true);
		StocksComparison_ToggleBtn.setPrefWidth(140);
		StocksComparison_ToggleBtn.setLayoutX(StocksComparison_ToggleBtn.getLayoutX() - 30);

		// for (ToggleButton aButton : buttons) {
		// if (!aButton.selectedProperty().get()) {
		// aButton.setPrefWidth(80);
		// }
		// if (aButton == Favorite_ToggleBtn && ifTheFirstTime == 1) {
		// aButton.setLayoutX(aButton.getLayoutX() + 30);
		// ifTheFirstTime = 0;
		// }
		// }
		content_Pane.getChildren().clear();
		try {
			content_Pane.getChildren().add(StocksComparisonController.launch(isOK));
			thePane = 3;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void jumpToHot(ActionEvent actionEvent) {
		for (ToggleButton aButton : buttons) {
			if (aButton.selectedProperty().get()) {
				aButton.setLayoutX(aButton.getLayoutX() + 30);
				aButton.setPrefWidth(80);
				aButton.setSelected(false);
			}

		}
		// Grail_ToggleBtn.setSelected(false);
		// StocksComparison_ToggleBtn.setSelected(false);
		// Favorite_ToggleBtn.setSelected(false);
		Hot_ToggleBtn.setSelected(true);
		Hot_ToggleBtn.setPrefWidth(140);
		Hot_ToggleBtn.setLayoutX(Hot_ToggleBtn.getLayoutX() - 30);

		// for (ToggleButton aButton : buttons) {
		// if (!aButton.selectedProperty().get()) {
		// aButton.setPrefWidth(80);
		// }
		// if (aButton == Favorite_ToggleBtn && ifTheFirstTime == 1) {
		// aButton.setLayoutX(aButton.getLayoutX() + 30);
		// ifTheFirstTime = 0;
		// }
		// }

		content_Pane.getChildren().clear();
		try {
			content_Pane.getChildren().add(HotCareerController.launch(isOK));
			thePane = 4;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jumpStockDetail(){
		content_Pane.getChildren().clear();
		try {
			content_Pane.getChildren().add(StockDetailsController.launch(isOK));
			thePane = 1;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jumpK(){
		content_Pane.getChildren().clear();
		try {
			content_Pane.getChildren().add(CandleStickChartController.launch(isOK));
			thePane = 1;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AnchorPane getOuterPane() {
		return outerPane;
	}

	public AnchorPane getContentPane() {
		return content_Pane;
	}

	public void closeStage(ActionEvent actionEvent) {
		Main.primaryStage.close();
		System.exit(0);
	}

	public void maximizeStage(ActionEvent actionEvent) {
		Main.primaryStage.setFullScreen(true);
	}

	public void minimizeStage(ActionEvent actionEvent) {
		Main.primaryStage.toBack();
	}
	//
	// private static void setAnchor(Node node, double top, double bottom,
	// double left, double right) {
	// AnchorPane.setTopAnchor(node, top);
	// AnchorPane.setBottomAnchor(node, bottom);
	// AnchorPane.setLeftAnchor(node, left);
	// AnchorPane.setRightAnchor(node, right);
	// }

	// public void jumpToSetDialog(Event Event) {
	// Stage stage = SettingDialogController.newDialog(Main.primaryStage);
	// stage.showAndWait();
	// }

	public void refreshPane(ActionEvent actionEvent) {

		ExceptionReporter exReporter = ExceptionReporter.getInstance();
		isOK = exReporter.InternetRepairTest();
		if (isOK) {
			wifi.getStyleClass().clear();
			wifi.getStyleClass().add("label-wifi");
			reason.setText("");
			switch (thePane) {
			case 1:
				if (theFavorite == 1) {
					jumpToFavorite(null);
				}
				if (theFavorite == 2) {
					jumpStockDetail();
				}
				if (theFavorite == 3) {
					jumpK();
				}
				break;
			case 2:
				jumpToGrails(null);
				break;
			case 3:
				jumpToComparison(null);
				break;
			case 4:
				jumpToHot(null);
				break;
			}
		}

	}

	public static mainPaneController getInstance() {
		return instance;
	}

	public void disable() {
		switch (thePane) {
		case 1:
			jumpToFavorite(null);

			break;
		case 2:
			jumpToGrails(null);
			break;
		case 3:
			jumpToComparison(null);
			break;
		case 4:
			jumpToHot(null);
			break;
		}

	}

	public int getFavorite() {
		return theFavorite;
	}

	public void setFavorite(int theFavorite) {
		this.theFavorite = theFavorite;
	}

	public void update(Observable o, Object arg) {
		// 界面做出变更
		System.out.println("I know exception!");
		ExceptionReporter exr = (ExceptionReporter) o;

		isOK = exr.isOk();
		reason.setText(exr.getExceptionInform());
		wifi.getStyleClass().clear();
		wifi.getStyleClass().add("label-nowifi");
		disable();

	}

}
