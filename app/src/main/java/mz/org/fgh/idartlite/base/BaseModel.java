package mz.org.fgh.idartlite.base;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    protected int listPosition;

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}
