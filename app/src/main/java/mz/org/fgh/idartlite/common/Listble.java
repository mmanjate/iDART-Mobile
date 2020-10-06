package mz.org.fgh.idartlite.common;

import mz.org.fgh.idartlite.base.BaseModel;

public interface Listble<T extends BaseModel> extends Comparable<T>{

    int getId();

    int getPosition();

    String getDescription();

    void setListPosition(int listPosition);

    int getQuantity();

    default String getLote() {
        return null;
    }

    @Override
    int compareTo(T t);
}
