package cn.edu.nju.luckers.luckers_stocks.main;

import java.io.IOException;

import javax.xml.stream.events.StartDocument;

import com.sun.javafx.application.LauncherImpl;

import cn.edu.nju.luckers.luckers_stocks.ui.AllStocks.AllStocksController;
import cn.edu.nju.luckers.luckers_stocks.ui.Favorites.FavoritesController;
import cn.edu.nju.luckers.luckers_stocks.ui.common.mainPaneController;
import cn.edu.nju.luckers.luckers_stocks.ui.tool.R;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Main extends Application {

	public static Scene originScene;
	public static Stage primaryStage;
	private static double calX;
	private static double calY;
	private static double oldPosX;
	private static double oldPosY;
	private static double oldWidth;
	private static double oldHeight;
	private static boolean dragging = false;
	private static boolean resizing = false;
	private static CURSOR_AREA pressedArea = CURSOR_AREA.CENTER;
	private static final double padding = R.ui.ResizePadding;
	private static final double titleHeight = R.ui.TitleHeight;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;

		primaryStage.setX(10);
		primaryStage.setY(10);
		// primaryStage.getIcons().add(new
		// Image(this.getClass().getResourceAsStream("/ui/css/pic/logo-client.png")));

		primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent pane = mainPaneController.launch().outerPane;
		if (originScene == null) {
			originScene = new Scene(pane);
		} else {
			originScene.setRoot(pane);
		}
		primaryStage.setWidth(R.ui.MinStageWidth);
		primaryStage.setHeight(R.ui.MinStageHeight);
		primaryStage.setScene(originScene);
		enableDragAndResize(originScene);
		primaryStage.show();
	}

//	private static Parent launchPanes() {
//		try {
//			return Navigation.launch();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	private static enum CURSOR_AREA {
		NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST, NORTH, SOUTH, WEST, EAST, TITLE, CENTER
	}

	private static CURSOR_AREA getCursorArea(double x, double y, double width, double height) {
		if (y < padding && x < padding) {
			return CURSOR_AREA.NORTH_WEST;
		} else if (y < padding && x > width - padding) {
			return CURSOR_AREA.NORTH_EAST;
		} else if (y > height - padding && x < padding) {
			return CURSOR_AREA.SOUTH_WEST;
		} else if (y > height - padding && x > width - padding) {
			return CURSOR_AREA.SOUTH_EAST;
		} else if (y > height - padding) {
			return CURSOR_AREA.SOUTH;
		} else if (x > width - padding) {
			return CURSOR_AREA.EAST;
		} else if (y < padding) {
			return CURSOR_AREA.NORTH;
		} else if (x < padding) {
			return CURSOR_AREA.WEST;
		} else if (y < titleHeight) {
			return CURSOR_AREA.TITLE;
		} else {
			return CURSOR_AREA.CENTER;
		}
	}

	private static final double minWidth = R.ui.MinStageWidth;
	private static final double minHeight = R.ui.MinStageHeight;

	private static void setBounds(Stage stage, double x, double y, double w, double h) {
		if (w > minWidth) {
			stage.setWidth(w);
			stage.setX(x);
			stage.setY(y);
		} else {
			stage.setWidth(minWidth);
		}
		if (h > minHeight) {
			stage.setHeight(h);
			stage.setX(x);
			stage.setY(y);
		} else {
			stage.setHeight(minHeight);
		}
	}

	static double ix;
	static double iy;

	private static void enableDrag(Scene scene) {
		scene.setOnMousePressed(event -> {
			ix = event.getScreenX() - primaryStage.getX();
			iy = event.getScreenY() - primaryStage.getY();
		});
		scene.setOnMouseDragged(event -> {
			primaryStage.setX(event.getScreenX() - ix);
			primaryStage.setY(event.getScreenY() - iy);
		});
	}

	/**
	 * ！！！！！！！！！ 我必须要写注释，千万不要搞混 sceneX 和 screenX ！！！！！！！！！
	 */
	private static void enableDragAndResize(Scene scene) {
		scene.setOnMousePressed(me -> {
			pressedArea = getCursorArea(me.getX(), me.getY(), primaryStage.getWidth(), primaryStage.getHeight());
			if (pressedArea == CURSOR_AREA.TITLE) {
				resizing = false;
				dragging = true;
				calX = me.getScreenX() - primaryStage.getX();
				calY = me.getScreenY() - primaryStage.getY();
			} else if (pressedArea == CURSOR_AREA.CENTER) {
				resizing = false;
				dragging = false;
			} else {
				resizing = true;
				dragging = false;
				oldPosX = primaryStage.getX();
				oldPosY = primaryStage.getY();
				oldWidth = primaryStage.getWidth();
				oldHeight = primaryStage.getHeight();
				calX = me.getScreenX();
				calY = me.getScreenY();
			}
		});
		scene.setOnMouseDragged(me -> {
			if (dragging) {
				primaryStage.setX(me.getScreenX() - calX);
				primaryStage.setY(me.getScreenY() - calY);
			} else if (resizing) {
				double dx = me.getScreenX() - calX;
				double dy = me.getScreenY() - calY;
				switch (pressedArea) {
				case NORTH_WEST:
					setBounds(primaryStage, oldPosX + dx, oldPosY + dy, oldWidth - dx, oldHeight - dy);
					break;
				case NORTH_EAST:
					setBounds(primaryStage, oldPosX, oldPosY + dy, oldWidth + dx, oldHeight - dy);
					break;
				case SOUTH_WEST:
					setBounds(primaryStage, oldPosX + dx, oldPosY, oldWidth - dx, oldHeight + dy);
					break;
				case SOUTH_EAST:
					setBounds(primaryStage, oldPosX, oldPosY, oldWidth + dx, oldHeight + dy);
					break;
				case NORTH:
					setBounds(primaryStage, oldPosX, oldPosY + dy, oldWidth, oldHeight - dy);
					break;
				case SOUTH:
					setBounds(primaryStage, oldPosX, oldPosY, oldWidth, oldHeight + dy);
					break;
				case WEST:
					setBounds(primaryStage, oldPosX + dx, oldPosY, oldWidth - dx, oldHeight);
					break;
				case EAST:
					setBounds(primaryStage, oldPosX, oldPosY, oldWidth + dx, oldHeight);
					break;
				case TITLE:
					break;
				case CENTER:
					break;
				}
			}
		});
		// change the look of the mouse when it is moved to the sides
		scene.setOnMouseMoved(me -> {
			CURSOR_AREA area = getCursorArea(me.getX(), me.getY(), primaryStage.getWidth(), primaryStage.getHeight());
			Cursor cursor = Cursor.DEFAULT;
			switch (area) {
			case NORTH_WEST:
				cursor = Cursor.NW_RESIZE;
				break;
			case NORTH_EAST:
				cursor = Cursor.NE_RESIZE;
				break;
			case SOUTH_WEST:
				cursor = Cursor.SW_RESIZE;
				break;
			case SOUTH_EAST:
				cursor = Cursor.SE_RESIZE;
				break;
			case NORTH:
			case SOUTH:
				cursor = Cursor.V_RESIZE;
				break;
			case WEST:
			case EAST:
				cursor = Cursor.H_RESIZE;
				break;
			case TITLE:
			case CENTER:
				cursor = Cursor.DEFAULT;
				break;
			}
			primaryStage.getScene().setCursor(cursor);
		});
	}
}
