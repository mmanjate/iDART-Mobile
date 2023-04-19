package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.stock.ReferedStockMovimentDaoDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;
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
        if (!Utilities.stringHasValue(this.orderNumber)) return  context.getString(R.string.reference_number_mandatory);
        if (this.operationType == null || this.operationType.getId() <= 0) return  context.getString(R.string.operation_type_mandatory);
        if (this.date == null) return  context.getString(R.string.registration_date);
        if((DateUtilities.dateDiff(this.date, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT)) > 0) {
            return context.getString(R.string.registration_date_cant_be_gt_than_current);
        }
        if (this.stock == null) return  context.getString(R.string.refered_stock_is_mandatory);
        if (this.stock.getDrug() == null) return  context.getString(R.string.drug_must_be_indicated);
        if (!Utilities.stringHasValue(this.stock.getBatchNumber())) return  context.getString(R.string.batch_number_mandatory);
        if (this.stock.getExpiryDate() == null ) return  context.getString(R.string.expire_date_mandatory);

        if( (DateUtilities.dateDiff(this.stock.getExpiryDate(), DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT)) <= 0) {
            return context.getString(R.string.expire_date_must_be_gt_than_current);
        }

        if (this.quantity <= 0) return  context.getString(R.string.invalid_quantity);
        if (!Utilities.stringHasValue(this.origin)) return  context.getString(R.string.origin_mandatory);

        return null;
    }

    @Override
    public String canBeEdited(Context context) {
        return super.canBeEdited(this.syncStatus, context);
    }

    @Override
    public String canBeRemoved(Context context) {
        return super.canBeRemoved(this.syncStatus, context);
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
