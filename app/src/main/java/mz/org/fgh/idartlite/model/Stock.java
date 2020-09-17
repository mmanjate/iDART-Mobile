package mz.org.fgh.idartlite.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import mz.org.fgh.idartlite.dao.StockDaoImpl;

@DatabaseTable(tableName = "Stock", daoClass = StockDaoImpl.class)
public class Stock {


    public static final String COLUMN_QUANTITY_SUPPLIED = "order_number";
    public static final String COLUMN_BATCH_NUMBER = "batch_number";
    public static final String COLUMN_DATE_RECEIVED = "date_received";
    public static final String COLUMN_SHELF_NUMBER = "shelf_number";
    public static final String COLUMN_PRESCRIPTION = "expiry_date";
    public static final String COLUMN_UNITS_RECEIVED = "units_received";
    public static final String COLUMN_STOCK_MOVIMENT = "stock_moviment";
    public static final String COLUMN_STOCK_ADJUSTMENTS = "stock_adjustments";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DRUG = "drug_id";
    public static final String COLUMN_CLINIC = "clinic_id";
    public static final String COLUMN_UUID = "uuid";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "order_number")
    private String orderNumber;

    @DatabaseField(columnName = "batch_number")
    private int batchNumber;

    @DatabaseField(columnName = "date_received")
    private Date dateReceived;

    @DatabaseField(columnName = "shelf_number")
    private int shelfNumber;

    @DatabaseField(columnName = "expiry_date")
    private Date expiryDate;

    @DatabaseField(columnName = "units_received")
    private int unitsReceived;

    @DatabaseField(columnName = "stock_moviment")
    private int stockMoviment;

    @DatabaseField(columnName = "stock_adjustments")
    private int stockAdjustments;

    @DatabaseField(columnName = "price")
    private double price;

    @DatabaseField(columnName = "uuid")
    private String uuid;

    @DatabaseField(canBeNull = false, foreign = true)
    private Clinic clinic_id;

    @DatabaseField(canBeNull = false, foreign = true)
    private Drug drug;

    public int getId() {
        return id;
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

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
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
}
