package cn.edu.nju.luckers.luckers_stocks.ui.common;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.EnumObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Enum2ObservableList {

    public static <E extends Enum<E> & EnumObservable<E>>
    ObservableList<SimpleEnumProperty<E>> transit(E[] enums){
        ObservableList<SimpleEnumProperty<E>> list = FXCollections.observableArrayList();
        for (E anEnum : enums) {
            list.add(new SimpleEnumProperty<E>(anEnum));
        }
        return list;
    }

}
