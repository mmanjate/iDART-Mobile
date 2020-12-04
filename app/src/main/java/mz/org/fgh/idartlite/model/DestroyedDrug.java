package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.stock.DestroyedDrugDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = DestroyedDrug.TABLE_NAME, daoClass = DestroyedDrugDaoImpl.class)
public class DestroyedDrug extends BaseModel implements Listble {

    public static final String TABLE_NAME = "destroyed_drug";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STOCK_ID = "stock_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_UPDATE_STATUS = "update_status";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_STOCK_ID,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Stock stock;

    @DatabaseField(columnName = COLUMN_QUANTITY)
    private int quantity;

    @DatabaseField(columnName = COLUMN_NOTES)
    private String notes;

    @DatabaseField(columnName = COLUMN_UPDATE_STATUS)
    private boolean updateStatus;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    private int adjustedValue;

    public DestroyedDrug() {
        listType = Listble.STOCK_DESTROY_LISTING;
    }

    public DestroyedDrug(Stock stock) {
        this.stock = stock;

        listType = Listble.STOCK_DESTROY_LISTING;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return null;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int getDrawable() {
        return 0;
    }

    @Override
    public String getCode() {
        return null;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(boolean updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String isValid(Context context) {
        if (this.date == null) return "A data da operação deve ser indicada.";
        if((int) (DateUtilities.dateDiff(this.date, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT)) > 0) {
            return "A data da operação não pode ser maior que a data corrente";
        }

        if (this.quantity <= 0) return "A quantidade deve ser indicada.";

        if (!Utilities.stringHasValue(this.notes)) return "As notas da operação devem ser indicadas.";
        return null;
    }

    @Override
    public String canBeEdited(Context context) {
        if((int) (DateUtilities.dateDiff(this.date, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT)) >= 30) {
            return "Esta operação de destruição de stock não pode ser alterada";
        }

        if (isSyncStatusSent(this.syncStatus)) return "Não pode efectuar alterações sobre este registo, pois já se encontra sincronizado com a central.";

        return null;
    }

    @Override
    public String canBeRemoved(Context context) {
        if((int) (DateUtilities.dateDiff(this.date, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT)) >= 30) {
            return "Esta operação de destruição de stock não pode ser removida";
        }

        if (isSyncStatusSent(this.syncStatus)) return "Não pode remover este registo, pois já se encontra sincronizado com a central.";
        return null;
    }

    @Override
    public boolean isStockDestroyListing() {
        return listType.equals(Listble.STOCK_DESTROY_LISTING);
    }

    @Override
    public void setQtyToModify(int qtyToDestroy) {
        this.quantity = qtyToDestroy;
    }

    @Override
    public int getQtyToModify() {
        return quantity;
    }

    @Override
    public String getLote() {
        return stock.getLote();
    }

    @Override
    public Date getValidate() {
        return stock.getExpiryDate();
    }

    @Override
    public int getSaldoActual() {
        return stock.getQuantity();
    }

    @Override
    public void setSaldoActual(int saldo) {
        this.stock.modifyCurrentQuantity(saldo);
    }

    @Override
    public void setAdjustedValue(int adjustedValue) {
        this.adjustedValue = adjustedValue;
    }

    @Override
    public int getAdjustedValue() {
        return adjustedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DestroyedDrug)) return false;
        DestroyedDrug that = (DestroyedDrug) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, stock);
    }
}
