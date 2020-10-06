package mz.org.fgh.idartlite.model;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.dao.StockDaoImpl;

import java.util.Date;
import java.util.Objects;

@DatabaseTable(tableName = "Stock", daoClass = StockDaoImpl.class)
public class Stock extends BaseModel implements Listble {


    public static final String COLUMN_ORDER_NUMBER = "order_number";
    public static final String COLUMN_BATCH_NUMBER = "batch_number";
    public static final String COLUMN_DATE_RECEIVED = "date_received";
    public static final String COLUMN_SHELF_NUMBER = "shelf_number";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";
    public static final String COLUMN_UNITS_RECEIVED = "units_received";
    public static final String COLUMN_STOCK_MOVIMENT = "stock_moviment";
    public static final String COLUMN_STOCK_ADJUSTMENTS = "stock_adjustments";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DRUG = "drug_id";
    public static final String COLUMN_CLINIC = "clinic_id";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_SYNC_STATUS = "sync_status";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_ORDER_NUMBER)
    private String orderNumber;

    @DatabaseField(columnName = COLUMN_BATCH_NUMBER)
    private String batchNumber;

    @DatabaseField(columnName = COLUMN_DATE_RECEIVED)
    private Date dateReceived;

    @DatabaseField(columnName = COLUMN_SHELF_NUMBER)
    private int shelfNumber;

    @DatabaseField(columnName = COLUMN_EXPIRY_DATE)
    private Date expiryDate;

    @DatabaseField(columnName = COLUMN_UNITS_RECEIVED)
    private int unitsReceived;

    @DatabaseField(columnName = COLUMN_STOCK_MOVIMENT)
    private int stockMoviment;

    @DatabaseField(columnName = COLUMN_STOCK_ADJUSTMENTS)
    private int stockAdjustments;

    @DatabaseField(columnName =COLUMN_PRICE)
    private double price;

    @DatabaseField(columnName = COLUMN_UUID)
    private String uuid;

    @DatabaseField(columnName = COLUMN_CLINIC,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Clinic clinic;

    @DatabaseField(columnName = COLUMN_DRUG,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Drug drug;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    public int getId() {
        return id;
    }

    @Override
    public int getPosition() {
        return this.listPosition;
    }

    @Override
    public String getDescription() {
        return this.drug.getDescription();
    }

    @Override
    public int getQuantity() {
        return this.unitsReceived;
    }

    @Override
    public int compareTo(BaseModel baseModel) {
        if (this.getPosition() == 0 || ((Stock) baseModel).getPosition() == 0) return 0;

        return this.getPosition() - ((Stock) baseModel).getPosition();
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getUnitsReceived() {
        return unitsReceived;
    }

    public void setUnitsReceived(int unitsReceived) {
        this.unitsReceived = unitsReceived;
    }

    public int getStockMoviment() {
        return stockMoviment;
    }

    public void setStockMoviment(int stockMoviment) {
        this.stockMoviment = stockMoviment;
    }

    public int getStockAdjustments() {
        return stockAdjustments;
    }

    public void setStockAdjustments(int stockAdjustments) {
        this.stockAdjustments = stockAdjustments;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return uuid.equals(stock.uuid);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", batchNumber=" + batchNumber +
                ", dateReceived=" + dateReceived +
                ", shelfNumber=" + shelfNumber +
                ", expiryDate=" + expiryDate +
                ", unitsReceived=" + unitsReceived +
                ", stockMoviment=" + stockMoviment +
                ", stockAdjustments=" + stockAdjustments +
                ", price=" + price +
                ", uuid='" + uuid + '\'' +
                ", clinic=" + clinic +
                ", drug=" + drug +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String getLote() {
        return null;
    }
}
