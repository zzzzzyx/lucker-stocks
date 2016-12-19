package cn.edu.nju.luckers.luckers_stocks.ui.tool;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Sissel on 2015/12/27.
 */
public class AnchorSet {
    public static void set(Node node, double top, double bottom, double left, double right){
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }

    public static void set0(Node node){
        set(node, 0.0, 0.0, 0.0, 0.0);
    }
}
