package mz.org.fgh.idartlite.base;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseModel implements Serializable {

    protected int listPosition;

    public static final String SYNC_SATUS_READY = "R";
    public static final String SYNC_SATUS_SENT = "S";
    public static final String SYNC_SATUS_UPDATED = "U";

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }



}
