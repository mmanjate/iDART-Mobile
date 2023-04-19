package mz.org.fgh.idartlite.base.model;

import android.content.Context;

import java.io.Serializable;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.util.Utilities;

/**
 * Generic class that represent all application entities
 */
public abstract class BaseModel implements Serializable {

    public static final String COLUMN_SYNC_STATUS = "sync_status";

    protected int listPosition;

    protected String listType;

    public static final String SYNC_SATUS_READY = "R";
    public static final String SYNC_SATUS_SENT = "S";
    public static final String SYNC_SATUS_UPDATED = "U";

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public int getListPosition() {
        return listPosition;
    }

    public boolean isSyncStatusReady(String syncStatus){
        return syncStatus.equals(SYNC_SATUS_READY);
    }

    public boolean isSyncStatusSent(String syncStatus){
        return syncStatus.equals(SYNC_SATUS_SENT);
    }

    public boolean isSyncStatusUpdated(String syncStatus){
        return syncStatus.equals(SYNC_SATUS_UPDATED);
    }

    public abstract String isValid(Context context);


    public abstract String canBeEdited(Context context);


    public abstract String canBeRemoved(Context context);

    public String canBeEdited(String syncStatus, Context context) {
        if (!Utilities.stringHasValue(syncStatus)) return "";

        if (isSyncStatusSent(syncStatus)) return context.getString(R.string.record_cant_be_edited);
        return null;
    }


    public String canBeRemoved(String syncStatus, Context context) {
        if (!Utilities.stringHasValue(syncStatus)) return "";

        if (isSyncStatusSent(syncStatus)) return context.getString(R.string.record_cant_be_removed);
        return null;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }


}
