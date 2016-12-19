package cn.edu.nju.luckers.luckers_stocks.ui.common;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingDialogController {

	
    private Stage thisStage;

	public static Stage newDialog(Stage owner){
        Stage stage = new Stage();
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("系统设置");
//        stage.initOwner(owner);

        try {
            FXMLLoader loader = new FXMLLoader(SettingDialogController.class.getResource("SettingDialog.fxml"));
            Pane pane = loader.load();
            SettingDialogController controller = loader.getController();
            stage.setScene(new Scene(pane));

            controller.thisStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stage;
    }
	
	public void cancel(ActionEvent actionEvent){
		thisStage.close();
	}

}
