package mz.org.fgh.idartlite.model.inventory;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.stock.IventoryDaoImpl;
import mz.org.fgh.idartlite.model.StockAjustment;

@DatabaseTable(tableName = "inventory", daoClass = IventoryDaoImpl.class)
public class Iventory extends BaseModel {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_SEQUENCE = "sequence";
    public static final String COLUMN_OPEN = "open";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_START_DATE)
    private Date startDate;

    @DatabaseField(columnName = COLUMN_END_DATE)
    private Date endDate;

    @DatabaseField(columnName = COLUMN_SEQUENCE)
    private int sequence;

    @DatabaseField(columnName = COLUMN_OPEN)
    private boolean open;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    private List<StockAjustment> ajustmentList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    public String isValid(Context context) {
        return null;
    }

    @Override
    public String canBeEdited(Context context) {
        return null;
    }

    @Override
    public String canBeRemoved(Context context) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Iventory)) return false;
        Iventory iventory = (Iventory) o;
        return id == iventory.id &&
                sequence == iventory.sequence &&
                open == iventory.open &&
                Objects.equals(startDate, iventory.startDate) &&
                Objects.equals(endDate, iventory.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, sequence, open);
    }

    public List<StockAjustment> getAjustmentList() {
        return ajustmentList;
    }

    public void setAjustmentList(List<StockAjustment> ajustmentList) {
        this.ajustmentList = ajustmentList;
    }

    public void addAjustment(StockAjustment ajustment){
        if (this.ajustmentList == null) this.ajustmentList = new ArrayList<>();

        this.ajustmentList.add(ajustment);
    }
}
