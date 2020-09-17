package mz.org.fgh.idartlite.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;

import java.util.Date;

@DatabaseTable(tableName = "Dispense")
public class Dispense extends BaseModel {

    public static final String COLUMN_PICKUP_DATE = "pickup_date";
    public static final String COLUMN_SUPPLY = "supply";
    public static final String COLUMN_NEXT_PICKUP_DATE = "next_pickup_date";
    public static final String COLUMN_PRESCRIPTION = "prescription_id";
    public static final String COLUMN_UUID = "uuid";

    @DatabaseField(columnName = "id", id = true)
    private int id;

    @DatabaseField(columnName = COLUMN_PICKUP_DATE)
    private Date pickupDate;

    @DatabaseField(columnName =COLUMN_SUPPLY)
    private int supply;

    @DatabaseField(columnName = COLUMN_NEXT_PICKUP_DATE)
    private Date nextPickupDate;

    @DatabaseField(columnName = COLUMN_PRESCRIPTION,canBeNull = false, foreign = true)
    private Prescription prescription;

    @DatabaseField(columnName = COLUMN_UUID)
    private String uuid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    public Date getNextPickupDate() {
        return nextPickupDate;
    }

    public void setNextPickupDate(Date nextPickupDate) {
        this.nextPickupDate = nextPickupDate;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
