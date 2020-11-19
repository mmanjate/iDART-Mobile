package mz.org.fgh.idartlite.adapter.recyclerview.listable;

import mz.org.fgh.idartlite.base.model.BaseModel;

public interface Listble<T extends BaseModel> extends Comparable<T>{

    int getId();

    default int getPosition() {
        return 0;
    }

    String getDescription();

    void setListPosition(int listPosition);

    default int getQuantity() {
        return 0;
    }

    default String getLote() {
        return null;
    }

    default String getFnmcode() {
        return null;
    }

    int getDrawable();

    String getCode();

    @Override
    default int compareTo(T t) {
        return 0;
    }
}
