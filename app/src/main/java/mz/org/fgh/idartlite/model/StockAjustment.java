package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.stock.StockAjustmentDaoImpl;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.util.DateUtilities;

@DatabaseTable(tableName = "stock_ajustment", daoClass = StockAjustmentDaoImpl.class)
public class StockAjustment extends BaseModel implements Listble {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STOCK_COUNT= "stock_count";
    public static final String COLUMN_AJUSTED_VALUE = "adjusted_value";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_STOCK_ID = "stock_id";
    public static final String COLUMN_IVENTORY_ID= "iventory_id";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_STOCK_COUNT)
    private int stockCount;

    @DatabaseField(columnName = COLUMN_AJUSTED_VALUE)
    private int adjustedValue;

    @DatabaseField(columnName = COLUMN_NOTES)
    private String notes;

    @DatabaseField(columnName = COLUMN_STOCK_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Stock stock;

    @DatabaseField(columnName = COLUMN_IVENTORY_ID, canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Iventory iventory;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    public StockAjustment(Stock stock, Iventory selectedRecord) {
        this.stock = stock;
        this.iventory = selectedRecord;
        this.listType = Listble.INVENTORY_LISTING;
        this.setSyncStatus(BaseModel.SYNC_SATUS_READY);
        this.setDate(DateUtilities.getCurrentDate());
    }

    public StockAjustment() {
        this.listType = Listble.INVENTORY_LISTING;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public int getAdjustedValue() {
        return adjustedValue;
    }

    public void setAdjustedValue(int adjustedValue) {
        this.adjustedValue = adjustedValue;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Iventory getIventory() {
        return iventory;
    }

    public void setIventory(Iventory iventory) {
        this.iventory = iventory;
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
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String getDescription() {
        return this.stock.getDescription();
    }

    @Override
    public int getQuantity() {
        return 0;
    }

    @Override
    public String getLote() {
        return this.stock.getLote();
    }

    @Override
    public Date getValidate() {
        return this.stock.getExpiryDate();
    }

    @Override
    public int getSaldoActual() {
        return this.stock.getStockMoviment();
    }

    @Override
    public int getQtyToModify() {
        return this.stockCount;
    }

    @Override
    public void setQtyToModify(int qtyToDestroy) {
        this.stockCount = qtyToDestroy;

        calculateValueToAjust();
    }

    private void calculateValueToAjust() {
        if (this.stock == null) return;
        this.adjustedValue = this.stockCount - getSaldoActual();
    }

    @Override
    public int getDrawable() {
        return 0;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public boolean isIventoryListing() {
        return this.listType.equals(Listble.INVENTORY_LISTING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockAjustment)) return false;
        StockAjustment that = (StockAjustment) o;
        return id == that.id && adjustedValue == that.adjustedValue &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(iventory, that.iventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, iventory);
    }
}
