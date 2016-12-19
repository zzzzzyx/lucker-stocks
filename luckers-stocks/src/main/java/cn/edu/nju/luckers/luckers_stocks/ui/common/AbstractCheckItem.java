package cn.edu.nju.luckers.luckers_stocks.ui.common;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class AbstractCheckItem<T> {
    protected SimpleObjectProperty<T> vo;
    protected SimpleBooleanProperty selected;

    public T getVo() {
        return vo.get();
    }

    public SimpleObjectProperty<T> voProperty() {
        return vo;
    }

    public void setVo(T vo) {
        this.vo.set(vo);
    }

    public boolean getSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public abstract String toString();
}
