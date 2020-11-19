package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.dispense.DispensedDrugDaoImpl;

@DatabaseTable(tableName = "Dispense_drug", daoClass = DispensedDrugDaoImpl.class)
public class DispensedDrug extends BaseModel {

    public static final String COLUMN_QUANTITY_SUPPLIED = "quantity_supplied";
    public static final String COLUMN_DISPENSE = "dispense_id";
    public static final String COLUMN_STOCK = "stock_id";
    public static final String COLUMN_SYNC_STATUS = "sync_status";


    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_QUANTITY_SUPPLIED)
    private int quantitySupplied;

    @DatabaseField(columnName = COLUMN_STOCK , canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Stock stock;

    @DatabaseField(columnName = COLUMN_DISPENSE ,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Dispense dispense;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    public DispensedDrug() {
    }

    public DispensedDrug(Stock stock, Dispense dispense) {
        this.stock = stock;
        this.dispense = dispense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantitySupplied() {
        return quantitySupplied;
    }

    public void setQuantitySupplied(int quantitySupplied) {
        this.quantitySupplied = quantitySupplied;
    }

    public Dispense getDispense() {
        return dispense;
    }

    public void setDispense(Dispense dispense) {
        this.dispense = dispense;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DispensedDrug that = (DispensedDrug) o;
        return id == that.id &&
                stock.equals(that.stock) &&
                dispense.equals(that.dispense);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, stock, dispense);
    }

    @Override
    public String toString() {
        return "DispensedDrug{" +
                "id=" + id +
                ", quantitySupplied=" + quantitySupplied +
                ", stock=" + stock +
                ", dispense=" + dispense +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
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
}
