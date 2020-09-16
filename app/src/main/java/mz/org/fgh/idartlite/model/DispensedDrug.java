package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;

@DatabaseTable(tableName = "Dispense_drug")
public class DispensedDrug extends BaseModel {

    public static final String COLUMN_QUANTITY_SUPPLIED = "quantity_supplied";
    public static final String COLUMN_DISPENSE = "dispense_id";
    public static final String COLUMN_STOCK = "stock_id";


    @DatabaseField(columnName = "id", id = true)
    private int id;

    @DatabaseField(columnName = COLUMN_QUANTITY_SUPPLIED)
    private int quantitySupplied;

    @DatabaseField(columnName = COLUMN_STOCK , canBeNull = false, foreign = true)
    private Stock stock;

    @DatabaseField(columnName = COLUMN_DISPENSE ,canBeNull = false, foreign = true)
    private Dispense dispense;

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
}
