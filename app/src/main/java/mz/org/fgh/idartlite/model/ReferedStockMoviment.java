package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.stock.ReferedStockMovimentDaoDaoImpl;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = ReferedStockMoviment.TABLE_NAME, daoClass = ReferedStockMovimentDaoDaoImpl.class)
public class ReferedStockMoviment extends BaseModel implements Listble<ReferedStockMoviment> {

    public static final String TABLE_NAME = "refered_stock_moviment";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STOCK_ID = "stock_id";
    public static final String COLUMN_QTY = "quantity";
    public static final String COLUMN_ORIGIN = "origin";
    public static final String COLUMN_OPERATION_TYPE_ID = "operation_type_id";
    public static final String COLUMN_UPDATED_STATUS = "updated_status";
    public static final String COLUMN_ORDER_NUMBER = "order_number";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;


    @DatabaseField(columnName = COLUMN_STOCK_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Stock stock;

    @DatabaseField(columnName = COLUMN_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_QTY)
    private int quantity;

    @DatabaseField(columnName = COLUMN_ORIGIN)
    private String origin;


    @DatabaseField(columnName = COLUMN_OPERATION_TYPE_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private OperationType operationType;

    @DatabaseField(columnName = COLUMN_UPDATED_STATUS)
    private boolean updatedStatus;

    @DatabaseField(columnName = COLUMN_ORDER_NUMBER)
    private String orderNumber;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    public ReferedStockMoviment() {
        this.listType = Listble.REFERED_STOCK_LISTING;
    }

    @Override
    public String getFnmcode() {
        return this.stock.getDrug().getFnmcode();
    }

    public ReferedStockMoviment(Stock stock) {
        this.stock = stock;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public boolean isUpdatedStatus() {
        return updatedStatus;
    }

    public void setUpdatedStatus(boolean updatedStatus) {
        this.updatedStatus = updatedStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    public String isValid(Context context) {
        if (!Utilities.stringHasValue(this.orderNumber)) return  "O número da guia de referência deve ser indicado.";
        if (this.operationType == null || this.operationType.getId() <= 0) return  "O tipo de operação deve ser indicado.";
        if (this.date == null) return  "A data de registo deve ser indicada.";
        if (this.stock == null) return  "O stock a referir deve ser indicado.";

        return null;
    }

    @Override
    public String canBeEdited(Context context) {
        if (!Utilities.stringHasValue(this.syncStatus)) return "";

        if (isSyncStatusSent(this.syncStatus)) return "Não pode efectuar alterações sobre este registo, pois já se encontra sincronizado com a central.";
        return null;
    }

    @Override
    public String canBeRemoved(Context context) {
        if (!Utilities.stringHasValue(this.syncStatus)) return "";

        if (isSyncStatusSent(this.syncStatus)) return "Não pode remover este registo, pois já se encontra sincronizado com a central.";
        return null;
    }

    @Override
    public boolean isReferedStockListing() {
        return listType.equals(Listble.REFERED_STOCK_LISTING);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.stock.getDescription();
    }

    @Override
    public int getDrawable() {
        return 0;
    }

    @Override
    public String getCode() {
        return null;
    }

    public ReferedStockMoviment clone(){
        ReferedStockMoviment referedStockMoviment = new ReferedStockMoviment();
        referedStockMoviment.setId(this.id);
        referedStockMoviment.setDate(this.date);
        referedStockMoviment.setStock(this.stock);
        referedStockMoviment.setOperationType(this.operationType);
        referedStockMoviment.setOrderNumber(this.orderNumber);
        referedStockMoviment.setSyncStatus(this.syncStatus);
        referedStockMoviment.setOrigin(this.origin);
        referedStockMoviment.setQuantity(this.quantity);
        referedStockMoviment.setUpdatedStatus(this.updatedStatus);

        return referedStockMoviment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferedStockMoviment)) return false;
        ReferedStockMoviment that = (ReferedStockMoviment) o;
        return id == that.id &&
                quantity == that.quantity &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(date, that.date) &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(operationType, that.operationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, date, quantity, origin, operationType);
    }
}
