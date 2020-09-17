package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.dao.DispensedDrugDaoImpl;

@DatabaseTable(tableName = "Dispense_drug", daoClass = DispensedDrugDaoImpl.class)
public class DispensedDrug {


    public static final String COLUMN_QUANTITY_SUPPLIED = "quantity_supplied";
    public static final String COLUMN_DISPENSE = "dispense_id";
    public static final String COLUMN_NEXT_PICKUP_DATE = "next_pickup_date";
    public static final String COLUMN_PRESCRIPTION = "prescription_id";
    public static final String COLUMN_UUID = "uuid";


    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "quantity_supplied")
    private int quantitySupplied;

    //private Stock stock_id;

    @DatabaseField(canBeNull = false, foreign = true)
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
}
