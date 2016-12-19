package cn.edu.nju.luckers.luckers_stocks.ui.common;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.EnumObservable;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SimpleEnumProperty<E extends Enum<E> & EnumObservable<E>> implements ObservableValue<String>{
    private E origin;

    public SimpleEnumProperty(E origin) {
        this.origin = origin;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    @Override
    public void addListener(ChangeListener<? super String> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {

    }

    @Override
    public String getValue() {
        return origin.getChinese();
    }

    public E getEnum() {
        return origin;
    }

    public String toString(){
        return origin.getChinese();
    }

}